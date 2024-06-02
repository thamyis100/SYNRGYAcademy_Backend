package synrgy7thapmoch1.sp;

import com.example.challenge6.repository.UserRepositoryOld;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestingSp {
    @Autowired
    private DataSource dataSource;
    @Autowired
    public UserRepositoryOld userRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public  QuerySPUser querySPUser;
    @Before
    public void init() {
        try {
            jdbcTemplate.execute(querySPUser.listUserPaginated);
            jdbcTemplate.execute(querySPUser.saveUser);
            jdbcTemplate.execute(querySPUser.updateUser);
            jdbcTemplate.execute(querySPUser.deleteUser);
        } finally {
//            session.close();
        }
    }
}
