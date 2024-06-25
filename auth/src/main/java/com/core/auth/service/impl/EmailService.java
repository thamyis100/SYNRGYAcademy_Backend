package com.core.auth.service.impl;

import com.core.auth.entity.oauth.User;
import com.core.auth.repository.oauth.IUserRepository;
import com.core.auth.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Service
@SuppressWarnings({"WeakerAccess", "ConstantConditions"})
@Slf4j
public class EmailService {

    @Value("${spring.mail.sender.name:admin}")
    private String senderName;

    @Value("${spring.mail.sender.mail:no-reply@test.com}")
    private String senderEmail;

    @Value("${expired.otp.minute:1200}")
    private int expiredOtp;

    @Value("${BASEURL:}")
    private String BASEURL;

    @Autowired
    private JavaMailSenderImpl javaMailSender;

    @Qualifier("taskExecutor")
    @Autowired
    private TaskExecutor taskExecutor;

    private final String REGISTER_URL = "/user-register/web/index/";

    private final Map<String, String> TEMPLATE_MAP =
            Map.ofEntries(
                    Map.entry("REGISTER", Util.getRegisterEmailTemplate()),
                    Map.entry("FORGET_PASSWORD", Util.getResetPassword())
            );

    @Autowired
    private IUserRepository userRepository;

    private final Map<String, String> SUBJECT_MAP =
            Map.ofEntries(
                    Map.entry("REGISTER", "Activation Account"),
                    Map.entry("FORGET_PASSWORD", "Forgot Password")
            );

    public void setupAndSendEmail(User user, String type) {
        String emailTemplate = TEMPLATE_MAP.get(type);

        User checker;
        String otp = user.getOtp();

        if (otp == null || user.getOtpExpiredDate() == null || user.getOtpExpiredDate().before(new Date())) {
            do {
                otp = Util.randomString(6, true);
                checker = userRepository.findOneByOTP(otp).orElse(null);
            } while (checker != null);
            Date newDate = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(newDate);
            calendar.add(Calendar.MINUTE, expiredOtp);
            Date expirationOtpDate = calendar.getTime();

            user.setOtp(otp);
            user.setOtpExpiredDate(expirationOtpDate);
            userRepository.save(user);
        }

        if (Objects.equals(type, "FORGET_PASSWORD")) {
            emailTemplate = emailTemplate.replaceAll("\\{\\{USERNAME}}", (user.getFullname() == null ? user.getUsername()
                    :
                    "@" + user.getUsername()));
        } else if (Objects.equals(type, "REGISTER")) {
            emailTemplate = emailTemplate.replaceAll("\\{\\{USERNAME}}", (user.getFullname() == null) ? user.getUsername() : user.getFullname());
        }
        emailTemplate = emailTemplate.replaceAll("\\{\\{VERIFY_TOKEN}}", otp);


        sendEmailAsync(user.getUsername(), emailTemplate, type);
    }

    private void sendEmail(String email, String message, String type) throws MessagingException, UnsupportedEncodingException {
        log.info("service (email) - create mime message");
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        log.info("service (email) - create mime message helper");
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

        log.info("service (email) - set email format");
        mimeMessageHelper.setFrom(senderEmail, senderName);
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject(SUBJECT_MAP.get(type));
        mimeMessageHelper.setText(message, true);

        log.info("service (email) - sending email to {}", email);
        log.info("service (email) - sending email from {}", senderEmail);
        log.info("service (email) - sending email with subject {}", SUBJECT_MAP.get(type));
        javaMailSender.send(mimeMessage);
    }

    private void sendEmailAsync(final String destEmail, final String message, final String type) {
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    sendEmail(destEmail, message, type);
                } catch (MessagingException | UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
