package basilseed.ui;

import java.util.List;

/**
 * Handles error messages and displays messages for invalid inputs.
 */
public class UiError extends Ui {

    /**
     * Constructs a UiError instance with silent mode disabled.
     * The UI will display all messages by default.
     */
    public UiError() {
        super();
    }

    /**
     * Constructs a UiError instance with the specified silent mode.
     * If silent mode is enabled, the UI suppresses all output messages.
     *
     * @param silent true to suppress output messages,
     *               false to allow messages to be displayed.
     */
    public UiError(boolean silent) {
        super(silent);
    }

    /**
     * Displays a message when the number of arguments supplied for a command is incorrect.
     *
     * @param command the command name
     * @param argNum  the expected number of arguments
     */
    public void displayWrongArgNum(String command, int argNum) {
        String outMsg = String.format("Wrong number of arguments. %s should have %d argument. \n",
            command, argNum);
        super.displayMessage(outMsg);
    }

    /**
     * Displays a message when an argument is expected to be an integer but is not.
     *
     * @param command the command name
     */
    public void displayArgNotInteger(String command) {
        String outMsg = String.format(
            "Argument is not a number! \nExample usage: %s 2 \n", command);
        super.displayMessage(outMsg);
    }

    /**
     * Displays a message when a task index is out of bounds.
     */
    public void displayIndexOutOfBounds() {
        String outMsg = "Index out of bounds! Has to be more than zero and equal or less than task list size!\n";
        super.displayMessage(outMsg);
    }

    /**
     * Displays a message when a task name is missing between a command
     * and a required keyword.
     *
     * @param firstArgKeyword the first argument keyword
     * @param command         the command name
     */
    public void displayTaskNameNotFound(String firstArgKeyword, String command) {
        String outMsg = String.format(
            "No task name detected. Provide one between the command %s and %s as an argument. \n",
            command, firstArgKeyword);
        super.displayMessage(outMsg);
    }

    /**
     * Displays a message when a required keyword argument is missing.
     *
     * @param argKeyword the missing keyword
     * @param command    the command name
     */
    public void displayArgKeywordNotFound(String argKeyword, String command) {
        String outMsg = String.format(
            "No '%s' detected. %s is a required argument for %s. \n" , argKeyword, argKeyword, command);
        super.displayMessage(outMsg);
    }

    /**
     * Displays a message when argument keywords are provided in the wrong order.
     *
     * @param argKeywordList the expected order of keywords
     */
    public void displayArgKeywordOrderWrong(List<String> argKeywordList) {
        String outMsg = "Wrong keyword order.\nKeywords in order: ";
        outMsg = outMsg + String.join(" ", argKeywordList) + "\n";
        super.displayMessage(outMsg);
    }

    /**
     * Displays a message when an expected argument after a keyword is missing.
     *
     * @param argKeyword the keyword preceding the missing argument
     * @param argType    the type of argument expected (e.g. date)
     * @param command    the command name
     */
    public void displayNoArgSupplied(String argKeyword, String argType, String command) {
        String outMsg = String.format("No %s %s detected. Provide one after %s as an argument. \n",
            command, argType, argKeyword);
        super.displayMessage(outMsg);
    }

    /**
     * Displays a message when an invalid date format is used.
     */
    public void displayValidDateType() {
        String outMsg = "Wrong date format! Use yyyy-mm-dd e.g. 2019-05-10 \n";
        super.displayMessage(outMsg);
    }

    /**
     * Displays a message when an invalid command is entered.
     */
    public void displayInvalidCommand(){
        String outMsg = "Woops, thats not a valid command. Try again! \n";
        super.displayMessage(outMsg);
    }

    /**
     * Displays a message when an I/O exception occurs while accessing storage.
     */
    public void displayIoException(){
        String outMsg = "\"Something went Wrong with IO. Check your permissions and file path!\n";
        super.displayMessage(outMsg);
    }


}
