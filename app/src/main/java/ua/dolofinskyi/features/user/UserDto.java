package ua.dolofinskyi.features.user;

import lombok.Getter;
import lombok.Setter;
import ua.dolofinskyi.features.task.TaskDto;

import java.util.List;
@Getter
@Setter
public class UserDto {
    private String username;
    private String email;
    private List<TaskDto> tasks;
}
