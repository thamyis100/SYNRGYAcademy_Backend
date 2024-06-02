package synrgy7thapmoch1.testing;

import com.example.challenge6.entity.UserOld;
import com.example.challenge6.service.impl.ServiceImplUser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestingUser {

    @Autowired
    private ServiceImplUser userService;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void saveUser() {
        UserOld save = new UserOld();
        save.setUsername("test_user");
        save.setEmail_address("test@example.com");
        save.setPassword("password");

        Map userSaveResponse = userService.save(save);
        int userSaveStatusCode = (int) userSaveResponse.get("status");
        Assert.assertEquals(200, userSaveStatusCode);
    }

    @Test
    public void editUser() {
        UUID userId = UUID.fromString("9bca81d7-cb36-4fad-8e86-0ca0092de5df");

        Map<String, Object> getUserResponse = userService.getById(userId);
        int getUserStatusCode = (int) getUserResponse.get("status");
        Assert.assertEquals(200, getUserStatusCode);

        if (getUserStatusCode == 200) {
            UserOld existingUser = (UserOld) getUserResponse.get("data");
            existingUser.setUsername("new_username");
            existingUser.setEmail_address("new_email@example.com");
            existingUser.setPassword("new_password");

            Map userUpdateResponse = userService.edit(existingUser);
            int userUpdateStatusCode = (int) userUpdateResponse.get("status");
            Assert.assertEquals(200, userUpdateStatusCode);
        }
    }

    @Test
    public void deleteUser() {
        UUID userId = UUID.fromString("9bca81d7-cb36-4fad-8e86-0ca0092de5df");

        Map userDeleteResponse = userService.delete(userId);
        int userDeleteStatusCode = (int) userDeleteResponse.get("status");
        Assert.assertEquals(200, userDeleteStatusCode);
    }

    @Test
    public void getUserById() {
        UUID userId = UUID.fromString("9bca81d7-cb36-4fad-8e86-0ca0092de5df");

        Map<String, Object> getUserResponse = userService.getById(userId);
        int getUserStatusCode = (int) getUserResponse.get("status");
        Assert.assertEquals(200, getUserStatusCode);
    }

    @Test
    public void listSuksesJSONObject() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "*/*");
        headers.set("Content-Type", "application/json");

        ResponseEntity<String> exchange = restTemplate.exchange(
                "http://localhost:8082/v1/user/list",
                HttpMethod.GET,
                null,
                String.class
        );

        String responseBody = exchange.getBody();
        if (responseBody != null) {
            JSONObject jsonResponse = new JSONObject(responseBody);
            JSONObject data = jsonResponse.getJSONObject("data");
            JSONArray content = data.getJSONArray("content");

            for (int i = 0; i < content.length(); i++) {
                JSONObject userObject = content.getJSONObject(i);
                String idString = userObject.getString("id");
                UUID id = UUID.fromString(idString);
                String username = userObject.getString("username");
                String email_address = userObject.getString("email_address");

                System.out.println("Id: " + id + ", Username: " + username + ", Email Address: " + email_address);
            }
        }
    }
}
