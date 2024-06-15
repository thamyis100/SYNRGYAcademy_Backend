package com.core.auth.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(securedEnabled = true) //secure definition
public class Oauth2ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    private final String[] PREDEFINED_USER_URI = new String[]{
            "/ping",
            "/swagger-ui/**",
            "/v2/api-docs/**",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/configuration/ui",
            "/swagger-resources/configuration/security",
            "/user-register/**",
            "/auth/**"
    };

    /**
     * Manage resource server.
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        super.configure(resources);
    }
//    private static final String SECURED_PATTERN = "/api/**";
    /**
     * Manage endpoints.
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .csrf()
                .disable()
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers(PREDEFINED_USER_URI)
                .permitAll()
//                .antMatchers(HttpMethod.GET, WHITELIST_URI)
//                .permitAll()
//                .antMatchers(USER_ROLE_URI).hasAuthority("ROLE_USER")
//                .antMatchers(HttpMethod.POST, ADMIN_ROLE_URI).hasAuthority("ROLE_ADMIN")
//                .antMatchers(HttpMethod.PUT, ADMIN_ROLE_URI).hasAuthority("ROLE_ADMIN")
//                .antMatchers(HttpMethod.PATCH, ADMIN_ROLE_URI).hasAuthority("ROLE_ADMIN")
//                .antMatchers(HttpMethod.DELETE, ADMIN_ROLE_URI).hasAuthority("ROLE_ADMIN")
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .permitAll()
        ;
    }
}

