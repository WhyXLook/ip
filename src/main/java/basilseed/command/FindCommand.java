package basilseed.command;

import basilseed.task.TaskManager;

import java.util.List;

/**
 * Represents a Find command with arguments and execute function defined
 *
 */
public class FindCommand extends Command{
    public static final List<String> KEYWORDS = List.of("");
    private String keyword;

    /**
     * Constructs a Find Command with the specified list of arguments.
     *
     * @param arguments the list of string arguments provided to the command
     */
    public FindCommand(List<String> arguments) {
        super(arguments);
        this.keyword = arguments.get(0);
    }

    /**
     * Executes the command using the given task manager.
     *
     * @param taskManager the task manager which will dictate how the command
     *                    executes
     */
    public void execute(TaskManager taskManager) {
        taskManager.findTask(this.keyword);
    }
}
