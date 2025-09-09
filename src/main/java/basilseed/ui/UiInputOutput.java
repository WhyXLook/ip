package basilseed.ui;

import java.util.Scanner;

/**
 * Handles the IO operations, specifically the user input strings.
 */
public class UiInputOutput extends Ui {
    private Scanner scanner;
    /**
     * Constructs a UiInputOutput instance with silent mode disabled.
     * The UI will display all messages by default.
     */
    public UiInputOutput() {
        super();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Constructs a UiInputOutput instance with the specified silent mode.
     * If silent mode is enabled, the UI suppresses all output messages.
     *
     * @param silent true to suppress output messages,
     *               false to allow messages to be displayed.
     */
    public UiInputOutput(boolean silent) {
        super(silent);
        this.scanner = new Scanner(System.in);
    }

    /**
     * Returns the next line of user input.
     *
     * @return User input string.
     */
    public String getInput() {
        return this.scanner.nextLine();
    }

    /**
     * Closes the input scanner.
     */
    public void close() {
        this.scanner.close();
    }

}
