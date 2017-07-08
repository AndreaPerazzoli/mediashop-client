package exceptions;

/**
 * Created by andreaperazzoli on 08/07/17.
 */
public class SqlConnectionException extends RuntimeException{
    public SqlConnectionException(String message) {
        super(message);
    }
}
