package synrgy7thapmoch4.Testing;

import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import synrgy7thapmoch4.Entity.Employee;
import synrgy7thapmoch4.Services.EmployeeService;

import java.util.Map;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestingEmployee {

    @Autowired
    private TestRestTemplate restTemplate;


    @Autowired
    public EmployeeService employeeService;

    @Test
    public void saveEmployee(){
        Employee save = new Employee();
        save.setName("Aldi");
        save.setAddress("Jakarta");
        save.setStatus("active");

       Map map =  employeeService.save(save);
        int responseCode = (Integer) map.get("status");
        Assert.assertEquals(200, responseCode);

    }


    @Test
    public void listSukses() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "*/*");
        headers.set("Content-Type", "application/json");


        ResponseEntity<Object> exchange = restTemplate.exchange("http://dev.farizdotid.com/api/daerahindonesia/provinsi", HttpMethod.GET, null, Object.class);
        System.out.println("response  =" + exchange.getBody());

        // get value
        assertEquals(HttpStatus.OK, exchange.getStatusCode());
    }
    @Test
    public void listSuksesEmployee() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "*/*");
        headers.set("Content-Type", "application/json");


        ResponseEntity<Object> exchange = restTemplate.exchange("http://localhost:8080/v1/employee/list-employee", HttpMethod.GET, null, Object.class);
        System.out.println("response  =" + exchange.getBody());

        // get value
        assertEquals(HttpStatus.OK, exchange.getStatusCode());
    }
    @Test
    public void listSuksesMerchant() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "*/*");
        headers.set("Content-Type", "application/json");


        ResponseEntity<Object> exchange = restTemplate.exchange("http://localhost:8080/v1/merchant/list-merchant", HttpMethod.GET, null, Object.class);
        System.out.println("response  =" + exchange.getBody());

        // get value
        assertEquals(HttpStatus.OK, exchange.getStatusCode());
    }



}
