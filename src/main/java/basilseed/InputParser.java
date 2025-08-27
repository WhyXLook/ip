package basilseed;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// TODO: extract the other common methods out to become functions
//  now need to do a new "getArg" function
// TODO: All functions with // wordsList.get(0) would give the command. We are assuming command has already been verified.
//  just pass in command.

public class InputParser {
    private TaskManager taskManager;

    public InputParser(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    private boolean wrongArgNum (List<String> wordsList, int argNum){
        if (wordsList.size() < argNum) {
            String outMsg = String.format("____________________________________________________________\n" +
                    "Wrong number of arguments. %s should have %d argument. \n" +
                    "____________________________________________________________\n", wordsList.get(0), argNum - 1);
            System.out.println(outMsg);
            return true;
        }
        return false;
    }

    private boolean argNotInteger (String command, List<String> wordsList){
        try {
            int index = Integer.parseInt(wordsList.get(1));
        }
        catch (NumberFormatException e) {
            String outMsg = String.format("____________________________________________________________\n" +
                    "Argument is not a number! \nExample usage: %s 2 \n" +
                    "____________________________________________________________\n", command);
            System.out.println(outMsg);
            return true;
        }
        return false;
    }

    private boolean indexOutOfBounds (int index){
        if (this.taskManager.indexOutOfBounds(index)) {
            String outMsg = "____________________________________________________________\n" +
                    "Index out of bounds! Has to be more than zero and equal or less than task list size!\n" +
                    "____________________________________________________________\n";
            System.out.println(outMsg);
            return true;
        }
        return false;
    }

    private String getTaskName (List<String> wordsList, String argKeyword){
        int index = -1;
        if (argKeyword.isEmpty()){
            // set to max so that when we subList later taskName will be empty.
            // Which is how we check the result
            index = wordsList.size();
        }
        else {
            index = wordsList.indexOf(argKeyword);
        }
        String taskName = wordsList.subList(0, index)
                .stream()
                .reduce((x,y) -> x + " " + y)
                .orElse("");
        return taskName;
    }

    private boolean taskNameNotFound(List<String> wordsList, String firstArgKeyword){
        // discovery of indexof function came from 2030 and https://www.baeldung.com/java-array-find-index
        // wordsList.get(0) would give the command. We are assuming command has already been verified.
        if (wordsList.indexOf(firstArgKeyword) == 1) {
            String outMsg = String.format("____________________________________________________________\n" +
                    "No task name detected. Provide one between the command %s and %s as an argument. \n" +
                    "____________________________________________________________\n", wordsList.get(0), firstArgKeyword);
            System.out.println(outMsg);
            return true;
        }
        return false;
    }

    private boolean argKeywordNotFound(List<String> wordsList, String argKeyword ){
        // wordsList.get(0) would give the command. We are assuming command has already been verified.
        if (!wordsList.contains(argKeyword)) {
            String outMsg = String.format("____________________________________________________________\n" +
                    "No '%s' detected. %s is a required argument for %s. \n" +
                    "____________________________________________________________\n", argKeyword, argKeyword, wordsList.get(0));
            System.out.println(outMsg);
            return true;
        }
        return false;
    }

    private boolean argKeywordOrderWrong (List<String> wordsList, List<String> argKeywordList){
        int index = 0;
        int prevIndex = -1;
        for (String keyword : wordsList){
            index = argKeywordList.indexOf(keyword);
            if (index == -1){
                continue; // likely is just argument to keyword
            }
            if (prevIndex >= index){
                String outMsg = String.format("____________________________________________________________\n" +
                        "%s should be before %s \n" +
                        "Keywords in order: ",argKeywordList.get(index), argKeywordList.get(prevIndex) );
                outMsg = outMsg + String.join(" ", argKeywordList) + "\n";
                System.out.println(outMsg);
                return true;
            }
            prevIndex = index;
        }
        return false;
    }

    private boolean noArgSupplied(List<String> wordsList, List<String> argKeywordList,
                                  String argKeyword, String argType){
        // wordsList.get(0) would give the command. We are assuming command has already been verified.
        if ( (wordsList.indexOf(argKeyword) + 1 == wordsList.size()) ||
                // next line is basically checking if the next arg after the target keyword is another keyword
                argKeywordList.contains(wordsList.get(wordsList.indexOf(argKeyword) + 1))) {
            String outMsg = String.format("____________________________________________________________\n" +
                    "No %s %s detected. Provide one after %s as an argument. \n" +
                    "____________________________________________________________\n",
                    wordsList.get(0), argType, argKeyword);
            System.out.println(outMsg);
            return true;
        }
        return false;
    }

    private String getArg(List<String> wordsList, String argKeyword, String enderArgKeyword){
        int enderIndex = wordsList.size();
        if (!enderArgKeyword.isEmpty()){
            enderIndex = wordsList.indexOf(enderArgKeyword);
        }
        String taskArg = wordsList.subList(wordsList.indexOf(argKeyword) + 1, enderIndex)
                .stream()
                .reduce((x,y) -> x + " " + y)
                .orElse("");
        return taskArg;
    }

    private void setMark(List<String> wordsList, boolean mark){
        String outMsg = "";
        int index = -1;
        if (wrongArgNum(wordsList, 2)){
            return;
        }
        if (argNotInteger(wordsList.get(0),wordsList)){
            return;
        }
        index = Integer.parseInt(wordsList.get(1));
        if (indexOutOfBounds(index)){
            return;
        }
        else {
            this.taskManager.setTaskDone(index - 1, mark);
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
                if (wrongArgNum(wordsList, 2)) {
                    break;
                }
                taskName = getTaskName(wordsList, "");
                this.taskManager.addTask(command, taskName, argsList);
                break;
            case "deadline":
                if (argKeywordNotFound(wordsList, "/by")){
                    break;
                }
                if (taskNameNotFound(wordsList,"/by")){
                    break;
                }
                if (noArgSupplied(wordsList, List.of("/by"),
                        "/by", "date")){
                    break;
                }
                taskName = getTaskName(wordsList, "/by");
                taskArg = getArg(wordsList, "/by","" );

                argsList.add(taskArg);
                this.taskManager.addTask(command, taskName, argsList);
                break;
            case "event":
                if (argKeywordNotFound(wordsList, "/from") ||
                        argKeywordNotFound(wordsList, "/to")){
                    break;
                }
                if (taskNameNotFound(wordsList, "/from")) {
                    break;
                }
                if(argKeywordOrderWrong(wordsList, List.of("/from", "/to"))){
                    break;
                }
                if(noArgSupplied(wordsList, List.of("/from", "/to"), "/from", "date")
                || noArgSupplied(wordsList, List.of("/from", "/to"), "/to", "date")){
                   break;
                }
                taskName = getTaskName(wordsList, "/from");
                taskArg = getArg(wordsList, "/from","/to" );
                argsList.add(taskArg);
                taskArg = getArg(wordsList, "/to","");
                argsList.add(taskArg);
                this.taskManager.addTask(command, taskName, argsList);
                break;
            case "delete":
                if (wrongArgNum(wordsList, 2)) {
                    break;
                }
                if (argNotInteger(wordsList.get(0),wordsList)){
                    break;
                }
                index = Integer.parseInt(wordsList.get(1));
                if(indexOutOfBounds(index)){
                    break;
                }
                else {
                    this.taskManager.deleteTask(index - 1);
                }
                break;
            default:
                outMsg = "____________________________________________________________\n" +
                        "Woops, thats not a valid command. Try again! \n" +
                        "____________________________________________________________\n";
                System.out.println(outMsg);

        }
    }
}
