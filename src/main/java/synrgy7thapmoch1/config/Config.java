package synrgy7thapmoch1.config;

import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

@Component
public class Config {

    public  static String APP_NAME ="Challenge6";

    public static  String yourName(){
        return "aqilah";
    }

    public String convertDateToString(Date date) {

        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String strDate = dateFormat.format(date);
        return strDate;
    }

    public boolean isValidEmail(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        return pat.matcher(email).matches();
    }

}


