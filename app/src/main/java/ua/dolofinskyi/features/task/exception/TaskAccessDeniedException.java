package ua.dolofinskyi.features.task.exception;

public class TaskAccessDeniedException extends RuntimeException {
    public TaskAccessDeniedException() {
        super("Access denied.");
    }
}
