package basilseed.task;
/**
 * Represents a simple to-do task with only a name and completion status.
 */
public class ToDo extends Task {

    /**
     * Constructs a ToDo with the specified name
     * @param name the name of the ToDo task
     */
    public ToDo(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
