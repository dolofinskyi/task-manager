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

import java.util.List;
import java.util.Optional;

public class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private TaskServiceImpl taskService;
    private Task initTask;
    private User initUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        initUser = new User();
        CustomOAuth2User customOAuth2User = mock(CustomOAuth2User.class);
        when(customOAuth2User.getUser()).thenReturn(initUser);

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(customOAuth2User);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        initTask = new Task();
        initTask.setId(1L);
        initTask.setTitle("Title");
        initTask.setDescription("Description");
        initTask.setIsDone(false);
        initUser.getTasks().add(initTask);
    }

    @Test
    void getTaskById() {
        TaskDto initTaskDto = new TaskDto();
        initTaskDto.setId(1L);
        initTaskDto.setTitle("Title");
        initTaskDto.setDescription("Description");
        initTaskDto.setIsDone(false);

        when(userService.getUserFromSecurityContextHolder()).thenReturn(initUser);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(initTask));
        when(taskMapper.toDto(initTask)).thenReturn(initTaskDto);

        TaskDto actual = taskService.getTaskById(1L);

        assertNotNull(actual);
        assertEquals(initTaskDto, actual);
    }

    @Test
    void createTask() {
        TaskDto initTaskDto = new TaskDto();
        initTaskDto.setId(1L);
        initTaskDto.setTitle("Title");
        initTaskDto.setDescription("Description");
        initTaskDto.setIsDone(false);

        when(userService.getUserFromSecurityContextHolder()).thenReturn(initUser);
        when(taskMapper.toEntity(initTaskDto)).thenReturn(initTask);
        when(taskRepository.save(initTask)).thenReturn(initTask);
        when(taskMapper.toDto(initTask)).thenReturn(initTaskDto);

        TaskDto actual = taskService.createTask(initTaskDto);

        assertNotNull(actual);
        assertEquals(initTaskDto.getTitle(), actual.getTitle());
        assertEquals(initTaskDto.getDescription(), actual.getDescription());
        assertEquals(initTaskDto.getIsDone(), actual.getIsDone());
    }

    @Test
    void updateTask() {
        TaskDto initTaskDto = new TaskDto();
        initTaskDto.setId(1L);
        initTaskDto.setTitle("Update Title");
        initTaskDto.setDescription("Update Description");
        initTaskDto.setIsDone(true);

        when(userService.getUserFromSecurityContextHolder()).thenReturn(initUser);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(initTask));
        when(taskRepository.save(initTask)).thenReturn(initTask);
        when(taskMapper.toDto(initTask)).thenReturn(initTaskDto);

        TaskDto actual = taskService.updateTask(initTaskDto);

        assertNotNull(actual);
        assertEquals(initTaskDto.getId(), actual.getId());
        assertEquals(initTaskDto.getTitle(), actual.getTitle());
        assertEquals(initTaskDto.getDescription(), actual.getDescription());
        assertEquals(initTaskDto.getIsDone(), actual.getIsDone());
    }

    @Test
    void deleteTask() {
        when(userService.getUserFromSecurityContextHolder()).thenReturn(initUser);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(initTask));
        taskService.deleteTask(1L);
        verify(taskRepository, times(1)).delete(initTask);
    }

    @Test
    void testGetAllUserTasks() {
        when(userService.getUserFromSecurityContextHolder()).thenReturn(initUser);
        when(taskMapper.entitiesToDtos(anyList())).thenReturn(List.of(new TaskDto()));

        List<TaskDto> result = taskService.getAllUserTasks();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        verify(userService, times(1)).getUserFromSecurityContextHolder();
    }
}