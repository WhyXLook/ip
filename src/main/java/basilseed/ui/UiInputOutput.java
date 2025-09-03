package basilseed.ui;

import java.util.Scanner;

public class UiInputOutput extends Ui {
    private Scanner scanner;

    public UiInputOutput() {
        super();
        this.scanner = new Scanner(System.in);
    }

    public UiInputOutput(boolean silent) {
        super(silent);
        this.scanner = new Scanner(System.in);
    }

    /**
     * Returns the next line of user input.
     *
     * @return User input string.
     */
    public String getInput(){
        return this.scanner.nextLine();
    }

    /**
     * Closes the input scanner.
     */
    public void close() {
        this.scanner.close();
    }

}
