package ua.dolofinskyi.features.user;

import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.List;
import java.util.Map;

public interface UserService {
    User getUserOAuth2Sub(String oauth2Sub);
    User createUser(Map<String, Object> attributes);
    UserDto updateUser(UserDto userDto);
    void deleteUser();
    List<UserDto> getAllUsers();
}
