package ua.dolofinskyi.features.task.exception;

public class TaskMissingDataException extends RuntimeException {
    public TaskMissingDataException() {
        super("Data must be provided.");
    }
}
