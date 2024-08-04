package ua.dolofinskyi.features.task;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ua.dolofinskyi.features.user.User;
import ua.dolofinskyi.features.user.UserServiceImpl;
import ua.dolofinskyi.security.oauth2.CustomOAuth2User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Collections;

public class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private TaskServiceImpl taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        User user = new User();
        CustomOAuth2User customOAuth2User = mock(CustomOAuth2User.class);
        when(customOAuth2User.getUser()).thenReturn(user);

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(customOAuth2User);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void testGetTaskById() {
        Task task = new Task();
        task.setId(1L);
        User user = new User();
        user.getTasks().add(task);

        when(userService.getUserFromSecurityContextHolder()).thenReturn(user);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskMapper.toDto(task)).thenReturn(new TaskDto());

        TaskDto result = taskService.getTaskById(1L);

        assertNotNull(result);
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateTask() {
        TaskDto taskDto = new TaskDto();
        taskDto.setTitle("Test Task");
        User user = new User();

        Task task = new Task();
        when(userService.getUserFromSecurityContextHolder()).thenReturn(user);
        when(taskMapper.toEntity(taskDto)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(task);
        when(taskMapper.toDto(task)).thenReturn(taskDto);

        TaskDto result = taskService.createTask(taskDto);

        assertNotNull(result);
        assertEquals(taskDto.getTitle(), result.getTitle());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void testUpdateTask() {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(1L);
        taskDto.setTitle("Updated Task");

        Task task = new Task();
        task.setId(1L);
        User user = new User();
        user.getTasks().add(task);

        when(userService.getUserFromSecurityContextHolder()).thenReturn(user);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(task)).thenReturn(task);
        when(taskMapper.toDto(task)).thenReturn(taskDto);

        TaskDto result = taskService.updateTask(taskDto);

        assertNotNull(result);
        assertEquals(taskDto.getTitle(), result.getTitle());
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void testDeleteTask() {
        Task task = new Task();
        task.setId(1L);
        User user = new User();
        user.getTasks().add(task);

        when(userService.getUserFromSecurityContextHolder()).thenReturn(user);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        taskService.deleteTask(1L);

        verify(taskRepository, times(1)).delete(task);
    }

    @Test
    void testGetAllUserTasks() {
        User user = new User();
        Task task = new Task();
        user.getTasks().add(task);

        when(userService.getUserFromSecurityContextHolder()).thenReturn(user);
        when(taskMapper.toDto(task)).thenReturn(new TaskDto());

        List<TaskDto> result = taskService.getAllUserTasks();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(userService, times(1)).getUserFromSecurityContextHolder();
    }
}