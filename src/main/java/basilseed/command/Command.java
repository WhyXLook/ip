package basilseed.command;

import basilseed.exception.BasilSeedIOException;
import basilseed.task.TaskManager;

import java.util.List;

/**
 * Abstract base class for all commands.
 * Each command holds its
 * associated arguments and provides methods to define the
 * command keywords and execution logic.
 */
public abstract class Command {
    protected List<String> arguments;

    /**
     * Constructs a Command with the specified list of arguments.
     *
     * @param arguments the list of string arguments provided to the command
     */
    public Command(List<String> arguments) {
        this.arguments = arguments;
    }

    /**
     * Executes the command using the given task manager.
     *
     * @param taskManager the task manager which will dictate how the command
     *                    executes
     */
    public abstract void execute(TaskManager taskManager) throws BasilSeedIOException;

}
