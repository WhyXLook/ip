package basilseed.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Abstract base class for all tasks.
 */
public abstract class Task {
    public static final String STORAGE_DATE_FORMAT = "MMM dd yyyy";
    public static final String INPUT_DATE_FORMAT = "yyyy-MM-dd";

    protected String name;
    protected boolean isDone;

    public Task(String name) {
        this.name = name;
        this.isDone = false;
    }

    /**
     * Returns the status icon of the task.
     *
     * @return "X" if done, empty string otherwise.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : ""); // mark done task with X
    }

    /**
     * Sets the completion status of the task.
     *
     * @param inputBoolean True if done, false otherwise.
     */
    public void setDone(boolean inputBoolean) {
        this.isDone = inputBoolean;
    }

    protected static String formatDate (LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(STORAGE_DATE_FORMAT);
        String dateString = date.format(formatter);
        return dateString;
    }

    @Override
    public String toString() {
        String outMsg =  "[" + this.getStatusIcon() + "] " + this.name;
        return outMsg;
    }
}
