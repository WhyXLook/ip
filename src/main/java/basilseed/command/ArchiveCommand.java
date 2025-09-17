package basilseed.command;

import basilseed.exception.BasilSeedIoException;
import basilseed.task.TaskManager;

import java.util.List;

/**
 * Represents an Archive command with arguments and execute function defined
 *
 */
public class ArchiveCommand extends Command {
    public static final List<String> KEYWORDS = List.of("");

    /**
     * Constructs a List Command.
     */
    public ArchiveCommand() {
        super(List.of());
    }

    /**
     * Executes the command using the given task manager.
     *
     * @param taskManager the task manager which will dictate how the command
     *                    executes
     * @return result of command as a String
     */
    public String execute(TaskManager taskManager) throws BasilSeedIoException {
        return taskManager.archiveTasks();
    }
}
