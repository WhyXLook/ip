package basilseed.ui;

public class UiStandard extends Ui {
    public UiStandard() {
        super();
    }

    public UiStandard(boolean silent) {
        super(silent);
    }

    public void displayGreeting() {
        String greeting = " Hello! I'm BasilSeed \n" +
                " What can I do for you?\n";
        super.displayMessage(greeting);
    }

    public void displayFarewell() {
        String farewell = "Bye. Hope to see you again soon!";
        super.displayMessage(farewell);
    }
}
