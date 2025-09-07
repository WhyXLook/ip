package basilseed;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import basilseed.task.Task;
import basilseed.ui.UiError;

import basilseed.command.Command;
import basilseed.command.DeadlineCommand;
import basilseed.command.DeleteCommand;
import basilseed.command.EventCommand;
import basilseed.command.FindCommand;
import basilseed.command.ListCommand;
import basilseed.command.MarkCommand;
import basilseed.command.ToDoCommand;
import basilseed.command.UnMarkCommand;

/**
 * Parses and validates user input into commands, arguments, and task attributes.
 * Ensures correctness of argument count, types, keywords, and date formats.
 * Extracts command details (task names, arguments)
 * Reports errors through UiError.
 */
public class InputParser {
    private static final String STORAGE_DATE_FORMAT = Task.STORAGE_DATE_FORMAT;
    private static final String INPUT_DATE_FORMAT = Task.INPUT_DATE_FORMAT;

    private UiError uiError;

    /**
     * Creates an InputParserCopy with the specified UiError handler.
     *
     * @param uiError UI handler for displaying errors.
     */
    public InputParser(UiError uiError) {
        this.uiError = uiError;
    }

    private boolean wrongArgNum(List<String> wordsList, int argNum, String command) {
        // We are assuming command has already been verified.
        if (wordsList.size() <= argNum) {
            this.uiError.displayWrongArgNum(command, argNum);
            return true;
        }
        return false;
    }

    private boolean argNotInteger(String command, List<String> wordsList) {
        try {
            int index = Integer.parseInt(wordsList.get(1));
        } catch (NumberFormatException e) {
            this.uiError.displayArgNotInteger(command);
            return true;
        }
        return false;
    }

    private boolean indexOutOfBounds(int index, int bounds) {
        if (index <= 0 || index > bounds) {
            this.uiError.displayIndexOutOfBounds();
            return true;
        }
        return false;
    }

    private boolean taskNameNotFound(List<String> wordsList, String firstArgKeyword, String command) {
        // We are assuming command has already been verified.
        if (wordsList.indexOf(firstArgKeyword) == 1) {
            this.uiError.displayTaskNameNotFound(firstArgKeyword,command);
            return true;
        }
        return false;
    }

    private boolean argKeywordNotFound(List<String> wordsList, String argKeyword, String command) {
        // We are assuming command has already been verified.
        if (!wordsList.contains(argKeyword)) {
            this.uiError.displayArgKeywordNotFound(argKeyword, command);
            return true;
        }
        return false;
    }

    private boolean argKeywordOrderWrong(List<String> wordsList, List<String> argKeywordList) {
        int index = 0;
        int prevIndex = -1;
        for (String keyword : wordsList) {
            index = argKeywordList.indexOf(keyword);
            if (index == -1) {
                continue; // likely is just argument to keyword
            }
            if (prevIndex >= index) {
                this.uiError.displayArgKeywordOrderWrong(argKeywordList);
                return true;
            }
            prevIndex = index;
        }
        return false;
    }

    private boolean noArgSupplied(List<String> wordsList, List<String> argKeywordList,
                                  String argKeyword, String argType, String command) {
        // We are assuming command has already been verified.
        if ((wordsList.indexOf(argKeyword) + 1 == wordsList.size()) ||
                // next line is basically checking if the next arg after the target keyword is another keyword
                argKeywordList.contains(wordsList.get(wordsList.indexOf(argKeyword) + 1))) {
            this.uiError.displayNoArgSupplied(argKeyword, argType, command);
            return true;
        }
        return false;
    }

    private String getArg(List<String> wordsList, String argKeyword, String enderArgKeyword) {
        int endIndex = wordsList.size();
        if (!enderArgKeyword.isEmpty()) {
            endIndex = wordsList.indexOf(enderArgKeyword);
        }
        int startIndex = argKeyword.isEmpty() ? 1 : wordsList.indexOf(argKeyword) + 1;
        String taskArg = wordsList.subList(startIndex, endIndex)
                .stream()
                .reduce((x,y) -> x + " " + y)
                .orElse("");
        return taskArg;
    }

