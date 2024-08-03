package ua.dolofinskyi.features.user;

import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.List;

public interface UserService {
    UserDto getUserById(Long id);
    UserDto getUserOAuth2Sub(String oauth2Sub);
    User createUser(OAuth2User oAuth2User);
    UserDto updateUser(UserDto userDto);
    void deleteUser();
    List<UserDto> getAllUsers();
}
