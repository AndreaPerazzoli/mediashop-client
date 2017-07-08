package exceptions;

/**
 * Created by andreaperazzoli on 08/07/17.
 */
public class UserNotRegisteredException extends RuntimeException {
    public UserNotRegisteredException(String message) {
        super(message);
    }
}
