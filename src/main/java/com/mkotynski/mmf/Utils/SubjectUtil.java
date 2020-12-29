package com.mkotynski.mmf.Utils;

import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.RefreshableKeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.ServletRequest;
import java.security.Principal;
import java.util.Collection;

public class SubjectUtil {

    public static String getSubjectFromRequest(ServletRequest request){
        RefreshableKeycloakSecurityContext context = (RefreshableKeycloakSecurityContext) request.getAttribute(KeycloakSecurityContext.class.getName());

        AccessToken accessToken = context.getToken();

        return accessToken.getSubject();
    }

    private String logPrincipal(Principal principal) {
        Collection<SimpleGrantedAuthority> authorities =
                (Collection<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        System.out.println(authorities);
        System.out.println(principal);

        return principal.getName() + " " + authorities.toString();
    }

}
