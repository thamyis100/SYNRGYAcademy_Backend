package synrgy7thapmoch1.entity.oauth;

import lombok.Data;

@Data
public class ResetPasswordModel {
    public String email;

    public String otp;
    public String newPassword;
}
