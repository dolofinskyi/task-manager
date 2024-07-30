package ua.dolofinskyi.features.task;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "task")
@Getter
@Setter
public class Task {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String description;
}
