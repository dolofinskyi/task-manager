package ua.dolofinskyi.features.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import ua.dolofinskyi.features.user.exception.UserAlreadyExistException;
import ua.dolofinskyi.features.user.exception.UserMissingCredentialsException;
import ua.dolofinskyi.features.user.exception.UserNotAuthenticatedException;
import ua.dolofinskyi.features.user.exception.UserNotFoundException;
import ua.dolofinskyi.security.oauth2.CustomOAuth2User;

import java.util.List;
import java.util.Spliterator;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserByOAuth2Sub(String oauth2Sub) {
        if (oauth2Sub == null) {
            throw new UserMissingCredentialsException();
        }
        return userRepository.findByOauth2Sub(oauth2Sub);
    }

    @Override
    public User getUserFromSecurityContextHolder() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null ||
                !(authentication.getPrincipal() instanceof CustomOAuth2User oAuth2User)) {
            throw new UserNotAuthenticatedException();
        }
        User user = oAuth2User.getUser();
        if (user == null) {
            throw new UserNotFoundException();
        }
        return user;
    }

    @Override
    public User createUser(OAuth2User oAuth2User) {
        if (oAuth2User == null) {
            throw new UserNotFoundException();
        }
        if (oAuth2User.getAttributes() == null) {
            throw new UserMissingCredentialsException();
        }
        for (String attr: List.of("sub", "email", "name")) {
            if (oAuth2User.getAttribute(attr) == null) {
                throw new UserMissingCredentialsException();
            }
        }
        String sub = oAuth2User.getAttribute("sub");
        if (getUserByOAuth2Sub(sub) != null) {
            throw new UserAlreadyExistException();
        }
        User user = new User();
        user.setUsername(oAuth2User.getAttribute("name"));
        user.setEmail(oAuth2User.getAttribute("email"));
        user.setOauth2Sub(sub);
        return userRepository.save(user);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        if (userDto == null ||
                userDto.getEmail() == null ||
                userDto.getUsername() == null ||
                userDto.getTasks() == null) {
            throw new UserMissingCredentialsException();
        }
        User user = getUserFromSecurityContextHolder();
        user.setUsername(user.getUsername());
        user.setEmail(user.getEmail());
        user.setTasks(user.getTasks());
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    @Override
    public void deleteUser() {
        User user = getUserFromSecurityContextHolder();
        userRepository.delete(user);
    }
}
