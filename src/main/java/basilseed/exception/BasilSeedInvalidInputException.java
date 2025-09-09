package basilseed.exception;

/**
 * Custom exception class for invalid inputs
 */
public class BasilSeedInvalidInputException extends BasilSeedException {

    /**
     * Constructs a InvalidInputException with the specified message
     *
     * @param message message to be read
     */
    public BasilSeedInvalidInputException(String message) {
        super(message);
    }
}
