package ua.dolofinskyi.features.task;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ua.dolofinskyi.common.response.Response;
import ua.dolofinskyi.features.task.exception.TaskMissingDataException;
import ua.dolofinskyi.features.task.exception.TaskNotFoundException;

@RestControllerAdvice
public class TaskExceptionHandler {
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<Response> handleTaskNotFoundException() {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        Response response = new Response(httpStatus.getReasonPhrase(), httpStatus.value());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TaskMissingDataException.class)
    public ResponseEntity<Response> handleTaskMissingIdException() {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        Response response = new Response(httpStatus.getReasonPhrase(), httpStatus.value());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
