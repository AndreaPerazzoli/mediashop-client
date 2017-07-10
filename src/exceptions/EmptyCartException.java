package exceptions;

/**
 * Created by andreaperazzoli on 09/07/17.
 */
public class EmptyCartException extends RuntimeException{
    public EmptyCartException() {
    }

    public EmptyCartException(String message) {
        super(message);

    }
}
