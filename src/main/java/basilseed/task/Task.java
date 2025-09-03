package basilseed.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Task {
    public static final String STORAGE_DATE_FORMAT = "MMM dd yyyy";
    public static final String INPUT_DATE_FORMAT = "yyyy-MM-dd";

    protected String name;
    protected boolean isDone;

    public Task(String name) {
        this.name = name;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : ""); // mark done task with X
    }

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
