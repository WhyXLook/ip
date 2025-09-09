package basilseed.exception;

/**
 * Custom exception class for IO activities
 */
public class BasilSeedIoException extends BasilSeedException {

    /**
     * Constructs a InvalidInputException with the specified message
     *
     * @param message message to be read
     */
    public BasilSeedIoException(String message) {
        super(message);
    }
}
