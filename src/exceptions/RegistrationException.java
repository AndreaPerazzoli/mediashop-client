package exceptions;

/**
 * Created by andreaperazzoli on 08/07/17.
 */
public class RegistrationException extends RuntimeException {
    public RegistrationException(String message){
        super(message);
    }
}
