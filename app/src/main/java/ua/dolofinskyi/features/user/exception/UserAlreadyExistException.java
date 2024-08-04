package ua.dolofinskyi.features.user.exception;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException() {
        super("User already exist.");
    }
}
