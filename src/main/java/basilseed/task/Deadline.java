package basilseed.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task that has a deadline.
 */
public class Deadline extends Task {

    protected LocalDate by;

    /**
     * Constructs a deadline task with a name, due date, and date format type.
     * The provided date string is parsed into a LocalDate instance
     * using the specified date format pattern.
     *
     * @param name Name of the deadline.
     * @param by Due date string.
     * @param dateType the date format pattern (e.g. yyyy-MM-dd) used to parse the dates
     */
    public Deadline(String name, String by, String dateType) {
        super(name);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateType);
        this.by = LocalDate.parse(by, formatter);
    }

    @Override
    // DateTimeFormatter came from https://stackoverflow.com/questions/39689866/how-to-format-localdate-
    // object-to-mm-dd-yyyy-and-have-format-persist
    public String toString() {
        return "[D]" + super.toString() + " /by " + formatDate(this.by);
    }
}
