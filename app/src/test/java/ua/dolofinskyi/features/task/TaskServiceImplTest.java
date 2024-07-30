package ua.dolofinskyi.features.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("h2")
class TaskServiceImplTest {
    @Autowired
    private TaskServiceImpl taskService;
    private TaskDto initTask;

    @BeforeEach
    void setUp() {
        initTask = new TaskDto();
        initTask.setTitle("Title");
        initTask.setDescription("Description");
    }

    @Test
    void testGetTaskById() {
        TaskDto createdTask = taskService.createTask(initTask);
        TaskDto foundTask = taskService.getTaskById(createdTask.getId());
        assertEquals(createdTask.getId(), foundTask.getId());
    }

    @Test
    void testCreateTask() {
        TaskDto createdTask = taskService.createTask(initTask);
        assertEquals(initTask.getTitle(), createdTask.getTitle());
        assertEquals(initTask.getDescription(), createdTask.getDescription());
    }

    @Test
    void testUpdateTask() {
        TaskDto createdTask = taskService.createTask(initTask);
        TaskDto updatedTask = taskService.updateTask(createdTask);
        assertEquals(createdTask.getId(), updatedTask.getId());
        assertEquals(createdTask.getTitle(), updatedTask.getTitle());
        assertEquals(createdTask.getDescription(), updatedTask.getDescription());
    }

    @Test
    void testDeleteTask() {
        TaskDto createdTask = taskService.createTask(initTask);
        assertDoesNotThrow(() -> taskService.deleteTask(createdTask.getId()));
    }

    @Test
    void testGetAllTasks() {
        int length = 5;

        for(int i = 0; i < length; i++) {
            taskService.createTask(initTask);
        }

        List<TaskDto> allTasks = taskService.getAllTasks();
        assertEquals(length, allTasks.size());
    }
}