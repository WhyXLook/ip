package basilseed.command;

import basilseed.exception.BasilSeedIOException;
import basilseed.task.TaskManager;

import java.util.List;

/**
 * Represents a ToDo command with arguments and execute function defined
 *
 */
public class ToDoCommand extends Command{
    public static final List<String> KEYWORDS = List.of("");
    private String taskName;
    private boolean isDone;

    /**
     * Constructs a ToDo Command with the specified list of arguments.
     *
     * @param arguments the list of string arguments provided to the command
     * @param isDone marks if the task is done
     */
    public ToDoCommand(List<String> arguments, String taskName, boolean isDone) {
        super(arguments);
        this.taskName = taskName;
        this.isDone = isDone;
    }

    /**
     * Executes the command using the given task manager.
     *
     * @param taskManager the task manager which will dictate how the command
     *                    executes
     */
    public void execute(TaskManager taskManager) throws BasilSeedIOException {
        taskManager.addToDoTask(this.taskName, this.isDone);
    }
}
