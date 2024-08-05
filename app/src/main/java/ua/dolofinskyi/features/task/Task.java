package ua.dolofinskyi.features.task;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ua.dolofinskyi.features.user.User;

@Entity
@Table(name = "task")
@Getter
@Setter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private Boolean isDone;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
