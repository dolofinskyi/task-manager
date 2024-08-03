package ua.dolofinskyi.features.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ua.dolofinskyi.common.response.Response;
import ua.dolofinskyi.features.user.exception.UserNotFoundException;

@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Response> handleUserNotFoundException() {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        Response response = new Response(httpStatus.getReasonPhrase(), httpStatus.value());
        return new ResponseEntity<>(response, httpStatus);
    }
}
