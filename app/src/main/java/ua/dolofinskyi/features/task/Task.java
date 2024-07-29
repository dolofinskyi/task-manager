package ua.dolofinskyi.features.task;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue
    @Getter
    private long id;
    @Getter
    @Setter
    private String title;
    @Getter
    @Setter
    private String description;
}
