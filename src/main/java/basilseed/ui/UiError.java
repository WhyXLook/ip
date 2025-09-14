package basilseed.ui;


import java.util.List;

/**
 * Handles error messages and returns messages for invalid inputs.
 */
public class UiError extends Ui {

    /**
     * Constructs a UiError instance with silent mode disabled.
     * The UI will return all messages by default.
     */
    public UiError() {
        super();
    }

    /**
     * Constructs a UiError instance with the specified silent mode.
     * If silent mode is enabled, the UI suppresses all output messages.
     *
     * @param silent true to suppress output messages,
     *               false to allow messages to be returned.
     */
    public UiError(boolean silent) {
        super(silent);
    }

    /**
     * Returns a message of error type given the string argument
     *
     * @param message error message to be returned
     * @return a String given by the message param
     */
    public String returnError(String message) {
        return super.returnMessage(message);
    }

    /**
     * Returns a message when the number of arguments supplied for a command is incorrect.
     *
     * @param command the command name
     * @param argNum  the expected number of arguments
     * @return String error message pertaining to wrong number of arguments
     */
    public String returnWrongArgNum(String command, int argNum) {
        String outMsg = String.format("Wrong number of arguments. %s should have %d argument. \n",
                command, argNum);
        return super.returnMessage(outMsg);
    }

    /**
     * Returns a message when an argument is expected to be an integer but is not.
     *
     * @param command the command name
     * @return String error message pertaining to argument is not an integer
     */
    public String returnArgNotInteger(String command) {
        String outMsg = String.format(
                "Argument is not a number! \nExample usage: %s 2 \n", command);
        return super.returnMessage(outMsg);
    }

    /**
     * returns a message when a task index is out of bounds.
     *
     * @return String error message pertaining to wrong number of arguments
     */
    public String returnIndexOutOfBounds() {
        String outMsg = "Index out of bounds! Has to be more than zero and equal or less than task list size!\n";
        return super.returnMessage(outMsg);
    }

    /**
     * returns a message when a task name is missing between a command
     * and a required keyword.
     *
     * @param firstArgKeyword the first argument keyword
     * @param command         the command name
     * @return String error message pertaining to the task name not being found
     */
    public String returnTaskNameNotFound(String firstArgKeyword, String command) {
        String outMsg = String.format(
                "No task name detected. Provide one between the command %s and %s as an argument. \n",
                command, firstArgKeyword);
        return super.returnMessage(outMsg);
    }

    /**
     * returns a message when a required keyword argument is missing.
     *
     * @param argKeyword the missing keyword
     * @param command    the command name
     * @return String error message pertaining to a keyword being missing
     */
    public String returnArgKeywordNotFound(String argKeyword, String command) {
        String outMsg = String.format(
                "No '%s' detected. %s is a required argument for %s. \n" , argKeyword, argKeyword, command);
        return super.returnMessage(outMsg);
    }

    /**
     * returns a message when argument keywords are provided in the wrong order.
     *
     * @param argKeywordList the expected order of keywords
     * @return String error message pertaining to the order of keywords being wrong
     */
    public String returnArgKeywordOrderWrong(List<String> argKeywordList) {
        String outMsg = "Wrong keyword order.\nKeywords in order: ";
        outMsg = outMsg + String.join(" ", argKeywordList) + "\n";
        return super.returnMessage(outMsg);
    }

    /**
     * returns a message when an expected argument after a keyword is missing.
     *
     * @param argKeyword the keyword preceding the missing argument
     * @param argType    the type of argument expected (e.g. date)
     * @param command    the command name
     * @return String error message pertaining to no argument being supplied
     */
    public String returnNoArgSupplied(String argKeyword, String argType, String command) {
        String outMsg = String.format("No %s %s detected. Provide one after %s as an argument. \n",
                command, argType, argKeyword);
        return super.returnMessage(outMsg);
    }

    /**
     * returns a message when an invalid date format is used.
     *
     * @return String error message pertaining to invalid date types
     */
    public String returnInvalidDateType() {
        String outMsg = "Wrong date format! Use yyyy-mm-dd e.g. 2019-05-10 \n";
        return super.returnMessage(outMsg);
    }

    /**
     * returns a message when an invalid command is entered.
     *
     * @return String error message pertaining to the command not being valid
     */
    public String returnInvalidCommand() {
        String outMsg = "Woops, thats not a valid command. Try again! \n";
        return super.returnMessage(outMsg);
    }

}
