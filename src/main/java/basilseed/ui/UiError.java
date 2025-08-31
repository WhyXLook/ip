package basilseed.ui;

// TODO finish up on the other errors

public class UiError extends Ui {
    public UiError() {
        super();
    }
    public UiError(boolean silent) {
        super(silent);
    }

    public void displayWrongArgNum(String command, int argNum){
        String outMsg = String.format("Wrong number of arguments. %s should have %d argument. \n",
                command, argNum);
        super.displayMessage(outMsg);
    }

    public void displayArgNotInteger(String command){
        String outMsg = String.format("____________________________________________________________\n" +
            "Argument is not a number! \nExample usage: %s 2 \n" +
            "____________________________________________________________\n", command);
        super.displayMessage(outMsg);
    }
}
