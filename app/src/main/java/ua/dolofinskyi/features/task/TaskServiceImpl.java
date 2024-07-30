package ua.dolofinskyi.features.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.dolofinskyi.features.task.exception.TaskMissingIdException;
import ua.dolofinskyi.features.task.exception.TaskNotFoundException;

import java.util.List;
import java.util.Spliterator;
import java.util.stream.StreamSupport;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private TaskRepository taskRepository;

    @Override
    public TaskDto getTaskById(Long id) {
        Task task = taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);
        return taskMapper.toDto(task);
    }

    @Override
    public TaskDto createTask(TaskDto taskDto) {
        Task task = taskMapper.toEntity(taskDto);
        Task savedTask = taskRepository.save(task);
        return taskMapper.toDto(savedTask);
    }

    @Override
    public TaskDto updateTask(TaskDto taskDto) {
        if(taskDto.getId() == null){
            throw new TaskMissingIdException();
        }
        Task targetTask = taskRepository.findById(taskDto.getId())
                .orElseThrow(TaskNotFoundException::new);
        targetTask.setTitle(taskDto.getTitle());
        targetTask.setDescription(taskDto.getDescription());
        Task updatedTask = taskRepository.save(targetTask);
        return taskMapper.toDto(updatedTask);
    }

    @Override
    public void deleteTask(Long id) {
        if(id == null){
            throw new TaskMissingIdException();
        }
        taskRepository.deleteById(id);
    }

    @Override
    public List<TaskDto> getAllTasks() {
        Spliterator<Task> taskSpliterator = taskRepository.findAll().spliterator();
        return StreamSupport.stream(taskSpliterator, false)
                .map(task -> taskMapper.toDto(task))
                .toList();
    }
}
