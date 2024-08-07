package ua.dolofinskyi.features.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

        for (Task task: user.getTasks()) {
            if (task.getId().equals(id)) {
                return taskMapper.toDto(task);
            }
        }

        throw new TaskNotFoundException();
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

        for (Task task: user.getTasks()) {
            if (task.getId().equals(taskDto.getId())) {
                task.setTitle(taskDto.getTitle());
                task.setDescription(taskDto.getDescription());
                task.setIsDone(taskDto.getIsDone());
                Task updatedTask = taskRepository.save(task);
                return taskMapper.toDto(updatedTask);
            }
        }

        throw new TaskNotFoundException();
    }

    @Override
    public void deleteTask(Long id) {
        if(id == null){
            throw new TaskMissingDataException();
        }

        User user = userService.getUserFromSecurityContextHolder();

        for (Task task: user.getTasks()) {
            if (task.getId().equals(id)) {
                taskRepository.delete(task);
                user.getTasks().remove(task);
                return;
            }
        }

        throw new TaskNotFoundException();
    }

    @Override
    public List<TaskDto> getAllUserTasks() {
        return userService.getUserFromSecurityContextHolder().getTasks().stream()
                .map(task -> taskMapper.toDto(task))
                .toList();
    }
}
