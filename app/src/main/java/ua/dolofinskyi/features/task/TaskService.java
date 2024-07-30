package ua.dolofinskyi.features.task;

public interface TaskService {
    TaskDto createTask(TaskDto taskDto);
    TaskDto updateTask(TaskDto taskDto);
    void deleteTask(Long id);
}
