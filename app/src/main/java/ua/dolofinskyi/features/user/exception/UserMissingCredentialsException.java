package ua.dolofinskyi.features.user.exception;

public class UserMissingCredentialsException extends RuntimeException {
    public UserMissingCredentialsException() {
        super("User credentials must be provided.");
    }
}
