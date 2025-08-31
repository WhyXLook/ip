package basilseed;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import basilseed.task.Task;
import basilseed.task.TaskManager;
import basilseed.ui.UiError;


public class InputParser {
    private static final String STORAGE_DATE_FORMAT = Task.STORAGE_DATE_FORMAT;
    private static final String INPUT_DATE_FORMAT = Task.INPUT_DATE_FORMAT;

    private TaskManager taskManager;
    private UiError uiError;

    public InputParser(TaskManager taskManager, UiError uiError) {
        this.taskManager = taskManager;
        this.uiError = uiError;
    }

    private String getCommand (List<String> commandList,String inputString ){
        if (commandList.contains(inputString)){
            return inputString;
        }

        if (inputString.matches("\\[T\\]\\[.*\\]")){
            return "todo";
        } else if (inputString.matches("\\[E\\]\\[.*\\]")){
            return "event";
        } else if (inputString.matches("\\[D\\]\\[.*\\]")){
            return "deadline";
        }
        return "";
    }

    private boolean marked (String inputString){
        if (inputString.matches("\\[.\\]\\[X\\]")){
            return true;
        }
        return false;
    }

    private boolean wrongArgNum (List<String> wordsList, int argNum, String command){
        // We are assuming command has already been verified.
        if (wordsList.size() <= argNum ) {
            this.uiError.displayWrongArgNum(command, argNum);
            return true;
        }
        return false;
    }

    private boolean argNotInteger (String command, List<String> wordsList){
        try {
            int index = Integer.parseInt(wordsList.get(1));
        }
        catch (NumberFormatException e) {
            this.uiError.displayArgNotInteger(command);
            return true;
        }
        return false;
    }

    private boolean indexOutOfBounds (int index){
        if (this.taskManager.indexOutOfBounds(index)) {
            this.uiError.displayIndexOutOfBounds();
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
        String taskName = wordsList.subList(1, index)
                .stream()
                .reduce((x,y) -> x + " " + y)
                .orElse("");
        return taskName;
    }

    private boolean taskNameNotFound(List<String> wordsList, String firstArgKeyword, String command){
        // discovery of indexof function came from 2030 and https://www.baeldung.com/java-array-find-index
        // We are assuming command has already been verified.
        if (wordsList.indexOf(firstArgKeyword) == 1) {
            this.uiError.displayTaskNameNotFound(firstArgKeyword,command);
            return true;
        }
        return false;
    }

    private boolean argKeywordNotFound(List<String> wordsList, String argKeyword, String command){
        // We are assuming command has already been verified.
        if (!wordsList.contains(argKeyword)) {
            this.uiError.displayArgKeywordNotFound(argKeyword, command);
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
                this.uiError.displayArgKeywordOrderWrong(argKeywordList);
                return true;
            }
            prevIndex = index;
        }
        return false;
    }

    private boolean noArgSupplied(List<String> wordsList, List<String> argKeywordList,
                                  String argKeyword, String argType, String command){
        // We are assuming command has already been verified.
        if ( (wordsList.indexOf(argKeyword) + 1 == wordsList.size()) ||
                // next line is basically checking if the next arg after the target keyword is another keyword
                argKeywordList.contains(wordsList.get(wordsList.indexOf(argKeyword) + 1))) {
            this.uiError.displayNoArgSupplied(argKeyword, argType, command);
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

    private String validDateType (String dateString){
        List<String> dateTypes = new ArrayList<>();
        //formatters.add(DateTimeFormatter.ofPattern(STORAGE_DATE_FORMAT));
        //formatters.add(DateTimeFormatter.ofPattern(INPUT_DATE_FORMAT));
        dateTypes.add(STORAGE_DATE_FORMAT);
        dateTypes.add(INPUT_DATE_FORMAT);

        for (String dateType : dateTypes) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateType);
            try {
                LocalDate date = LocalDate.parse(dateString, formatter);
                return dateType;
            } catch (DateTimeParseException e) {
                // do nothing here, aka try next date format
            }
        }
        this.uiError.displayValidDateType();
        return "";
    }

    private void setMark(List<String> wordsList, boolean mark, String command){
        // We are assuming command has already been verified.
        String outMsg = "";
        int index = -1;
        if (wrongArgNum(wordsList, 1, command)){
            return;
        }
        if (argNotInteger(command,wordsList)){
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
        String command = getCommand(List.of("list", "mark", "unmark", "todo", "deadline", "event", "delete"),
                wordsList.get(0));
        boolean isMarked = marked(wordsList.get(0));
        String outMsg = "";
        String taskName = "";
        String taskArg = "";
        String dateType = "";
        int index = -1;
        switch (command){
        case "list":
            this.taskManager.listTasks();
            break;
        case "mark":
            setMark(wordsList, true, command);
            break;
        case "unmark":
            setMark(wordsList, false, command);
            break;
        case "todo":
            if (wrongArgNum(wordsList, 1, command)) {
                break;
            }
            taskName = getTaskName(wordsList, "");
            this.taskManager.addTask(command, taskName, argsList, isMarked, dateType);
            break;
        case "deadline":
            if (argKeywordNotFound(wordsList, "/by", command)) {
                break;
            }
            if (taskNameNotFound(wordsList,"/by", command)) {
                break;
            }
            if (noArgSupplied(wordsList, List.of("/by"),
                    "/by", "date", command)) {
                break;
            }
            taskName = getTaskName(wordsList, "/by");
            taskArg = getArg(wordsList, "/by","" );
            dateType = validDateType(taskArg);
            if (Objects.equals(dateType, "")){
                break;
            }
            argsList.add(taskArg);
            this.taskManager.addTask(command, taskName, argsList, isMarked, dateType);
            break;
        case "event":
            if (argKeywordNotFound(wordsList, "/from", command) ||
                    argKeywordNotFound(wordsList, "/to", command)){
                break;
            }
            if (taskNameNotFound(wordsList, "/from", command)) {
                break;
            }
            if(argKeywordOrderWrong(wordsList, List.of("/from", "/to"))){
                break;
            }
            if(noArgSupplied(wordsList, List.of("/from", "/to"), "/from", "date", command)
            || noArgSupplied(wordsList, List.of("/from", "/to"), "/to", "date", command)){
               break;
            }
            taskName = getTaskName(wordsList, "/from");
            taskArg = getArg(wordsList, "/from","/to" );
            dateType = validDateType(taskArg);
            if (Objects.equals(dateType, "")){
                break;
            }
            argsList.add(taskArg);
            taskArg = getArg(wordsList, "/to","");
            dateType = validDateType(taskArg);
            if (Objects.equals(dateType, "")){
                break;
            }
            argsList.add(taskArg);
            this.taskManager.addTask(command, taskName, argsList, isMarked, dateType);
            break;
        case "delete":
            if (wrongArgNum(wordsList, 1, command)) {
                break;
            }
            if (argNotInteger(command,wordsList)){
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
            this.uiError.displayInvalidCommand();
        }
    }
}
