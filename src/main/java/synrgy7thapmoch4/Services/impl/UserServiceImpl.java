package synrgy7thapmoch4.Services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import synrgy7thapmoch4.Entity.User;
import synrgy7thapmoch4.Repository.UserRepository;
import synrgy7thapmoch4.Services.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public void createUser(Long userId, String userName, String userEmail) {
        userRepository.createUserSP(userId, userName, userEmail);
    }

    public User readUser(Long userId) {
        return userRepository.readUserSP(userId);
    }

    public void updateUser(Long userId, String userName, String userEmail) {
        userRepository.updateUserSP(userId, userName, userEmail);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteUserSP(userId);
    }
}
