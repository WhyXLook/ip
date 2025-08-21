import java.util.Arrays;
import java.util.List;

public class CommandParser {
    private TaskManager taskManager = new TaskManager();

    public void parse(String inputString){
        /*
        Function to parse user input, checking if its a command keyword. i.e. List
        Modify the passed in arrayList as needed by the command.
        Separated by the space, the first word is the command while the second word is the argument
        */
        // TODO make it so that double space can also be divided with regex
        // conversion from string arrays to List string https://stackoverflow.com/questions/2607289/converting-array-to-list-in-java
        List<String> wordsList = Arrays.asList(inputString.split(" "));
        String command = wordsList.get(0);
        String outMsg = "";
        int index = -1;
        switch (command){
            case "list":
                this.taskManager.listTasks();
                break;
            case "mark":
                // TODO check if second arg exist and is more than 0 --> this is on commandParser
                index = Integer.parseInt(wordsList.get(1)) - 1;
                this.taskManager.setTaskDone(index, true);

                break;
            case "unmark":
                // TODO check if second arg exist and is more than 0 --> this is on commandParser
                index = Integer.parseInt(wordsList.get(1)) - 1;
                this.taskManager.setTaskDone(index, false);
                break;
            // witches if you don't break it will run the last peice of code. So can abuse that
            case "todo":
            case "deadline":
            case "event":
                // use of sublist came from 2030, but also available on java docs anyway
                this.taskManager.addTask(command, wordsList.subList(1, wordsList.size()));
                break;
            default:
                outMsg = "____________________________________________________________\n" +
                        "Woops, thats not a valid command. Try again! \n" +
                        "____________________________________________________________\n";
                System.out.println(outMsg);

        }
    }
}
