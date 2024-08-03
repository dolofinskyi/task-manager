package ua.dolofinskyi.features.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
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
    public UserDto getUserById(Long id) {
        User user = userRepository
                .findById(id)
                .orElseThrow(UserNotFoundException::new);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto getUserOAuth2Sub(String oauth2Sub) {
        User user = userRepository.findByOauth2Sub(oauth2Sub);
        return userMapper.toDto(user);
    }

    @Override
    public User createUser(OAuth2User oAuth2User) {
        User user = new User();
        user.setUsername(oAuth2User.getAttribute("name"));
        user.setEmail(oAuth2User.getAttribute("email"));
        user.setOauth2Sub(oAuth2User.getAttribute("sub"));
        return userRepository.save(user);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        User user = oAuth2User.getUser();
        user.setUsername(user.getUsername());
        user.setEmail(user.getEmail());
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    @Override
    public void deleteUser() {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        User user = oAuth2User.getUser();
        userRepository.deleteById(user.getId());
    }

    @Override
    public List<UserDto> getAllUsers() {
        Spliterator<User> userSpliterator = userRepository.findAll().spliterator();
        return StreamSupport.stream(userSpliterator, false)
                .map(user -> userMapper.toDto(user))
                .toList();
    }
}
