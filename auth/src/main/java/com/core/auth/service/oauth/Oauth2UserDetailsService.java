package com.core.auth.service.oauth;


import com.core.auth.repository.oauth.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class Oauth2UserDetailsService implements UserDetailsService{

    @Autowired
    private IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        return userRepository.findOneByUsername(s).orElseThrow(()->new UsernameNotFoundException(String.format("Username %s is not found", s)));
    }

//    @CacheEvict("oauth_username")
//    public void clearCache(String s) {
//    }
}

