package basilseed.ui;

/**
 * Handles and displays standard messages or UI elements and texts
 * that will show up even with minimal or no user interaction
 */
public class UiStandard extends Ui {

    /**
     * Constructs a UiStandard instance with silent mode disabled.
     * The UI will display all messages by default.
     */
    public UiStandard() {
        super();
    }

    /**
     * Constructs a UiStandard instance with the specified silent mode.
     * If silent mode is enabled, the UI suppresses all output messages.
     *
     * @param silent true to suppress output messages,
     *               false to allow messages to be displayed.
     */
    public UiStandard(boolean silent) {
        super(silent);
    }

    /**
     * Displays the greeting message.
     *
     * @return a greeting string
     */
    public String displayGreeting() {
        String greeting = " Hello! I'm BasilSeed \n"
            + " What can I do for you?\n";
        return super.displayMessage(greeting);
    }

    /**
     * Displays the farewell message.
     *
     * @return a farewell string
     */
    public String displayFarewell() {
        String farewell = "Bye. Hope to see you again soon! \n";
        return super.displayMessage(farewell);
    }
}
