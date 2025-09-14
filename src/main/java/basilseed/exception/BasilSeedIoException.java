package basilseed.exception;

/**
 * Custom exception class for IO activities
 */
public class BasilSeedIoException extends BasilSeedException {

    /**
     * Constructs a BasilSeedIoException using the given parameters
     *
     * @param defaultPath the default path for the expected file
     */
    public BasilSeedIoException(String defaultPath) {
        super(String.format("Something went Wrong with IO. "
            + "Check your perms and file path!\n"
            + "Default is at %s\n", defaultPath)
        );
    }
}
