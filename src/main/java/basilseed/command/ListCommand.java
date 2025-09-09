package basilseed.command;

import basilseed.task.TaskManager;

import java.util.List;

/**
 * Represents a List command with arguments and execute function defined
 *
 */
public class ListCommand extends Command {
    public static final List<String> KEYWORDS = List.of("");

    /**
     * Constructs a List Command with the specified list of arguments.
     *
     * @param arguments the list of string arguments provided to the command
     */
    public ListCommand(List<String> arguments) {
        super(arguments);
    }

    /**
     * Executes the command using the given task manager.
     *
     * @param taskManager the task manager which will dictate how the command
     *                    executes
     * @return result of command as a String
     */
    public String execute(TaskManager taskManager) {
        return taskManager.listTasks(taskManager.getAllTasks());
    }
}
