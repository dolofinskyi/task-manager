package ua.dolofinskyi.features.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ua.dolofinskyi.features.task.Task;

import java.util.Set;

@Entity
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;
    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private String oauth2Sub;
    @Getter
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Task> tasks;
}
