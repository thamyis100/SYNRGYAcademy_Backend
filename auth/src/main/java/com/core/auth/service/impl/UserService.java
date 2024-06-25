package com.core.auth.service.impl;

import com.core.auth.service.IUserService;
import com.core.auth.service.oauth.Oauth2UserDetailsService;
import com.core.auth.dto.user.UserResponse;
import com.core.auth.entity.oauth.User;
import com.core.auth.repository.oauth.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class UserService implements IUserService {

    @Autowired
    private Oauth2UserDetailsService userDetailsService;

    @Autowired
    private IUserRepository userRepository;

    @Override
    public UserResponse getDetailProfile(Principal principal) {
        User user = getUserIdToken(principal);
        return UserResponse.builder()
                .id(user.getId())
                .fullname(user.getFullname())
                .username(user.getUsername())
                .build();
    }

    @Override
    public User getUserIdToken(Principal principal) {
        UserDetails userDetails;
        String username = principal.getName();
        if (username.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        userDetails = userDetailsService.loadUserByUsername(username);

        return userRepository.findOneByUsername(userDetails.getUsername()).orElseThrow(()->new UsernameNotFoundException("User name not found"));
    }
}
