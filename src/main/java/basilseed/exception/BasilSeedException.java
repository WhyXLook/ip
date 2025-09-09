package basilseed.exception;

/**
 * Base custom exception class
 */
public class BasilSeedException extends Exception {

    /**
     * Constructs a BasilSeedException with the specified message
     *
     * @param message message to be read
     */
    public BasilSeedException(String message) {
        super(message);
    }
}
