package ua.dolofinskyi.features.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ua.dolofinskyi.security.oauth2.CustomOAuth2User;

import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserOAuth2Sub(String oauth2Sub) {
        return userRepository.findByOauth2Sub(oauth2Sub);
    }

    @Override
    public User createUser(Map<String, Object> attributes) {
        User user = new User();
        user.setUsername((String) attributes.get("name"));
        user.setEmail((String) attributes.get("email"));
        user.setOauth2Sub((String) attributes.get("sub"));
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
        userRepository.delete(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        Spliterator<User> userSpliterator = userRepository.findAll().spliterator();
        return StreamSupport.stream(userSpliterator, false)
                .map(user -> userMapper.toDto(user))
                .toList();
    }
}
