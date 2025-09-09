package basilseed.command;

import basilseed.exception.BasilSeedIoException;
import basilseed.task.TaskManager;

import java.util.List;

/**
 * Represents a Mark command with arguments and execute function defined
 *
 */
public class MarkCommand extends Command {
    public static final List<String> KEYWORDS = List.of("");
    private int index;

    /**
     * Constructs a Mark Command with the specified list of arguments.
     *
     * @param arguments the list of string arguments provided to the command
     */
    public MarkCommand(List<String> arguments) {
        super(arguments);
        this.index = Integer.parseInt(arguments.get(0));
    }

    /**
     * Executes the command using the given task manager.
     *
     * @param taskManager the task manager which will dictate how the command
     *                    executes
     * @return result of command as a String
     */
    public String execute(TaskManager taskManager) throws BasilSeedIoException {
        return taskManager.setTaskDone(index, true);
    }
}
