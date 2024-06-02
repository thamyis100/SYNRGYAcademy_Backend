package synrgy7thapmoch1.service;

import com.example.challenge6.entity.UserOld;

import java.util.Map;
import java.util.UUID;

public interface UserServiceOld {
    Map save(UserOld request);
    Map edit(UserOld request);
    Map delete(UUID id);
    Map getById(UUID id);
    Map pagination(int page, int size);
}
