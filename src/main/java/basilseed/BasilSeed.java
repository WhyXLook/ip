package basilseed;

import java.util.ArrayList;

import basilseed.ui.UiError;
import basilseed.ui.UiStandard;
import basilseed.ui.UiInputOutput;
import basilseed.ui.UiSuccess;

public class BasilSeed {

    private static void startUp(InputParser inputParser, UiSuccess uiSuccess) {
        /*
        Self-explanatory function. Reads storage on startup and initializes
        taskManager's tasks array list with those.
         */
        Storage storage = new Storage();
        ArrayList<String> taskStrings = storage.read();
        uiSuccess.setSilent(true);
        for (String taskString : taskStrings){
            inputParser.parse(taskString);
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
        InputParser inputParser = new InputParser(taskManager, uiError);
        startUp(inputParser, uiSuccess);
        String userInput = uiIo.getInput();
        for(;!userInput.equals("bye"); userInput = uiIo.getInput()){
            inputParser.parse(userInput);
        }
        uiStandard.displayFarewell();
    }
}
