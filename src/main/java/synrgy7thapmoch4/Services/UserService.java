package synrgy7thapmoch4.Services;


import synrgy7thapmoch4.Entity.User;

import java.util.Map;
import java.util.UUID;

public interface UserService {

    Map save(User request);
    Map edit(User request);
    Map delete(User request);

    Map listAllUser();

    User readUser(UUID id);
}
