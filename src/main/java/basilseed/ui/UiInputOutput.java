package basilseed.ui;

import java.util.Scanner;

public class UiInputOutput extends Ui implements AutoCloseable {
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



    // discovery of autocloseable came from https://www.baeldung.com/java-destructor
    @Override
    public void close() throws Exception {
        this.scanner.close();
    }

}
