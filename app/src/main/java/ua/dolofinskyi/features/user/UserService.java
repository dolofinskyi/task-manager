package ua.dolofinskyi.features.user;

import org.springframework.security.oauth2.core.user.OAuth2User;
import java.util.List;

public interface UserService {
    User getUserByOAuth2Sub(String oauth2Sub);
    User getUserFromSecurityContextHolder();
    User createUser(OAuth2User oAuth2User);
    UserDto updateUser(UserDto userDto);
    void deleteUser();
    List<UserDto> getAllUsers();
}
