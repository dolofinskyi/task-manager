package ua.dolofinskyi.security.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;

import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import ua.dolofinskyi.features.user.User;
import ua.dolofinskyi.features.user.UserServiceImpl;

import java.util.Collection;
import java.util.List;


@Service
public class CustomOAuth2UserService extends OidcUserService {
    @Autowired
    private UserServiceImpl userService;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);
        String sub = oidcUser.getAttribute("sub");
        User user = userService.getUserByOAuth2Sub(sub);

        if (user == null) {
            user = userService.createUser(oidcUser);
        }

        List<User.Role> roles = List.of(
                User.Role.ROLE_USER
        );

        user.setRoles(roles);

        Collection<? extends GrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name())).toList();

        return new CustomOAuth2User(user, authorities, oidcUser);
    }
}
