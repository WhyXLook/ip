import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandParser {
    private TaskManager taskManager = new TaskManager();

    private void setMark(List<String> wordsList, boolean mark){
        String outMsg = "";
        int index = -1;
        if (wordsList.size() < 2) {
            outMsg = "____________________________________________________________\n" +
                    "Wrong number of arguments. mark should have 1 argument. \n" +
                    "____________________________________________________________\n";
            System.out.println(outMsg);
        }
        index = Integer.parseInt(wordsList.get(1)) - 1;
        if (index > 0) {
            this.taskManager.setTaskDone(index, mark);
        }
        else {
            outMsg = "____________________________________________________________\n" +
                    "Invalid Index! Has to be more than 0\n" +
                    "____________________________________________________________\n";
            System.out.println(outMsg);
        }
    }

    public void parse(String inputString){
        /*
        Function to parse user input, checking if its a command keyword. i.e. List
        Modify the passed in arrayList as needed by the command.
        Separated by the space, the first word is the command while the second word is the argument
        */
        // conversion from string arrays to List string https://stackoverflow.com/questions/2607289/converting-array-to-list-in-java
        List<String> wordsList = Arrays.asList(inputString.split("\\s+"));
        ArrayList<String> argsList = new ArrayList<>();
        String command = wordsList.get(0);
        String outMsg = "";
        String taskName = "";
        String taskArg = "";
        int index = -1;
        switch (command){
            case "list":
                this.taskManager.listTasks();
                break;
            case "mark":
                setMark(wordsList, true);
                break;
            case "unmark":
                setMark(wordsList, false);
                break;
            case "todo":
                if (wordsList.size() < 2) {
                    outMsg = "____________________________________________________________\n" +
                            "Wrong number of arguments. todo should have 1 argument. \n" +
                            "____________________________________________________________\n";
                    System.out.println(outMsg);
                    break;
                }
                taskName = wordsList.stream()
                        .reduce((x,y) -> x + " " + y)
                        .orElse("");
                this.taskManager.addTask(command, taskName, argsList);
                break;
            case "deadline":
                if (!wordsList.contains("/by")) {
                    outMsg = "____________________________________________________________\n" +
                            "No '/by' detected. /by is a required argument for deadlines. \n" +
                            "____________________________________________________________\n";
                    System.out.println(outMsg);
                    break;
                }
                // discovery of indexof function came from 2030 and https://www.baeldung.com/java-array-find-index
                if (wordsList.indexOf("/by") == 1) {
                    outMsg = "____________________________________________________________\n" +
                            "No task name detected. Provide one between 'deadline' and '/by' as an argument. \n" +
                            "____________________________________________________________\n";
                    System.out.println(outMsg);
                    break;
                }
                if (wordsList.indexOf("/by") + 1 == wordsList.size()) {
                    outMsg = "____________________________________________________________\n" +
                            "No deadline date detected. Provide one after '/by' as an argument. \n" +
                            "____________________________________________________________\n";
                    System.out.println(outMsg);
                    break;
                }
                // use of sublist came from 2030, but also available on java docs anyway
                taskName = wordsList.subList(0, wordsList.indexOf("/by"))
                        .stream()
                        .reduce((x,y) -> x + " " + y)
                        .orElse("");
                taskArg = wordsList.subList(wordsList.indexOf("/by") + 1, wordsList.size())
                        .stream()
                        .reduce((x,y) -> x + " " + y)
                        .orElse("");
                argsList.add(taskArg);
                this.taskManager.addTask(command, taskName, argsList);
                break;
            case "event":
                if (!wordsList.contains("/from") || !wordsList.contains("/to")) {
                    outMsg = "____________________________________________________________\n" +
                            "'/from' and '/to' are not satisfied. Make sure to include them both as argumentss. \n" +
                            "____________________________________________________________\n";
                    System.out.println(outMsg);
                    break;
                }
                if (wordsList.indexOf("/from") == 1) {
                    outMsg = "____________________________________________________________\n" +
                            "No task name detected. Provide one between 'event' and '/from' as an argument. \n" +
                            "____________________________________________________________\n";
                    System.out.println(outMsg);
                    break;
                }
                if (wordsList.indexOf("/to") < wordsList.indexOf("/from")) {
                    outMsg = "____________________________________________________________\n" +
                            "'/from' should be before '/to'  \n" +
                            "____________________________________________________________\n";
                    System.out.println(outMsg);
                    break;
                }
                if (wordsList.indexOf("/to") == wordsList.indexOf("/from") + 1 ||
                        wordsList.indexOf("/to") + 1 == wordsList.size()) {
                    outMsg = "____________________________________________________________\n" +
                            "Date arguments for '/from' and '/to' not satisfied. Make sure to include them for both. \n" +
                            "____________________________________________________________\n";
                    System.out.println(outMsg);
                    break;
                }
                taskName = wordsList.subList(0, wordsList.indexOf("/from"))
                        .stream()
                        .reduce((x,y) -> x + " " + y)
                        .orElse("");
                taskArg = wordsList.subList(wordsList.indexOf("/from") + 1, wordsList.indexOf("/to"))
                        .stream()
                        .reduce((x,y) -> x + " " + y)
                        .orElse("");
                argsList.add(taskArg);
                taskArg = wordsList.subList(wordsList.indexOf("/to") + 1, wordsList.size())
                        .stream()
                        .reduce((x,y) -> x + " " + y)
                        .orElse("");
                argsList.add(taskArg);
                this.taskManager.addTask(command, taskName, argsList);
                break;
            default:
                outMsg = "____________________________________________________________\n" +
                        "Woops, thats not a valid command. Try again! \n" +
                        "____________________________________________________________\n";
                System.out.println(outMsg);

        }
    }
}
