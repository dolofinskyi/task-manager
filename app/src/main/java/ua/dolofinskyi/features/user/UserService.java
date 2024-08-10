package ua.dolofinskyi.features.user;

import org.springframework.security.oauth2.core.user.OAuth2User;

public interface UserService {
    User save(User user);
    User getUserByOAuth2Sub(String oauth2Sub);
    User createUser(OAuth2User oAuth2User);
    UserDto updateUser(UserDto userDto);
    void deleteUser();
    UserDto getUserDtoFromSecurityContextHolder();
    User getUserFromSecurityContextHolder();
}
