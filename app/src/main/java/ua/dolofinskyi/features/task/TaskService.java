package ua.dolofinskyi.features.task;

import java.util.List;

public interface TaskService {
    TaskDto getTaskById(Long id);
    TaskDto createTask(TaskDto taskDto);
    TaskDto updateTask(TaskDto taskDto);
    void deleteTask(Long id);
    List<TaskDto> getAllUserTasks();
    List<TaskDto> getAllTasks();
}
