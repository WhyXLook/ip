package basilseed.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a start and end date, such as an event or activity.
 * Stores the event name, start date, and end date.
 */
public class Event extends Task {
    protected LocalDate from;
    protected LocalDate to;

    /**
     * Constructs an Event with the specified name, start date, and end date.
     * The provided date strings are parsed into LocalDate instances
     * using the specified date format pattern.
     *
     * @param name the name of the event
     * @param from the start date of the event as a string
     * @param to the end date of the event as a string
     * @param dateType the date format pattern (e.g. yyyy-MM-dd) used to parse the dates
     */
    public Event(String name, String from, String to, String dateType) {
        super(name);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateType);
        this.from = LocalDate.parse(from, formatter);
        this.to = LocalDate.parse(to, formatter);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " /from " + formatDate(from) + " /to " + formatDate(to);
    }
}
