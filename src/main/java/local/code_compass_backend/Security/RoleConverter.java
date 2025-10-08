package local.code_compass_backend.Security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Map<String, Object> appMetadata = jwt.getClaim("app_metadata");

        if (appMetadata != null) {
            String role = (String) appMetadata.get("role");
            if (role != null) {
                return List.of(new SimpleGrantedAuthority("ROLE_" + role));
            }
        }

        return Collections.emptyList();
    }
}
