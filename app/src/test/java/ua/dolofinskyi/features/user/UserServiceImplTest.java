package ua.dolofinskyi.features.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import ua.dolofinskyi.features.task.Task;
import ua.dolofinskyi.features.user.exception.UserNotFoundException;
import ua.dolofinskyi.security.oauth2.CustomOAuth2User;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserMapper userMapper;
    @Mock
    private UserRepository userRepository;
    private User initUser;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        initUser = new User();
        initUser.setUsername("username");
        initUser.setEmail("email");
        initUser.setRoles(List.of(User.Role.ROLE_USER));
        initUser.setOauth2Sub("google_sub_0");

        CustomOAuth2User customOAuth2User = mock(CustomOAuth2User.class);
        when(customOAuth2User.getUser()).thenReturn(initUser);

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(customOAuth2User);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    @Test
    void getUserByOAuth2Sub() {
        when(userRepository.findByOauth2Sub(initUser.getOauth2Sub())).thenReturn(initUser);
        User actual = userService.getUserByOAuth2Sub(initUser.getOauth2Sub());
        assertEquals(initUser, actual);
    }

    @Test
    void getUserFromSecurityContextHolder() {
        User actual = userService.getUserFromSecurityContextHolder();
        assertEquals(initUser, actual);
    }

    @Test
    void createUser() {
        when(userRepository.save(any(User.class))).thenReturn(initUser);

        OAuth2User oAuth2User = new DefaultOAuth2User(
                List.of(new SimpleGrantedAuthority(User.Role.ROLE_USER.name())),
                Map.of("email", "email",
                        "sub", "google_sub_0",
                        "name", "username"),
                "sub"
        );

        User actual = userService.createUser(oAuth2User);
        assertEquals(initUser, actual);
    }

    @Test
    void updateUser() {
        UserDto userDto = new UserDto();
        userDto.setUsername("update username");
        userDto.setEmail("update email");
        userDto.setTasks(List.of(new Task()));

        when(userService.getUserFromSecurityContextHolder()).thenReturn(initUser);
        when(userMapper.toDto(any(User.class))).thenReturn(userDto);
        when(userRepository.save(any(User.class))).thenReturn(initUser);

        UserDto actual = userService.updateUser(userDto);
        assertEquals(userDto, actual);
    }

    @Test
    void deleteUser() {
        when(userService.getUserFromSecurityContextHolder()).thenReturn(initUser);
        userService.deleteUser();
        verify(userRepository, times(1)).delete(initUser);
    }

    @Test
    void deleteUserNotFound() {
        when(userService.getUserFromSecurityContextHolder()).thenReturn(null);
        assertThrows(UserNotFoundException.class, () -> userService.deleteUser());
    }
}