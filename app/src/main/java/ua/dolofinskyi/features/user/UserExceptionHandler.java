package ua.dolofinskyi.features.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ua.dolofinskyi.common.response.Response;
import ua.dolofinskyi.features.task.exception.TaskAccessDeniedException;
import ua.dolofinskyi.features.task.exception.TaskMissingDataException;
import ua.dolofinskyi.features.task.exception.TaskNotFoundException;
import ua.dolofinskyi.features.user.exception.UserAlreadyExistException;
import ua.dolofinskyi.features.user.exception.UserMissingCredentialsException;
import ua.dolofinskyi.features.user.exception.UserNotAuthenticatedException;
import ua.dolofinskyi.features.user.exception.UserNotFoundException;

@RestControllerAdvice
public class UserExceptionHandler {
    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<Response> handleUserAlreadyExistException() {
        HttpStatus httpStatus = HttpStatus.CONFLICT;
        Response response = new Response(httpStatus.getReasonPhrase(), httpStatus.value());
        return new ResponseEntity<>(response, httpStatus);
    }

    @ExceptionHandler(UserMissingCredentialsException.class)
    public ResponseEntity<Response> handleUserMissingCredentialsException() {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        Response response = new Response(httpStatus.getReasonPhrase(), httpStatus.value());
        return new ResponseEntity<>(response, httpStatus);
    }

    @ExceptionHandler(UserNotAuthenticatedException.class)
    public ResponseEntity<Response> handleUserNotAuthenticatedException() {
        HttpStatus httpStatus = HttpStatus.FORBIDDEN;
        Response response = new Response(httpStatus.getReasonPhrase(), httpStatus.value());
        return new ResponseEntity<>(response, httpStatus);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Response> handleUserNotFoundException() {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        Response response = new Response(httpStatus.getReasonPhrase(), httpStatus.value());
        return new ResponseEntity<>(response, httpStatus);
    }
}
