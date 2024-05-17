package synrgy7thapmoch4.Services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import synrgy7thapmoch4.Entity.User;
import synrgy7thapmoch4.Repository.UserRepository;
import synrgy7thapmoch4.Services.UserService;
import synrgy7thapmoch4.utils.Response;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private Response response;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Map save(User request) {
        /*
        1. validasi field apa : Wajib sesuai flow bisnis
         */
        if(response.chekNull(request.getUsername())){
            return  response.error("Name is required.",402);
        }
        if(StringUtils.isEmpty(request.getUsername())){
            return  response.error("Name is required.",402);
        }

        return response.sukses(userRepository.save(request));
    }

    @Override
    public Map edit(User request) {
        try {
        /*
        1. get id  -> cehk data ke db, ?
        2. save
         */
            if(response.chekNull(request.getId())){
                return  response.error("Id is required.",402);
            }

            Optional<User> getId = userRepository.findById(request.getId());
            if(!getId.isPresent()){
                return response.error("id not found", 404);
            }

            User edit = getId.get();
            edit.setEmail(request.getEmail());
            edit.setUsername(request.getUsername());
            edit.setPassword(request.getPassword());

            return response.sukses(userRepository.save(edit));
        }catch (Exception e){
            return response.error(e.getMessage(), 500);
        }
    }

    @Override
    public Map delete(User request) {
        if (request.getId() == null) {
            return response.error("Id is required.", 402);
        }

        Optional<User> optionaluser = userRepository.findById(request.getId());
        if (optionaluser.isPresent()) {
            userRepository.delete(optionaluser.get());
            return response.sukses("user deleted successfully.");
        } else {
            return response.error("user not found.", 404);
        }
    }

    @Override
    public Map listAllUser() {
        Iterable<User> users = userRepository.findAll();
        return response.sukses(users);
    }

    @Override
    public User readUser(UUID userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        return userOptional.orElse(null);
    }
}
