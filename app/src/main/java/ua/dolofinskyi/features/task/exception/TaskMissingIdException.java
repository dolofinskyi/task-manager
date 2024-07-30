package ua.dolofinskyi.features.task.exception;

public class TaskMissingIdException extends RuntimeException {
    public TaskMissingIdException() {
        super("ID must be provided.");
    }
}
