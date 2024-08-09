package ua.dolofinskyi.features.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.dolofinskyi.features.user.exception.UserAlreadyExistException;
import ua.dolofinskyi.features.user.exception.UserMissingCredentialsException;
import ua.dolofinskyi.features.user.exception.UserNotAuthenticatedException;
import ua.dolofinskyi.features.user.exception.UserNotFoundException;
import ua.dolofinskyi.security.oauth2.CustomOAuth2User;

import java.util.List;

@Service
@Transactional
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
    public User createUser(OAuth2User oAuth2User) {
        if (oAuth2User == null || oAuth2User.getAttributes() == null) {
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
                userDto.getUsername() == null) {
            throw new UserMissingCredentialsException();
        }
        User user = getUserFromSecurityContextHolder();
        user.setUsername(user.getUsername());
        user.setEmail(user.getEmail());
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    @Override
    public void deleteUser() {
        User user = getUserFromSecurityContextHolder();
        if (user == null) {
            throw new UserNotFoundException();
        }
        userRepository.delete(user);
    }

    @Override
    public UserDto getUserDtoFromSecurityContextHolder() {
        User user = getUserFromSecurityContextHolder();
        return userMapper.toDto(user);
    }

    @Override
    public User getUserFromSecurityContextHolder() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new UserNotAuthenticatedException();
        }
        Object principal = authentication.getPrincipal();
        User user = null;

        if (principal instanceof CustomOAuth2User oAuth2User) {
            user = oAuth2User.getUser();
        }
        if (principal instanceof User subject) {
            user = subject;
        }
        if (user == null) {
            throw new UserNotFoundException();
        }
        return user;
    }
}
