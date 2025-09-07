package basilseed.ui;

import java.util.List;

/**
 * Handles error messages and displays messages for invalid inputs.
 */
public class UiError extends Ui {

    /**
     * Constructs a UiError instance with silent mode disabled.
     * The UI will display all messages by default.
     */
    public UiError() {
        super();
    }

    /**
     * Constructs a UiError instance with the specified silent mode.
     * If silent mode is enabled, the UI suppresses all output messages.
     *
     * @param silent true to suppress output messages,
     *               false to allow messages to be displayed.
     */
    public UiError(boolean silent) {
        super(silent);
    }

    /**
     * Displays a message of error type given the string argument
     *
     * @param message error message to be displayed
     */
    public void displayError(String message) {
        super.displayMessage(message);
    }


}
