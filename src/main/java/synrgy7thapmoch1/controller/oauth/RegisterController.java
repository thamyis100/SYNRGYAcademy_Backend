package synrgy7thapmoch1.controller.oauth;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import synrgy7thapmoch1.config.Config;
import synrgy7thapmoch1.entity.oauth.User;
import synrgy7thapmoch1.repository.oauth.UserRepository;
import synrgy7thapmoch1.req.RegisterModel;
import synrgy7thapmoch1.service.email.EmailSender;
import synrgy7thapmoch1.service.oauth.UserService;
import synrgy7thapmoch1.utils.EmailTemplate;
import synrgy7thapmoch1.utils.Response;
import synrgy7thapmoch1.utils.SimpleStringUtils;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user-register/")
public class RegisterController {
    @Autowired
    private UserRepository userRepository;

    Config config = new Config();

    @Autowired
    public UserService serviceReq;

    @Autowired
    public Response templateCRUD;

    @Autowired
    public EmailSender emailSender;
    @Autowired
    public EmailTemplate emailTemplate;

    @Value("${expired.token.password.minute:}")//FILE_SHOW_RUL
    private int expiredToken;

    @Value("${BASEURL:}")//FILE_SHOW_RUL
    private String BASEURL;

    @PostMapping("/register")
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map> saveRegisterManual(@Valid @RequestBody RegisterModel objModel) throws RuntimeException {
        Map map = new HashMap();

        User user = userRepository.checkExistingEmail(objModel.getUsername());
        if (null != user) {
            return new ResponseEntity<Map>(templateCRUD.templateError("Username sudah ada"), HttpStatus.OK);

        }
        map = serviceReq.registerManual(objModel);

        return new ResponseEntity<Map>(map, HttpStatus.OK);
    }

    @PostMapping("/register-google")
    public ResponseEntity<Map> saveRegisterByGoogle(@Valid @RequestBody RegisterModel objModel) throws RuntimeException {
        Map map = new HashMap();

        User user = userRepository.checkExistingEmail(objModel.getUsername());
        if (null != user) {
            return new ResponseEntity<Map>(templateCRUD.templateError("Username sudah ada"), HttpStatus.OK);

        }
        map = serviceReq.registerByGoogle(objModel);
        //gunanya send email
        Map mapRegister =  sendEmailegister(objModel);
        return new ResponseEntity<Map>(mapRegister, HttpStatus.OK);

    }

    // Step 2: sendp OTP berupa URL: guna updeta enable agar bisa login:
    @PostMapping("/send-otp")//send OTP
    public Map sendEmailegister(
            @RequestBody RegisterModel user) {
        String message = "Thanks, please check your email for activation.";

        if (user.getUsername() == null) return templateCRUD.templateSukses("No email provided");
        User found = userRepository.findOneByUsername(user.getUsername());
        if (found == null) return templateCRUD.templateError("Email not found"); //throw new BadRequest("Email not found");

        String template = emailTemplate.getRegisterTemplate();
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
            template = template.replaceAll("\\{\\{USERNAME}}", (found.getFullname() == null ? found.getUsername() : found.getFullname()));
            template = template.replaceAll("\\{\\{VERIFY_TOKEN}}",  otp);
            userRepository.save(found);
        } else {
            template = template.replaceAll("\\{\\{USERNAME}}", (found.getFullname() == null ? found.getUsername() : found.getFullname()));
            template = template.replaceAll("\\{\\{VERIFY_TOKEN}}",  found.getOtp());
        }
        emailSender.sendAsync(found.getUsername(), "Register", template);
        return templateCRUD.templateSukses(message);
    }

    @GetMapping("/register-confirm-otp/{token}")
    public ResponseEntity<Map> saveRegisterManual(@PathVariable(value = "token") String tokenOtp) throws RuntimeException {
        User user = userRepository.findOneByOTP(tokenOtp);
        if (null == user) {
            return new ResponseEntity<Map>(templateCRUD.templateError("OTP tidak ditemukan"), HttpStatus.OK);
        }
//validasi jika sebelumnya sudah melakukan aktifasi

        if(user.isEnabled()){
            return new ResponseEntity<Map>(templateCRUD.templateSukses("Akun Anda sudah aktif, Silahkan melakukan login"), HttpStatus.OK);
        }
        String today = config.convertDateToString(new Date());

        String dateToken = config.convertDateToString(user.getOtpExpiredDate());
        if(Long.parseLong(today) > Long.parseLong(dateToken)){
            return new ResponseEntity<Map>(templateCRUD.templateError("Your token is expired. Please Get token again."), HttpStatus.OK);
        }
        //update user
        user.setEnabled(true);
        userRepository.save(user);

        return new ResponseEntity<Map>(templateCRUD.templateSukses("Sukses, Silahkan Melakukan Login"), HttpStatus.OK);
    }

    @PostMapping("/send-otp-tymeleaf")//send OTP
    public Map sendEmailegisterTymeleaf(@RequestBody RegisterModel user) {
        String message = "Thanks, please check your email for activation.";

        if (user.getUsername() == null) return templateCRUD.templateError("No email provided");
        User found = userRepository.findOneByUsername(user.getUsername());
        if (found == null) return templateCRUD.templateError("Email not found"); //throw new BadRequest("Email not found");

        String template = emailTemplate.getRegisterTemplate();
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
            template = template.replaceAll("\\{\\{USERNAME}}", (found.getFullname() == null ? found.getUsername() : found.getFullname()));
            template = template.replaceAll("\\{\\{VERIFY_TOKEN}}", BASEURL + "/v1/user-register/web/index/"+ otp);
            userRepository.save(found);
        } else {
            template = template.replaceAll("\\{\\{USERNAME}}", (found.getFullname() == null ? found.getUsername() : found.getFullname()));
            template = template.replaceAll("\\{\\{VERIFY_TOKEN}}", BASEURL + "/v1/user-register/web/index/"+  found.getOtp());
        }
        emailSender.sendAsync(found.getUsername(), "Register", template);
        return templateCRUD.templateSukses(message);
    }

    @PostMapping("/register-google-tymeleaf")
    public ResponseEntity<Map> saveRegisterByGoogleTyemeleaf(@Valid @RequestBody RegisterModel objModel) throws RuntimeException {
        Map map = new HashMap();

        User user = userRepository.checkExistingEmail(objModel.getUsername());
        if (null != user) {
            return new ResponseEntity<Map>(templateCRUD.templateError("Username sudah ada"), HttpStatus.OK);

        }
        map = serviceReq.registerByGoogle(objModel);
        //gunanya send email
        Map mapRegister =  sendEmailegisterTymeleaf(objModel);
        return new ResponseEntity<Map>(mapRegister, HttpStatus.OK);
    }


}
