package basilseed;

import java.util.ArrayList;
import java.util.List;

import basilseed.task.TaskManager;
import basilseed.ui.UiError;
import basilseed.ui.UiStandard;
import basilseed.ui.UiInputOutput;
import basilseed.ui.UiSuccess;

public class BasilSeed {

    private static void parseAndProcess(String inputString, InputParser inputParser, TaskManager taskManager,
                                        UiSuccess uiSuccess) {
        String validString = inputParser.parse(inputString, taskManager.getTaskCount());
        if (!validString.isEmpty()) {
            String command = inputParser.getCommand(validString);
            String taskName = inputParser.getTaskName(validString);
            List<String> argsList = inputParser.getAllArgs(validString);
            String dateType = inputParser.getDateType(argsList);
            boolean isMarked = inputParser.isMarked(validString);
            taskManager.processCommand(command, taskName, argsList, isMarked, dateType);
        }
    }

    private static void startUp(InputParser inputParser, TaskManager taskManager, UiSuccess uiSuccess) {
        /*
        Self-explanatory function. Reads storage on startup and initializes
        taskManager's tasks array list with those.
         */
        Storage storage = new Storage();
        ArrayList<String> taskStrings = storage.read();
        uiSuccess.setSilent(true);
        for (String taskString : taskStrings) {
            parseAndProcess(taskString, inputParser, taskManager, uiSuccess);
        }
        uiSuccess.setSilent(false);

    }

    public static void main(String[] args) {
        UiStandard uiStandard = new UiStandard();
        uiStandard.displayGreeting();
        UiError uiError = new UiError();
        UiSuccess uiSuccess = new UiSuccess();
        UiInputOutput uiIo = new UiInputOutput();
        TaskManager taskManager = new TaskManager(uiSuccess);
        InputParser inputParser = new InputParser(uiError);
        startUp(inputParser, taskManager, uiSuccess);
        for (String userInput = uiIo.getInput(); !userInput.equals("bye"); userInput = uiIo.getInput()) {
            parseAndProcess(userInput, inputParser, taskManager, uiSuccess);
        }
        uiStandard.displayFarewell();
        uiIo.close();
    }
}
