package basilseed.ui;

/**
 * Abstract base class for UI components.
 * Provides common functions such as setting silence and message formatting.
 */
public abstract class Ui {
    private boolean isSilent;

    protected Ui() {
        this.isSilent = false;
    }

    protected Ui(boolean silent) {
        this.isSilent = silent;
    }

    public void setSilent(boolean silent) {
        this.isSilent = silent;
    }

    public boolean getIsSilentStatus() {
        return this.isSilent;
    }

    protected void displayLine() {
        System.out.println("____________________________________________________________\n");
    }

    protected void displayMessage(String message) {
        if (!this.isSilent) {
            displayLine();
            System.out.print(message);
            displayLine();
        }
    }

}
