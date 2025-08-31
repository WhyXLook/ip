package basilseed.ui;

import java.util.List;

public class UiSuccess extends Ui {
    public UiSuccess() {
        super();
    }

    public UiSuccess(boolean silent) {
        super(silent);
    }

    public void displayTaskList(List<String> taskList) {
        String outMsg =  "";
        for (int i = 0; i < taskList.size(); i++) {
            outMsg = outMsg + (i + 1) + "." + taskList.get(i) + "\n";
        }
        super.displayMessage(outMsg);
    }

    public void displayTaskMarked(String taskString, boolean isDone){
        if(isDone){
            displayMessage("Nice! I've marked this task as done:\n");
        } else {
            displayMessage("OK, I've marked this task as not done yet:\n");
        }
    }

    public void displayTaskAdded(String taskString, int totalTasks) {
        String outMsg = "Got it. I've added this task:\n" +
                taskString + "\n" +
                "Now you have " + totalTasks + " tasks in the list.\n";
        super.displayMessage(outMsg);
    }

    public void displayTaskDeleted(String taskString, int totalTasks) {
        String outMsg = "Noted. I've removed this task:\n" +
                taskString + "\n" +
                "Now you have " + totalTasks + " tasks in the list.\n";
        super.displayMessage(outMsg);
    }
}
