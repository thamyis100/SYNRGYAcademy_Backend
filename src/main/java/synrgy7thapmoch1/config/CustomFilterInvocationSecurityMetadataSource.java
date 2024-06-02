package synrgy7thapmoch1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import synrgy7thapmoch1.entity.oauth.RolePath;
import synrgy7thapmoch1.repository.oauth.RolePathRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private RolePathRepository rolePathRepository;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) {
        HttpServletRequest request = ((FilterInvocation) object).getRequest();
        String httpMethod = request.getMethod();
        String requestUrl = request.getRequestURI();

        List<RolePath> rolePaths = (List<RolePath>) rolePathRepository.findAll();

        return rolePaths.stream()
                .filter(rp -> rp.getMethod().equals(httpMethod) && new AntPathRequestMatcher(rp.getPattern()).matches(request))
                .map(rp -> new SecurityConfig("" + rp.getRole()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
