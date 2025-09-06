package basilseed.ui;

import java.util.List;

/**
 * Handles and displays success messages when user commands execute correctly.
 */
public class UiSuccess extends Ui {

    /**
     * Constructs a UiSuccess instance with silent mode disabled.
     * The UI will display all messages by default.
     */
    public UiSuccess() {
        super();
    }

    /**
     * Constructs a Ui instance with the specified silent mode.
     * If silent mode is enabled, the UI suppresses all output messages.
     *
     * @param silent true to suppress output messages,
     *               false to allow messages to be displayed.
     */
    public UiSuccess(boolean silent) {
        super(silent);
    }

    /**
     * Displays the current list of tasks with their indices.
     *
     * @param taskList the list of task string representations
     */
    public void displayTaskList(List<String> taskList) {
        String outMsg =  "";
        for (int i = 0; i < taskList.size(); i++) {
            outMsg = outMsg + (i + 1) + "." + taskList.get(i) + "\n";
        }
        super.displayMessage(outMsg);
    }

    /**
     * Displays a message confirming whether a task was marked done or not done.
     *
     * @param taskString the task description
     * @param isDone     true if the task is now marked done, false if unmarked
     */
    public void displayTaskMarked(String taskString, boolean isDone){
        String outMsg = "";
        if (isDone) {
            outMsg =  "Nice! I've marked this task as done: " + taskString + "\n";
        } else {
            outMsg = "OK, I've marked this task as not done yet: " + taskString + "\n";
        }
        displayMessage(outMsg);
    }

    /**
     * Displays a message confirming that a task was added to the list.
     *
     * @param taskString the task description
     * @param totalTasks the total number of tasks after addition
     */
    public void displayTaskAdded(String taskString, int totalTasks) {
        String outMsg = "Got it. I've added this task:\n" +
            taskString + "\n" +
            "Now you have " + totalTasks + " tasks in the list.\n";
        super.displayMessage(outMsg);
    }

    /**
     * Displays a message confirming that a task was removed from the list.
     *
     * @param taskString the task description
     * @param totalTasks the total number of tasks after deletion
     */
    public void displayTaskDeleted(String taskString, int totalTasks) {
        String outMsg = "Noted. I've removed this task:\n" +
            taskString + "\n" +
            "Now you have " + totalTasks + " tasks in the list.\n";
        super.displayMessage(outMsg);
    }
}
