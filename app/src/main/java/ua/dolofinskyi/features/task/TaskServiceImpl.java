package ua.dolofinskyi.features.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.dolofinskyi.features.task.exception.TaskAccessDeniedException;
import ua.dolofinskyi.features.task.exception.TaskMissingDataException;
import ua.dolofinskyi.features.task.exception.TaskNotFoundException;
import ua.dolofinskyi.features.user.User;
import ua.dolofinskyi.features.user.UserRepository;
import ua.dolofinskyi.features.user.UserServiceImpl;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private UserRepository userRepository;

    @Override
    public TaskDto getTaskById(Long id) {
        if(id == null) {
            throw new TaskMissingDataException();
        }
        User user = userService.getUserFromSecurityContextHolder();
        Task task = taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
        if (!user.getTasks().contains(task)) {
            throw new TaskAccessDeniedException();
        }
        return taskMapper.toDto(task);
    }

    @Override
    public TaskDto createTask(TaskDto taskDto) {
        if (taskDto == null ||
                taskDto.getTitle() == null ||
                taskDto.getIsDone() == null) {
            throw new TaskMissingDataException();
        }
        User user = userService.getUserFromSecurityContextHolder();
        Task task = taskMapper.toEntity(taskDto);
        task.setUser(user);
        Task savedTask = taskRepository.save(task);
        user.getTasks().add(savedTask);
        return taskMapper.toDto(savedTask);
    }

    @Override
    public TaskDto updateTask(TaskDto taskDto) {
        if(taskDto == null ||
                taskDto.getId() == null ||
                taskDto.getTitle() == null ||
                taskDto.getIsDone() == null){
            throw new TaskMissingDataException();
        }
        User user = userService.getUserFromSecurityContextHolder();
        Task targetTask = taskRepository.findById(taskDto.getId())
                .orElseThrow(TaskNotFoundException::new);
        if (!user.getTasks().contains(targetTask)) {
            throw new TaskAccessDeniedException();
        }
        targetTask.setTitle(taskDto.getTitle());
        targetTask.setDescription(taskDto.getDescription());
        Task updatedTask = taskRepository.save(targetTask);
        return taskMapper.toDto(updatedTask);
    }

    @Override
    public void deleteTask(Long id) {
        if(id == null){
            throw new TaskMissingDataException();
        }
        User user = userService.getUserFromSecurityContextHolder();
        Task task = taskRepository.findById(id)
                .orElseThrow(TaskNotFoundException::new);
        if (!user.getTasks().contains(task)) {
            throw new TaskAccessDeniedException();
        }
        taskRepository.delete(task);
    }

    @Override
    public List<TaskDto> getAllUserTasks() {
        User user = userService.getUserFromSecurityContextHolder();
        return taskMapper.entitiesToDtos(user.getTasks());
    }
}
