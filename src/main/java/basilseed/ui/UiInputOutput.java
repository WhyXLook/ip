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

    public String getInput(){
        return this.scanner.nextLine();
    }

    // discovery of finalize came from googling "java method that automatically runs when object is destroyed
    //     or finish running. Many articles on "finalize()"
    // https://www.geeksforgeeks.org/java/finalize-method-in-java-and-how-to-override-it/
    @Override
    protected void finalize() throws Throwable {
        this.scanner.close();
    }

}
