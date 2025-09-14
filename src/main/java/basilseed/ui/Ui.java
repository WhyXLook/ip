package basilseed.ui;

/**
 * Abstract base class for UI components.
 * Provides common functions such as setting silence and message formatting.
 * UI will not display messages if silence is set to true.
 */
public abstract class Ui {
    private boolean isSilent;

    /**
     * Constructs a Ui instance with silent mode disabled.
     * The UI will display all messages by default.
     */
    protected Ui() {
        this.isSilent = false;
    }

    /**
     * Constructs a Ui instance with the specified silent mode.
     * If silent mode is enabled, the UI suppresses all output messages.
     *
     * @param silent true to suppress output messages,
     *               false to allow messages to be displayed.
     */
    protected Ui(boolean silent) {
        this.isSilent = silent;
    }

    public void setSilent(boolean silent) {
        this.isSilent = silent;
    }

    protected String returnMessage(String message) {
        if (!this.isSilent) {
            return message;
        }
        return "";
    }

}
