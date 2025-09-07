package basilseed.command;

import basilseed.task.TaskManager;

import java.util.List;

/**
 * Represents a Event command with arguments and execute function defined
 *
 */
public class EventCommand extends Command{
    public static final List<String> KEYWORDS = List.of("/from", "/to");
    private String taskName;
    private boolean isDone;
    private String startDate;
    private String endDate;
    private String dateType;

    /**
     * Constructs a Event Command with the specified list of arguments.
     *
     * @param arguments the list of string arguments provided to the command
     * @param isDone marks if the task is done
     * @param dateType the date format pattern (e.g. yyyy-MM-dd) of the date argument
     */
    public EventCommand(List<String> arguments, String taskName, boolean isDone, String dateType) {
        super(arguments);
        this.taskName = taskName;
        this.isDone = isDone;
        this.startDate = arguments.get(0);
        this.endDate = arguments.get(1);
        this.dateType = dateType;
    }

    /**
     * Executes the command using the given task manager.
     *
     * @param taskManager the task manager which will dictate how the command
     *                    executes
     */
    public void execute(TaskManager taskManager) {
        taskManager.addEventTask(this.taskName, this.isDone, this.startDate, this.endDate, this.dateType);
    }
}
