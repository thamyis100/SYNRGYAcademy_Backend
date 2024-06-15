package synrgy7thapmoch1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(securedEnabled = true) //secure definition
public class Oauth2ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    @Autowired
    private CustomFilterInvocationSecurityMetadataSource securityMetadataSource;

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
                .antMatchers("/","/showFile/**","/v1/showFile/**","/v1/upload", "/user-register/**","/swagger-ui/**","/swagger-ui.html","/v3/api-docs/**","/user-login/**",
                        "/forget-password/**", "/oauth/authorize**", "/login**", "/error**", "/v1/user-register/**", "/user-login/**")
                .permitAll()
                .antMatchers("/v1/role-test-global/list-barang").hasAnyAuthority("ROLE_READ")
                .antMatchers("/v1/role-test-global/post-barang").hasAnyAuthority("ROLE_WRITE")
                .antMatchers("/v1/role-test-global/post-barang-user").hasAnyAuthority("ROLE_USER")
                .antMatchers("/v1/role-test-global/post-barang-admin").hasAnyAuthority("ROLE_ADMIN")
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .permitAll()
        ;
    }
    private ObjectPostProcessor<FilterSecurityInterceptor> objectPostProcessor() {
        return new ObjectPostProcessor<FilterSecurityInterceptor>() {
            @Override
            public <O extends FilterSecurityInterceptor> O postProcess(O fsi) {
                fsi.setSecurityMetadataSource(securityMetadataSource);
                return fsi;
            }
        };
    }
}
