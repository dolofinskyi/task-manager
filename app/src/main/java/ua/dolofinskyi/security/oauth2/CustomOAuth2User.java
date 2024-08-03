package ua.dolofinskyi.security.oauth2;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import ua.dolofinskyi.features.user.User;

import java.util.Collection;

@Getter
public class CustomOAuth2User extends DefaultOidcUser {
    private final User user;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomOAuth2User(User user,
                            Collection<? extends GrantedAuthority> authorities,
                            OidcUser oidcUser) {
        super(authorities, oidcUser.getIdToken(), oidcUser.getUserInfo());
        this.user = user;
        this.authorities = authorities;
    }

    @Override
    public String getName() {
        return user.getUsername();
    }
}
