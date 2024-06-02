package synrgy7thapmoch1.controller.oauth;

import com.example.challenge6.config.Config;
import com.example.challenge6.entity.oauth.ResetPasswordModel;
import com.example.challenge6.entity.oauth.User;
import com.example.challenge6.repository.oauth.UserRepository;
import com.example.challenge6.service.email.EmailSender;
import com.example.challenge6.service.oauth.UserService;
import com.example.challenge6.utils.EmailTemplate;
import com.example.challenge6.utils.Response;
import com.example.challenge6.utils.SimpleStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/forget-password/")
public class ForgetPasswordController {

    @Autowired
    private UserRepository userRepository;

    Config config = new Config();

    @Autowired
    public UserService serviceReq;

    @Value("${expired.token.password.minute:}")//FILE_SHOW_RUL
    private int expiredToken;

    @Autowired
    public Response templateCRUD;

    @Autowired
    public EmailTemplate emailTemplate;

    @Autowired
    public EmailSender emailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Step 1 : Send OTP
    @PostMapping("/send")//send OTP//send OTP
    public Map sendEmailPassword(@RequestBody ResetPasswordModel user) {
        String message = "Thanks, please check your email";

        if (StringUtils.isEmpty(user.getEmail())) return templateCRUD.templateError("No email provided");
        User found = userRepository.findOneByUsername(user.getEmail());
        if (found == null) return templateCRUD.notFound("Email not found"); //throw new BadRequest("Email not found");

        String template = emailTemplate.getResetPassword();
        if (StringUtils.isEmpty(found.getOtp())) {
            User search;
            String otp;
            do {
                otp = SimpleStringUtils.randomString(6, true);
                search = userRepository.findOneByOTP(otp);
            } while (search != null);
            Date dateNow = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateNow);
            calendar.add(Calendar.MINUTE, expiredToken);
            Date expirationDate = calendar.getTime();

            found.setOtp(otp);
            found.setOtpExpiredDate(expirationDate);
            template = template.replaceAll("\\{\\{PASS_TOKEN}}", otp);
            template = template.replaceAll("\\{\\{USERNAME}}", (found.getUsername() == null ? "" +
                    "@UserName"
                    :
                    "@" + found.getUsername()));

            userRepository.save(found);
        } else {
            template = template.replaceAll("\\{\\{USERNAME}}", (found.getUsername() == null ? "" +
                    "@UserName"
                    :
                    "@" + found.getUsername()));
            template = template.replaceAll("\\{\\{PASS_TOKEN}}", found.getOtp());
        }
        emailSender.sendAsync(found.getUsername(), "Chute - Forget Password", template);


        return templateCRUD.templateSukses("success");

    }

    //Step 2 : CHek TOKEN OTP EMAIL
    @PostMapping("/validate")
    public Map cheKTOkenValid(@RequestBody ResetPasswordModel model) {
        if (model.getOtp() == null) return templateCRUD.notFound("Token is required");

        User user = userRepository.findOneByOTP(model.getOtp());
        if (user == null) {
            return templateCRUD.templateError("Token not valid");
        }

        return templateCRUD.templateSukses("Success");
    }

    // Step 3 : lakukan reset password baru
    @PostMapping("/change-password")
    public Map resetPassword(@RequestBody ResetPasswordModel model) {
        if (model.getOtp() == null) return templateCRUD.notFound("Token is required");
        if (model.getNewPassword() == null) return templateCRUD.notFound("New Password is required");
        User user = userRepository.findOneByOTP(model.getOtp());
        String success;
        if (user == null) return templateCRUD.notFound("Token not valid");

        user.setPassword(passwordEncoder.encode(model.getNewPassword().replaceAll("\\s+", "")));
        user.setOtpExpiredDate(null);
        user.setOtp(null);

        try {
            userRepository.save(user);
            success = "success";
        } catch (Exception e) {
            return templateCRUD.templateError("Gagal simpan user");
        }
        return templateCRUD.templateSukses(success);
    }
}
