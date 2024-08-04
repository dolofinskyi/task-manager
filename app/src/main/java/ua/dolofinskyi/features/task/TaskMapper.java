package ua.dolofinskyi.features.task;

import org.springframework.stereotype.Component;
import ua.dolofinskyi.common.mapper.Mapper;

@Component
public class TaskMapper implements Mapper<Task, TaskDto> {
    @Override
    public Task toEntity(TaskDto taskDto) {
        Task task = new Task();
        task.setId(taskDto.getId());
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        return task;
    }

    @Override
    public TaskDto toDto(Task task) {
        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setTitle(task.getTitle());
        taskDto.setDescription(task.getDescription());
        return taskDto;
    }
}
