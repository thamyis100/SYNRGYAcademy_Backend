package com.core.auth.service.oauth;


import com.core.auth.entity.oauth.Client;
import com.core.auth.entity.oauth.Role;
import com.core.auth.entity.oauth.RolePath;
import com.core.auth.entity.oauth.User;
import com.core.auth.entity.oauth.*;
import com.core.auth.repository.oauth.IClientRepository;
import com.core.auth.repository.oauth.IRolePathRepository;
import com.core.auth.repository.oauth.IRoleRepository;
import com.core.auth.repository.oauth.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
@Service
public class DatabaseSeeder implements ApplicationRunner {

    private static final String TAG = "DatabaseSeeder {}";

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private IClientRepository clientRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRolePathRepository rolePathRepository;

    private final String defaultPassword = "password";

    private final String[] users = new String[]{
            "admin@mail.com:ROLE_SUPERUSER ROLE_USER ROLE_ADMIN",
            "user@mail.com:ROLE_USER",
            "badimtna@gmail.com:ROLE_USER ROLE_READ ROLE_WRITE"
    };

    private final String[] clients = new String[]{
            "my-client-apps:ROLE_READ ROLE_WRITE", // mobile
            "my-client-web:ROLE_READ ROLE_WRITE" // web
    };

    private final String[] roles = new String[] {
            "ROLE_SUPERUSER:user_role:^/.*:GET|PUT|POST|PATCH|DELETE|OPTIONS",
            "ROLE_ADMIN:user_role:^/.*:GET|PUT|POST|PATCH|DELETE|OPTIONS",
            "ROLE_USER:user_role:^/.*:GET|PUT|POST|PATCH|DELETE|OPTIONS",
            "ROLE_READ:oauth_role:^/.*:GET|PUT|POST|PATCH|DELETE|OPTIONS",
            "ROLE_WRITE:oauth_role:^/.*:GET|PUT|POST|PATCH|DELETE|OPTIONS"
    };


    @Override
    @Transactional
    public void run(ApplicationArguments applicationArguments) {
        String password = encoder.encode(defaultPassword);

        this.insertRoles();
        this.insertClients(password);
        this.insertUser(password);
    }

    @Transactional
    public void insertRoles() {
        for (String role: roles) {
            String[] str = role.split(":");
            String name = str[0];
            String type = str[1];
            String pattern = str[2];
            String[] methods = str[3].split("\\|");
            Role oldRole = roleRepository.findOneByName(name);
            if (null == oldRole) {
                oldRole = new Role();
                oldRole.setName(name);
                oldRole.setType(type);
                oldRole.setRolePaths(new ArrayList<>());
                for (String m: methods) {
                    String rolePathName = name.toLowerCase()+"_"+m.toLowerCase();
                    RolePath rolePath = rolePathRepository.findOneByName(rolePathName);
                    if (null == rolePath) {
                        rolePath = new RolePath();
                        rolePath.setName(rolePathName);
                        rolePath.setMethod(m.toUpperCase());
                        rolePath.setPattern(pattern);
                        rolePath.setRole(oldRole);
                        rolePathRepository.save(rolePath);
                        oldRole.getRolePaths().add(rolePath);
                    }
                }
            }

            roleRepository.save(oldRole);
        }
    }

    @Transactional
    public void insertClients(String password) {
        for (String c: clients) {
            String[] s = c.split(":");
            String clientName = s[0];
            String[] clientRoles = s[1].split("\\s");
            Client oldClient = clientRepository.findOneByClientId(clientName);
            if (null == oldClient) {
                oldClient = new Client();
                oldClient.setClientId(clientName);
                oldClient.setAccessTokenValiditySeconds(28800);//1 jam 3600 :token valid : seharian kerja : normal 1 jam
                oldClient.setRefreshTokenValiditySeconds(7257600);// refresh
                oldClient.setGrantTypes("password refresh_token authorization_code");
                oldClient.setClientSecret(password);
                oldClient.setApproved(true);
                oldClient.setRedirectUris("");
                oldClient.setScopes("read write");
                List<Role> rls = roleRepository.findByNameIn(clientRoles);

                if (rls.size() > 0) {
                    oldClient.getAuthorities().addAll(rls);
                }
            }
            clientRepository.save(oldClient);
        }
    }

    @Transactional
    public void insertUser(String password) {
        for (String userNames: users) {
            String[] str = userNames.split(":");
            String username = str[0];
            String[] roleNames = str[1].split("\\s");

            User oldUser = userRepository.findOneByUsername(username).orElseGet(()->{
                User user = new User();
                user.setUsername(username);
                user.setPassword(password);
                List<Role> r = roleRepository.findByNameIn(roleNames);
                user.setRoles(r);
                return user;
            });

            userRepository.save(oldUser);
        }
    }
}

