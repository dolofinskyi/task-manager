package ua.dolofinskyi.features.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskServiceImpl taskService;

    @GetMapping("/get")
    public TaskDto getTaskById(@RequestParam Long id) {
        return taskService.getTaskById(id);
    }

    @PostMapping("/create")
    public TaskDto createTask(@RequestBody TaskDto taskDto) {
        return taskService.createTask(taskDto);
    }

    @PutMapping("/update")
    public TaskDto updateTask(@RequestBody TaskDto taskDto) {
        return taskService.updateTask(taskDto);
    }

    @DeleteMapping("/delete")
    public void deleteTask(@RequestParam Long id) {
        taskService.deleteTask(id);
    }

    @GetMapping("/all")
    public List<TaskDto> getAllTasks() {
        return taskService.getAllTasks();
    }
}
