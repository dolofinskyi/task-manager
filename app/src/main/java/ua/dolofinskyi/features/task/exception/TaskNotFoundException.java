package ua.dolofinskyi.features.task.exception;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException() {
        super("Task not found.");
    }
}
