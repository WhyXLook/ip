package basilseed;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import java.util.Map;

import basilseed.task.Task;
import basilseed.ui.UiError;


public class InputParser {
    private static final String STORAGE_DATE_FORMAT = Task.STORAGE_DATE_FORMAT;
    private static final String INPUT_DATE_FORMAT = Task.INPUT_DATE_FORMAT;
    public static final Map<String, List<String>> COMMAND_KEYWORDS_MAP = Map.of("list", List.of(""),
        "mark", List.of(""),
        "unmark", List.of(""),
        "todo", List.of(""),
        "deadline", List.of("/by"),
        "event", List.of("/from", "/to"),
        "delete", List.of("")
        );

    private UiError uiError;

    public InputParser(UiError uiError) {
        this.uiError = uiError;
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

    private boolean indexOutOfBounds (int index, int bounds){
        if (index <= 0 || index > bounds) {
            this.uiError.displayIndexOutOfBounds();
            return true;
        }
        return false;
    }

    private boolean taskNameNotFound(List<String> wordsList, String firstArgKeyword, String command){
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
        int endIndex = wordsList.size();
        if (!enderArgKeyword.isEmpty()){
            endIndex = wordsList.indexOf(enderArgKeyword);
        }
        int startIndex = argKeyword.isEmpty() ? 1 : wordsList.indexOf(argKeyword) + 1;
        String taskArg = wordsList.subList(startIndex, endIndex)
                .stream()
                .reduce((x,y) -> x + " " + y)
                .orElse("");
        return taskArg;
    }

    private String parseMark(String inputString, String command, int bounds){
        List<String> wordsList = Arrays.asList(inputString.split("\\s+"));
        // We are assuming command has already been verified.
        String outMsg = "";
        int index = -1;
        if (wrongArgNum(wordsList, 1, command)){
            return "";
        }
        if (argNotInteger(command,wordsList)){
            return "";
        }
        index = Integer.parseInt(wordsList.get(1));
        if (indexOutOfBounds(index, bounds)){
            return "";
        }
        else {
            //this.taskManager.setTaskDone(index - 1, mark);
            return inputString;
        }
    }

    private String validDateType(String dateString){
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

    public boolean isMarked(String inputString){
        if (inputString.matches("\\[.\\]\\[X\\]")){
            return true;
        }
        return false;
    }

    public String getDateType(List<String> argList){
        this.uiError.setSilent(true);
        for (String arg : argList) {
            String dateType = validDateType(arg);
            if (!dateType.isEmpty()){
                this.uiError.setSilent(false);
                return dateType;
            }
        }
        this.uiError.setSilent(false);
        return "";
    }

    public String getCommand (String inputString ){
        List<String> wordsList = Arrays.asList(inputString.split("\\s+"));
        String firstWord = wordsList.get(0);
        List<String> commandList = new ArrayList<>(COMMAND_KEYWORDS_MAP.keySet());
        if (commandList.contains(firstWord)){
            return firstWord;
        }

        if (firstWord.matches("\\[T\\]\\[.*\\]")){
            return "todo";
        } else if (firstWord.matches("\\[E\\]\\[.*\\]")){
            return "event";
        } else if (firstWord.matches("\\[D\\]\\[.*\\]")){
            return "deadline";
        }
        return "";
    }

    // TODO get all firstArgKeyword by doing the setKey thing
    //  then finish up on the rest (i.e. implement getCommand, getTaskName, getAllArgs (new method)
    //  If everything works do the Command thing
    public String getTaskName (String inputString){
        List<String> wordsList = Arrays.asList(inputString.split("\\s+"));
        String command = getCommand(inputString);
        String firstArgKeyword = COMMAND_KEYWORDS_MAP.get(command).get(0);
        int index = -1;
        if (firstArgKeyword.isEmpty()){
            // set to max so that when we subList later taskName will be the rest of the string
            index = wordsList.size();
        }
        else {
            index = wordsList.indexOf(firstArgKeyword);
        }
        String taskName = wordsList.subList(1, index)
                .stream()
                .reduce((x,y) -> x + " " + y)
                .orElse("");
        return taskName;
    }

    public List<String> getAllArgs (String inputString) {
        List<String> wordsList = Arrays.asList(inputString.split("\\s+"));
        String command = getCommand(inputString);
        List<String> argKeywords = new ArrayList<>(COMMAND_KEYWORDS_MAP.get(command));
        argKeywords.add("");
        List<String> allArgs = new ArrayList<>();
        for (int index = 0; index < argKeywords.size() - 1; index++) {
            String arg = getArg(wordsList, argKeywords.get(index), argKeywords.get(index + 1));
            allArgs.add(arg);
        }
        return allArgs;
    }

    public String parse(String inputString, int taskListSize){
        /*
        Function to parse user input, checking if its a command keyword. i.e. List
        Modify the passed in arrayList as needed by the command.
        Separated by the space, the first word is the command while the second word is the argument
        */
        // conversion from string arrays to List string Solution below inspired by
        // https://stackoverflow.com/questions/2607289/converting-array-to-list-in-java
        List<String> wordsList = Arrays.asList(inputString.split("\\s+"));
        //ArrayList<String> argsList = new ArrayList<>();
        String command = getCommand(wordsList.get(0));
        //boolean isMarked = isMarked(wordsList.get(0));
        String taskArg = "";
        String dateType = "";
        int index = -1;
        switch (command){
        case "list":
            return inputString;
        case "mark":
            return parseMark(inputString, command, taskListSize);
        case "unmark":
            return parseMark(inputString, command, taskListSize);
        case "todo":
            if (wrongArgNum(wordsList, 1, command)) {
                return "";
            }
            return inputString;
        case "deadline":
            if (argKeywordNotFound(wordsList, "/by", command)) {
                return "";
            }
            if (taskNameNotFound(wordsList,"/by", command)) {
                return "";
            }
            if (noArgSupplied(wordsList, List.of("/by"),
                    "/by", "date", command)) {
                return "";
            }
            taskArg = getArg(wordsList, "/by","" );
            dateType = validDateType(taskArg);
            if (Objects.equals(dateType, "")){
                return "";
            }
            return inputString;
        case "event":
            if (argKeywordNotFound(wordsList, "/from", command) ||
                    argKeywordNotFound(wordsList, "/to", command)){
                return "";
            }
            if (taskNameNotFound(wordsList, "/from", command)) {
                return "";
            }
            if(argKeywordOrderWrong(wordsList, List.of("/from", "/to"))){
                return "";
            }
            if(noArgSupplied(wordsList, List.of("/from", "/to"), "/from", "date", command)
            || noArgSupplied(wordsList, List.of("/from", "/to"), "/to", "date", command)){
                return "";
            }
            taskArg = getArg(wordsList, "/from","/to" );
            dateType = validDateType(taskArg);
            if (Objects.equals(dateType, "")){
                return "";
            }
            taskArg = getArg(wordsList, "/to","");
            dateType = validDateType(taskArg);
            if (Objects.equals(dateType, "")){
                return "";
            }
            return inputString;
        case "delete":
            if (wrongArgNum(wordsList, 1, command)) {
                return "";
            }
            if (argNotInteger(command,wordsList)){
                return "";
            }
            index = Integer.parseInt(wordsList.get(1));
            if(indexOutOfBounds(index, taskListSize)){
                return "";
            }
            else {
                return inputString;
            }
        default:
            this.uiError.displayInvalidCommand();
            return "";
        }
    }

}