    private boolean markNotValid(String inputString, String command, int bounds) {
        List<String> wordsList = Arrays.asList(inputString.split("\\s+"));
        // We are assuming command has already been verified.
        int index = -1;
        if (wrongArgNum(wordsList, 1, command)) {
            return true;
        }
        if (argNotInteger(command,wordsList)) {
            return true;
        }
        index = Integer.parseInt(wordsList.get(1));
        if (indexOutOfBounds(index, bounds)) {
            return true;
        } else {
            //this.taskManager.setTaskDone(index - 1, mark);
            return false;
        }
    }

    private String validDateType(String dateString) {
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
        return null;
    }

    /**
     * Returns whether the input string represents a marked task.
     *
     * @param inputString String representation of the task.
     * @return True if task is marked, false otherwise.
     */
    public boolean isMarked(String inputString) {
        if (inputString.matches("\\[.\\]\\[X\\]")) {
            return true;
        }
        return false;
    }

    /**
     * Returns the date format type of the first valid date found in the argument list.
     *
     * @param argList List of arguments.
     * @return Date format string if valid date found, empty string otherwise.
     */
    public String getDateType(List<String> argList) {
        this.uiError.setSilent(true);
        for (String arg : argList) {
            String dateType = validDateType(arg);
            if (dateType != null && !dateType.isEmpty()) {
                this.uiError.setSilent(false);
                return dateType;
            }
        }
        this.uiError.setSilent(false);
        return null;
    }


