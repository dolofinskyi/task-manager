package ua.dolofinskyi.features.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private TaskRepository taskRepository;

    @Override
    public TaskDto createTask(TaskDto taskDto) {
        Task task = taskMapper.toEntity(taskDto);
        Task savedTask = taskRepository.save(task);
        return taskMapper.toDto(savedTask);
    }

    @Override
    public TaskDto updateTask(TaskDto taskDto) {
        if(taskDto.getId() == null){
            throw new IllegalArgumentException("ID must be provided.");
        }
        Task targetTask = taskRepository.findById(taskDto.getId())
                .orElseThrow(() -> new RuntimeException("Task not found"));
        targetTask.setTitle(taskDto.getTitle());
        targetTask.setDescription(taskDto.getDescription());
        Task updatedTask = taskRepository.save(targetTask);
        return taskMapper.toDto(updatedTask);
    }

    @Override
    public void deleteTask(Long id) {
        if(id == null){
            throw new IllegalArgumentException("ID must be provided.");
        }
        taskRepository.deleteById(id);
    }
}
