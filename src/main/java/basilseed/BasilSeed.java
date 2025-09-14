package basilseed;

import java.util.ArrayList;

import basilseed.exception.BasilSeedException;
import basilseed.exception.BasilSeedIoException;
import basilseed.exception.BasilSeedInvalidInputException;
import basilseed.task.TaskManager;

import basilseed.ui.UiStandard;
import basilseed.ui.UiSuccess;

import basilseed.command.Command;

/**
 * Entry point of the BasilSeed application.
 * Handles program startup, initialization of UI components,
 * command parsing, and main execution loop.
 */
public class BasilSeed {
    private final InputParser inputParser;
    private final TaskManager taskManager;
    private final UiStandard uiStandard;
    private final UiSuccess uiSuccess;

    /**
     * Creates a BasilSeedException
     *
     * @throws BasilSeedException
     */
    public BasilSeed() throws BasilSeedException {
        this.uiSuccess = new UiSuccess();
        this.uiStandard = new UiStandard();
        this.inputParser = new InputParser();
        Storage storage = new Storage();
        this.taskManager = new TaskManager(uiSuccess, storage);

        startUp(inputParser, taskManager, uiSuccess, storage);

    }


    /**
     * Initializes the task list from storage and loads tasks into the task manager.
     *
     * @param inputParser Parser for interpreting stored task strings.
     * @param taskManager Task manager to populate.
     * @param uiSuccess UI handler for success messages.
     */
    private static void startUp(InputParser inputParser, TaskManager taskManager, UiSuccess uiSuccess, Storage storage)
            throws BasilSeedIoException, BasilSeedInvalidInputException {
        /*
        Self-explanatory function. Reads storage on startup and initializes
        taskManager's tasks array list with those.
         */
        ArrayList<String> taskStrings = storage.read();
        uiSuccess.setSilent(true);
        for (String taskString : taskStrings) {
            Command command = inputParser.parseFromStorage(taskString, taskManager.getTaskCount());
            command.execute(taskManager);
        }
        uiSuccess.setSilent(false);

    }

    /**
     * Returns the greeting message.
     *
     * @return the greeting string
     */
    public String getGreeting() {
        return this.uiStandard.displayGreeting();
    }

    /**
     * Returns the farewell message.
     *
     * @return a farewell string
     */
    public String getFarewell() {
        return this.uiStandard.displayFarewell();
    }

    public String getResponse(String userInput) {
        try {
            Command command = this.inputParser.parse(userInput, taskManager.getTaskCount());
            return command.execute(taskManager);
        } catch (BasilSeedException e) {
            return e.getMessage();
        }
    }
}
