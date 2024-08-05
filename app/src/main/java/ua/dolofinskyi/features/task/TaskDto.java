package ua.dolofinskyi.features.task;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private Boolean isDone;
}
