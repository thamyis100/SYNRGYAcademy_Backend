package synrgy7thapmoch1.service.impl;

import com.example.challenge6.entity.UserOld;
import com.example.challenge6.repository.UserRepositoryOld;
import com.example.challenge6.service.UserServiceOld;
import com.example.challenge6.utils.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class ServiceImplUser implements UserServiceOld {

    private static final Logger logger = LoggerFactory.getLogger(ServiceImplUser.class);

    @Autowired
    private UserRepositoryOld userRepository;

    @Autowired
    private Response response;

    @Override
    public Map save(UserOld request) {
        try {
            if(response.chekNull(request.getUsername())){
                return response.error("Username is required.", 402);
            }

            if (response.chekNull(request.getEmail_address())) {
                return response.error("EmailAddress is required.", 402);
            }

            if (response.chekNull(request.getPassword())) {
                return response.error("Password status is required.", 402);
            }

            UserOld savedUser = userRepository.save(request);
            if (savedUser != null) {
                return response.sukses(savedUser);

            } else {
                return response.error("Failed to save user_id.", 500);
            }
        } catch (Exception e) {
            logger.error("Error saving user_id", e);
            return response.error("Failed to save user_id.", 500);
        }
    }

    @Override
    public Map edit(UserOld request) {
        try{
            if(request.getId() == null){
                return response.error("id_user is required.", 402);
            }

            Optional<UserOld> getUser = userRepository.findById(request.getId());
            if(getUser.isEmpty()){
                return response.error("id_user not found", 404);
            }

            UserOld editUser = getUser.get();
            if (request.getUsername() != null) {
                editUser.setUsername(request.getUsername());
            }
            if (request.getEmail_address() != null) {
                editUser.setEmail_address(request.getEmail_address());
            }
            if (request.getPassword() != null) {
                editUser.setPassword(request.getPassword());
            }

            return response.sukses(userRepository.save(editUser));
        } catch (Exception e) {
            logger.error("Error while editing user_id: {}", e.getMessage());
            return response.error("Failed to edit user_id", 500);
        }
    }

    @Override
    public Map delete(UUID id) {
        try {
            Optional<UserOld> userOptional = userRepository.findById(id);
            if (userOptional.isPresent()) {
                userRepository.delete(userOptional.get());
                return response.sukses(userOptional);
            } else {
                return response.error("User not found", 404);
            }
        } catch (Exception e) {
            logger.error("Error while deleting user_id: {}", e.getMessage());
            return response.error("Failed to delete user_id", 500);
        }
    }

    @Override
    public Map getById(UUID id) {
        try {
            Optional<UserOld> getUser = userRepository.findById(id);
            if (getUser.isPresent()) {
                return response.sukses(getUser.get());
            } else {
                return response.error("id_user not found", 404);
            }
        } catch (Exception e) {
            logger.error("Error occurred while fetching user_id by ID: {}", e.getMessage());
            return response.error("Failed to fetch user_id by ID", 500);
        }
    }

    @Override
    public Map pagination(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<UserOld> listUser = userRepository.findAll(pageable);
            return response.sukses(listUser);
        } catch (Exception e) {
            logger.error("Error occurred while paginating users: {}", e.getMessage());
            return response.error("Failed to paginate users", 500);
        }
    }


}
