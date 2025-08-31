package basilseed.ui;

import java.util.List;

public abstract class Ui {
    private boolean isSilent;

    protected Ui() {
        this.isSilent = false;
    }

    protected Ui(boolean silent) {
        this.isSilent = silent;
    }

    protected void setSilent(boolean silent) {
        this.isSilent = silent;
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
