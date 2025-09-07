package basilseed.command;

import basilseed.task.TaskManager;

import java.util.List;

/**
 * Represents a Delete command with arguments and execute function defined
 *
 */
public class DeleteCommand extends Command{
    public static final List<String> KEYWORDS = List.of("");
    private int index;

    /**
     * Constructs a Delete Command with the specified list of arguments.
     *
     * @param arguments the list of string arguments provided to the command
     */
    public DeleteCommand(List<String> arguments) {
        super(arguments);
        this.index = Integer.parseInt(arguments.get(0));
    }

    /**
     * Executes the command using the given task manager.
     *
     * @param taskManager the task manager which will dictate how the command
     *                    executes
     */
    public void execute(TaskManager taskManager) {
        taskManager.deleteTask(index);
    }
}
