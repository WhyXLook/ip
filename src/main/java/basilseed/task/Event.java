package basilseed.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    protected LocalDate from;
    protected LocalDate to;

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
