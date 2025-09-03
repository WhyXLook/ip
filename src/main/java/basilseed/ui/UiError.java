package basilseed.ui;

import java.util.List;
// TODO finish up on the other errors

public class UiError extends Ui {
    public UiError() {
        super();
    }
    public UiError(boolean silent) {
        super(silent);
    }

    public void displayWrongArgNum(String command, int argNum) {
        String outMsg = String.format("Wrong number of arguments. %s should have %d argument. \n",
            command, argNum);
        super.displayMessage(outMsg);
    }

    public void displayArgNotInteger(String command) {
        String outMsg = String.format(
            "Argument is not a number! \nExample usage: %s 2 \n", command);
        super.displayMessage(outMsg);
    }

    public void displayIndexOutOfBounds() {
        String outMsg = "Index out of bounds! Has to be more than zero and equal or less than task list size!\n";
        super.displayMessage(outMsg);
    }

    public void displayTaskNameNotFound(String firstArgKeyword, String command) {
        String outMsg = String.format(
            "No task name detected. Provide one between the command %s and %s as an argument. \n",
            command, firstArgKeyword);
        super.displayMessage(outMsg);
    }

    public void displayArgKeywordNotFound(String argKeyword, String command) {
        String outMsg = String.format(
            "No '%s' detected. %s is a required argument for %s. \n" , argKeyword, argKeyword, command);
        super.displayMessage(outMsg);
    }

    public void displayArgKeywordOrderWrong(List<String> argKeywordList) {
        String outMsg = "Wrong keyword order.\nKeywords in order: ";
        outMsg = outMsg + String.join(" ", argKeywordList) + "\n";
        super.displayMessage(outMsg);
    }

    public void displayNoArgSupplied(String argKeyword, String argType, String command) {
        String outMsg = String.format("No %s %s detected. Provide one after %s as an argument. \n",
            command, argType, argKeyword);
        super.displayMessage(outMsg);
    }

    public void displayValidDateType() {
        String outMsg = "Wrong date format! Use yyyy-mm-dd e.g. 2019-05-10 \n";
        super.displayMessage(outMsg);
    }

    public void displayInvalidCommand() {
        String outMsg = "Woops, thats not a valid command. Try again! \n";
        super.displayMessage(outMsg);
    }

    public void displayIoException() {
        String outMsg = "\"Something went Wrong with IO. Check your permissions and file path!\n";
        super.displayMessage(outMsg);
    }


}
