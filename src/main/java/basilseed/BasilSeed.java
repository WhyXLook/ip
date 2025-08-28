package basilseed;

import java.util.ArrayList;
import java.util.Scanner;

public class BasilSeed {

    private static void startUp(InputParser inputParser) {
        /*
        Self-explanatory function. Reads storage on startup and initializes
        taskManager's tasks array list with those.
         */
        Storage storage = new Storage();
        ArrayList<String> taskStrings = storage.read();
        for (String taskString : taskStrings){
            inputParser.parse(taskString);
        }

    }

    public static void main(String[] args) {
        String outMsg = "____________________________________________________________\n" +
                " Hello! I'm BasilSeed \n" +
                " What can I do for you?\n" +
                "____________________________________________________________\n";
        System.out.println(outMsg);
        System.out.println("Enter a string: ");
        // Use of scanner to get user input came from https://stackoverflow.com/questions/5287538/how-to-get-the-user-input-in-java
        TaskManager taskManager = new TaskManager();
        InputParser inputParser = new InputParser(taskManager);
        startUp(inputParser);
        Scanner reader = new Scanner(System.in);
        String userInput = reader.nextLine();
        for(;!userInput.equals("bye"); userInput = reader.nextLine()){
            inputParser.parse(userInput);
        }
        System.out.println("""
                ____________________________________________________________
                Bye. Hope to see you again soon!
                ____________________________________________________________\
                """);
        reader.close();
    }
}
