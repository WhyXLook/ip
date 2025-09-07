package basilseed.command;

import basilseed.exception.BasilSeedIOException;
import basilseed.task.TaskManager;

import java.util.List;

/**
 * Represents a deadline command with arguments and execute function defined
 *
 */
public class DeadlineCommand extends Command{
    public static final List<String> KEYWORDS = List.of("/by");
    private String taskName;
    private boolean isDone;
    private String dueDate;
    private String dateType;

    /**
     * Constructs a Deadline Command with the specified list of arguments.
     *
     * @param arguments the list of string arguments provided to the command
     * @param isDone marks if the task is done
     * @param dateType the date format pattern (e.g. yyyy-MM-dd) of the date argument
     */
    public DeadlineCommand(List<String> arguments, String taskName, boolean isDone, String dateType) {
        super(arguments);
        this.taskName = taskName;
        this.isDone = isDone;
        this.dueDate = arguments.get(0);
        this.dateType = dateType;
    }

    /**
     * Executes the command using the given task manager.
     *
     * @param taskManager the task manager which will dictate how the command
     *                    executes
     */
    public void execute(TaskManager taskManager) throws BasilSeedIOException {
        taskManager.addDeadlineTask(this.taskName, this.isDone, this.dueDate, this.dateType);
    }
}
