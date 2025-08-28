package basilseed.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {

    protected LocalDate by;

    public Deadline(String name, String by, String dateType) {
        super(name);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateType);
        this.by = LocalDate.parse(by, formatter);
    }

    @Override
    // DateTimeFormatter came from https://stackoverflow.com/questions/39689866/how-to-format-localdate-object-to-mm-dd-yyyy-and-have-format-persist
    public String toString() {
        return "[D]" + super.toString() + " /by " + formatDate(this.by);
    }
}