    /**
     * Returns the task name parsed from the input string.
     *
     * @param inputString User input.
     * @return Task name.
     */
    public String getTaskName (String inputString, String firstArgKeyword) {
        List<String> wordsList = Arrays.asList(inputString.split("\\s+"));
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

    /**
     * Returns all argument values parsed from the input string.
     *
     * @param inputString User input.
     * @param argKeywords keywords that are used as signposts to parse the relevant arguments
     * @return List of argument values. Excludes the argument keywords and task name.
     */
    public List<String> getAllArgs (String inputString, List<String> argKeywords) {
        List<String> wordsList = Arrays.asList(inputString.split("\\s+"));
        List<String> argKeywordArrayList = new ArrayList<>(argKeywords);
        argKeywordArrayList.add("");
        List<String> allArgs = new ArrayList<>();
        for (int index = 0; index < argKeywordArrayList.size() - 1; index++) {
            String arg = getArg(wordsList, argKeywordArrayList.get(index), argKeywordArrayList.get(index + 1));
            allArgs.add(arg);
        }
        return allArgs;
    }

    /**
     * Parses and validates the given input string against the current task list size.
     * Will only run checks.
     *
     * @param inputString User input.
     * @param taskListSize Size of the task list.
     * @return Input string if valid, empty string otherwise.
     */
    public Command parse(String inputString, int taskListSize){
        /*
        Function to parse user input, checking if its a command keyword. i.e. List
        Modify the passed in arrayList as needed by the command.
        Separated by the space, the first word is the command while the second word is the argument
        */
        // conversion from string arrays to List string Solution below inspired by
        // https://stackoverflow.com/questions/2607289/converting-array-to-list-in-java
        List<String> wordsList = Arrays.asList(inputString.split("\\s+"));
        if (wordsList.isEmpty()) {
            this.uiError.displayInvalidCommand();
            return null;
        }
        String firstWord = wordsList.get(0);
        String taskName = "";
        String dateType = "";
        List<String> allArgs;
        boolean isDone = false;
        int index = -1;
        switch (firstWord) {
            case "list":
                return new ListCommand(List.of());
            case "mark":
                if (markNotValid(inputString, firstWord, taskListSize)) {
                    return null;
                }
                allArgs = getAllArgs(inputString, MarkCommand.KEYWORDS);
                System.out.println("Mark command all args: " + allArgs);
                return new MarkCommand(allArgs);
            case "unmark":
                if (markNotValid(inputString, firstWord, taskListSize)) {
                    return null;
                }
                allArgs = getAllArgs(inputString, UnMarkCommand.KEYWORDS);
                return new UnMarkCommand(allArgs);
            case "todo":
                if (wrongArgNum(wordsList, 1, firstWord)) {
                    return null;
                }
                allArgs = getAllArgs(inputString, ToDoCommand.KEYWORDS);
                isDone = isMarked(inputString);
                taskName = getArg(wordsList, "", "");
                return new  ToDoCommand(allArgs, taskName, isDone);
            case "deadline":
                if (argKeywordNotFound(wordsList, DeadlineCommand.KEYWORDS.get(0), firstWord)) {
                    return null;
                }
                if (taskNameNotFound(wordsList, DeadlineCommand.KEYWORDS.get(0), firstWord)) {
                    return null;
                }
                if (noArgSupplied(wordsList, DeadlineCommand.KEYWORDS,
                    DeadlineCommand.KEYWORDS.get(0), "date", firstWord)) {
                    return null;
                }
                allArgs = getAllArgs(inputString, DeadlineCommand.KEYWORDS);
                dateType = validDateType(allArgs.get(0));
                if (dateType == null || Objects.equals(dateType, "")) {
                    return null;
                }
                isDone = isMarked(inputString);
                taskName = getArg(wordsList, "", DeadlineCommand.KEYWORDS.get(0));
                return new DeadlineCommand(allArgs, taskName, isDone, dateType);
            case "event":
                if (argKeywordNotFound(wordsList, EventCommand.KEYWORDS.get(0), firstWord) ||
                    argKeywordNotFound(wordsList, EventCommand.KEYWORDS.get(1), firstWord)) {
                    return null;
                }
                if (taskNameNotFound(wordsList, EventCommand.KEYWORDS.get(0), firstWord)) {
                    return null;
                }
                if (argKeywordOrderWrong(wordsList, EventCommand.KEYWORDS)) {
                    return null;
                }
                if (noArgSupplied(wordsList, EventCommand.KEYWORDS, EventCommand.KEYWORDS.get(0), "date", firstWord)
                    || noArgSupplied(wordsList, EventCommand.KEYWORDS, EventCommand.KEYWORDS.get(1), "date",
                    firstWord)) {
                    return null;
                }

                allArgs = getAllArgs(inputString, EventCommand.KEYWORDS);
                for (String arg : allArgs) {
                    dateType = validDateType(arg);
                    if (dateType == null || Objects.equals(dateType, "")) {
                        return null;
                    }
                }
                isDone = isMarked(inputString);
                taskName = getArg(wordsList, "", EventCommand.KEYWORDS.get(0));
                return new EventCommand(allArgs, taskName, isDone, dateType);
            case "delete":
                if (wrongArgNum(wordsList, 1, firstWord)) {
                    return null;
                }
                if (argNotInteger(firstWord,wordsList)) {
                    return null;
                }

                allArgs = getAllArgs(inputString, DeleteCommand.KEYWORDS);
                index = Integer.parseInt(allArgs.get(0));
                if (indexOutOfBounds(index, taskListSize)) {
                    return null;
                }
                else {
                    return new DeleteCommand(allArgs);
                }
            case "find":
                if (wrongArgNum(wordsList, 1, firstWord)) {
                    return null;
                }
                allArgs = getAllArgs(inputString, ToDoCommand.KEYWORDS);
                return new FindCommand(allArgs);
            default:
                if (!firstWord.isEmpty() && firstWord.length() >= 3) {
                    switch (firstWord.substring(0,3)) {
                    case "[T]":
                        allArgs = getAllArgs(inputString, ToDoCommand.KEYWORDS);
                        isDone = isMarked(inputString);
                        taskName = getArg(wordsList, "", "");
                        return new  ToDoCommand(allArgs, taskName, isDone);
                    case "[E]":
                        allArgs = getAllArgs(inputString, EventCommand.KEYWORDS);
                        dateType = validDateType(allArgs.get(0));
                        taskName = getArg(wordsList, "", EventCommand.KEYWORDS.get(0));
                        return new EventCommand(allArgs, taskName, isDone, dateType);
                    case "[D]":
                        allArgs = getAllArgs(inputString, DeadlineCommand.KEYWORDS);
                        dateType = validDateType(allArgs.get(0));
                        taskName = getArg(wordsList, "", DeadlineCommand.KEYWORDS.get(0));
                        return new DeadlineCommand(allArgs, taskName, isDone, dateType);
                    default:
                        this.uiError.displayInvalidCommand();
                        return null;
                    }
                }
                this.uiError.displayInvalidCommand();
                return null;
        }
    }

}
