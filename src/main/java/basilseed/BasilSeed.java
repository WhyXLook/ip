package basilseed;

import java.util.ArrayList;
import java.util.List;

import basilseed.exception.BasilSeedException;
import basilseed.exception.BasilSeedIOException;
import basilseed.exception.BasilSeedInvalidInputException;
import basilseed.task.TaskManager;

import basilseed.ui.UiError;
import basilseed.ui.UiStandard;
import basilseed.ui.UiInputOutput;
import basilseed.ui.UiSuccess;

import basilseed.command.Command;

/**
 * Entry point of the BasilSeed application.
 * Handles program startup, initialization of UI components,
 * command parsing, and main execution loop.
 */
public class BasilSeed {

    /**
     * Initializes the task list from storage and loads tasks into the task manager.
     *
     * @param inputParser Parser for interpreting stored task strings.
     * @param taskManager Task manager to populate.
     * @param uiSuccess UI handler for success messages.
     */
    private static void startUp(InputParser inputParser, TaskManager taskManager, UiSuccess uiSuccess, Storage storage)
            throws BasilSeedIOException, BasilSeedInvalidInputException {
        /*
        Self-explanatory function. Reads storage on startup and initializes
        taskManager's tasks array list with those.
         */
        ArrayList<String> taskStrings = storage.read();
        uiSuccess.setSilent(true);
        for (String taskString : taskStrings) {
            Command command = inputParser.parse(taskString, taskManager.getTaskCount());
            command.execute(taskManager);
        }
        uiSuccess.setSilent(false);

    }

    /**
     * Main entry point of the BasilSeed program.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        UiStandard uiStandard = new UiStandard();
        uiStandard.displayGreeting();
        UiError uiError = new UiError();
        UiSuccess uiSuccess = new UiSuccess();
        InputParser inputParser = new InputParser(uiError);
        Storage storage;
        TaskManager taskManager;

        try {
            storage = new Storage();
            taskManager = new TaskManager(uiSuccess, storage);
            startUp(inputParser, taskManager, uiSuccess, storage);
        } catch (BasilSeedException e) {
            uiError.displayError(e.getMessage());
            uiError.displayError("As such, exiting now.\n");
            return;
        }

        UiInputOutput uiIo = new UiInputOutput();
        for (String userInput = uiIo.getInput(); !userInput.equals("bye"); userInput = uiIo.getInput()) {
            try {
                Command command = inputParser.parse(userInput, taskManager.getTaskCount());
                command.execute(taskManager);
            } catch (BasilSeedException e) {
                uiError.displayError(e.getMessage());
            }
        }
        uiStandard.displayFarewell();
        uiIo.close();
    }
}
