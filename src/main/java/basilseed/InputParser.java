package basilseed;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import basilseed.exception.BasilSeedInvalidInputException;
import basilseed.task.Task;

import basilseed.command.Command;
import basilseed.command.DeadlineCommand;
import basilseed.command.DeleteCommand;
import basilseed.command.EventCommand;
import basilseed.command.FindCommand;
import basilseed.command.ListCommand;
import basilseed.command.MarkCommand;
import basilseed.command.ToDoCommand;
import basilseed.command.UnMarkCommand;
import basilseed.ui.UiError;

/**
 * Parses and validates user input into commands, arguments, and task attributes.
 * Ensures correctness of argument count, types, keywords, and date formats.
 * Extracts command details (task names, arguments)
 */
public class InputParser {
    private static final String STORAGE_DATE_FORMAT = Task.STORAGE_DATE_FORMAT;
    private static final String INPUT_DATE_FORMAT = Task.INPUT_DATE_FORMAT;
    private static final int STORAGE_COMMAND_STRING_LENGTH = 3;
    private UiError uiError = new UiError();

    /**
     * Creates an InputParser
     */
    public InputParser() {
    }

    private static void assertCommandVerified(String command) {
        assert (command.equals("find")
                || command.equals("list")
                || command.equals("delete")
                || command.equals("mark")
                || command.equals("unmark")
                || command.equals("todo")
                || command.equals("deadline")
                || command.equals("event")
        );
    }

    private void wrongArgNumCheck(List<String> wordsList, int argNum, String command)
            throws BasilSeedInvalidInputException {
        // We are assuming command has already been verified.
        assertCommandVerified(command);
        if (wordsList.size() <= argNum) {
            throw new BasilSeedInvalidInputException(this.uiError.returnWrongArgNum(command, argNum));
        }
    }

    private void argNotIntegerCheck(String command, List<String> wordsList) throws BasilSeedInvalidInputException {
        try {
            int index = Integer.parseInt(wordsList.get(1));
        } catch (NumberFormatException e) {
            throw new BasilSeedInvalidInputException(this.uiError.returnArgNotInteger(command));
        }
    }

    private void indexOutOfBoundsCheck(int index, int bounds) throws BasilSeedInvalidInputException {
        if (index <= 0 || index > bounds) {
            throw new BasilSeedInvalidInputException(this.uiError.returnIndexOutOfBounds());
        }
    }

    private void taskNameNotFoundCheck(List<String> wordsList, String firstArgKeyword, String command)
            throws BasilSeedInvalidInputException {
        // We are assuming command has already been verified.
        assertCommandVerified(command);
        if (wordsList.indexOf(firstArgKeyword) == 1) {
            throw new BasilSeedInvalidInputException(this.uiError.returnTaskNameNotFound(firstArgKeyword, command));
        }
    }

    private void argKeywordNotFoundCheck(List<String> wordsList, String argKeyword, String command)
            throws BasilSeedInvalidInputException {
        // We are assuming command has already been verified.
        assertCommandVerified(command);
        if (!wordsList.contains(argKeyword)) {
            throw new BasilSeedInvalidInputException(this.uiError.returnArgKeywordNotFound(argKeyword, command));
        }
    }

    private void argKeywordOrderWrongCheck(List<String> wordsList, List<String> argKeywordList)
            throws BasilSeedInvalidInputException {
        int index = 0;
        int prevIndex = -1;
        for (String keyword : wordsList) {
            index = argKeywordList.indexOf(keyword);
            if (index == -1) {
                continue; // likely is just argument to keyword
            }
            if (prevIndex >= index) {
                throw new BasilSeedInvalidInputException(this.uiError.returnArgKeywordOrderWrong(argKeywordList));
            }
            prevIndex = index;
        }
    }

    private void noArgSupplied(List<String> wordsList, List<String> argKeywordList,
            String argKeyword, String argType, String command) throws BasilSeedInvalidInputException {
        // We are assuming command has already been verified.
        assertCommandVerified(command);
        if ((wordsList.indexOf(argKeyword) + 1 == wordsList.size())
                // next line is basically checking if the next arg after the target keyword is another keyword
                || argKeywordList.contains(wordsList.get(wordsList.indexOf(argKeyword) + 1))) {
            throw new BasilSeedInvalidInputException(this.uiError.returnNoArgSupplied(argKeyword, argType, command));
        }
    }

    private String getArg(List<String> wordsList, String argKeyword, String enderArgKeyword) {
        int endIndex = wordsList.size();
        if (!enderArgKeyword.isEmpty()) {
            endIndex = wordsList.indexOf(enderArgKeyword);
        }
        int startIndex = argKeyword.isEmpty() ? 1 : wordsList.indexOf(argKeyword) + 1;
        String taskArg = wordsList.subList(startIndex, endIndex)
                .stream()
                .reduce((x, y) -> x + " " + y)
                .orElse("");
        return taskArg;
    }

    private void markNotValidCheck(String inputString, String command, int bounds)
            throws BasilSeedInvalidInputException {
        List<String> wordsList = Arrays.asList(inputString.split("\\s+"));
        assertCommandVerified(command);
        // We are assuming command has already been verified.
        int index = -1;
        wrongArgNumCheck(wordsList, 1, command);
        argNotIntegerCheck(command, wordsList);
        index = Integer.parseInt(wordsList.get(1));
        indexOutOfBoundsCheck(index, bounds);
    }

    private String validDateType(String dateString) throws BasilSeedInvalidInputException {
        List<String> dateTypes = new ArrayList<>();
        dateTypes.add(STORAGE_DATE_FORMAT);
        dateTypes.add(INPUT_DATE_FORMAT);
        for (String dateType : dateTypes) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateType);
            try {
                LocalDate.parse(dateString, formatter);
                return dateType;
            } catch (DateTimeParseException e) {
                // do nothing first because the other dates could be correct
            }
        }
        throw new BasilSeedInvalidInputException(this.uiError.returnInvalidDateType());
    }

    /**
     * Returns whether the input string represents a marked task.
     *
     * @param inputString String representation of the task.
     * @return True if task is marked, false otherwise.
     */
    private boolean isMarked(String inputString) {
        if (inputString.matches("\\[.\\]\\[X\\]")) {
            return true;
        }
        return false;
    }


    /**
     * Returns all argument values parsed from the input string.
     *
     * @param inputString User input.
     * @param argKeywords keywords that are used as signposts to parse the relevant arguments
     * @return List of argument values. Excludes the argument keywords and task name.
     */
    private List<String> getAllArgs(String inputString, List<String> argKeywords) {
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

    private Command parseList() {
        return new ListCommand(List.of());
    }

    private Command parseMark(String inputString, String firstWord, int taskListSize)
            throws BasilSeedInvalidInputException {
        markNotValidCheck(inputString, firstWord, taskListSize);
        List<String> allArgs = getAllArgs(inputString, MarkCommand.KEYWORDS);
        return new MarkCommand(allArgs);
    }

    private Command parseUnMark(String inputString, String firstWord, int taskListSize)
            throws BasilSeedInvalidInputException {
        markNotValidCheck(inputString, firstWord, taskListSize);
        List<String> allArgs = getAllArgs(inputString, MarkCommand.KEYWORDS);
        return new UnMarkCommand(allArgs);
    }

    private void checkToDo(String firstWord, List<String> wordsList)
            throws BasilSeedInvalidInputException {
        wrongArgNumCheck(wordsList, 1, firstWord);
    }

    private Command processToDo(String inputString, List<String> wordsList) {
        List<String> allArgs = getAllArgs(inputString, ToDoCommand.KEYWORDS);
        boolean isDone = isMarked(inputString);
        String taskName = getArg(wordsList, "", "");
        return new ToDoCommand(allArgs, taskName, isDone);
    }

    private Command parseTodo(String inputString, String firstWord, List<String> wordsList)
            throws BasilSeedInvalidInputException {
        checkToDo(firstWord, wordsList);
        return processToDo(inputString, wordsList);
    }

    private void checkDeadline(String inputString, String firstWord, List<String> wordsList)
            throws BasilSeedInvalidInputException {
        argKeywordNotFoundCheck(wordsList, DeadlineCommand.KEYWORDS.get(0), firstWord);
        taskNameNotFoundCheck(wordsList, DeadlineCommand.KEYWORDS.get(0), firstWord);
        noArgSupplied(wordsList, DeadlineCommand.KEYWORDS,
            DeadlineCommand.KEYWORDS.get(0), "date", firstWord);
        List<String> allArgs = getAllArgs(inputString, DeadlineCommand.KEYWORDS);
        validDateType(allArgs.get(0));
    }

    private Command processDeadline(String inputString, List<String> wordsList)
            throws BasilSeedInvalidInputException {
        List<String> allArgs = getAllArgs(inputString, DeadlineCommand.KEYWORDS);
        String dateType = validDateType(allArgs.get(0));
        boolean isDone = isMarked(inputString);
        String taskName = getArg(wordsList, "", DeadlineCommand.KEYWORDS.get(0));
        return new DeadlineCommand(allArgs, taskName, isDone, dateType);
    }

    private Command parseDeadline(String inputString, String firstWord, List<String> wordsList)
            throws BasilSeedInvalidInputException {
        checkDeadline(inputString, firstWord, wordsList);
        return processDeadline(inputString, wordsList);
    }

    private void checkEvent(String inputString, String firstWord, List<String> wordsList)
            throws BasilSeedInvalidInputException {
        argKeywordNotFoundCheck(wordsList, EventCommand.KEYWORDS.get(0), firstWord);
        argKeywordNotFoundCheck(wordsList, EventCommand.KEYWORDS.get(1), firstWord);
        taskNameNotFoundCheck(wordsList, EventCommand.KEYWORDS.get(0), firstWord);
        argKeywordOrderWrongCheck(wordsList, EventCommand.KEYWORDS);
        noArgSupplied(wordsList, EventCommand.KEYWORDS, EventCommand.KEYWORDS.get(0), "date", firstWord);
        noArgSupplied(wordsList, EventCommand.KEYWORDS, EventCommand.KEYWORDS.get(1), "date", firstWord);
        List<String> allArgs = getAllArgs(inputString, EventCommand.KEYWORDS);
        for (String arg : allArgs) {
            validDateType(arg);
        }
    }

    private Command processEvent(String inputString, List<String> wordsList)
            throws BasilSeedInvalidInputException {
        List<String> allArgs = getAllArgs(inputString, EventCommand.KEYWORDS);
        String dateType = "";
        for (String arg : allArgs) {
            dateType = validDateType(arg);
        }
        boolean isDone = isMarked(inputString);
        String taskName = getArg(wordsList, "", EventCommand.KEYWORDS.get(0));
        return new EventCommand(allArgs, taskName, isDone, dateType);
    }

    private Command parseEvent(String inputString, String firstWord, List<String> wordsList)
            throws BasilSeedInvalidInputException {
        checkEvent(inputString, firstWord, wordsList);
        return processEvent(inputString, wordsList);
    }

    private Command parseDelete(String inputString, String firstWord, List<String> wordsList, int taskListSize)
            throws BasilSeedInvalidInputException {
        wrongArgNumCheck(wordsList, 1, firstWord);
        argNotIntegerCheck(firstWord, wordsList);
        List<String> allArgs = getAllArgs(inputString, DeleteCommand.KEYWORDS);
        int index = Integer.parseInt(allArgs.get(0));
        indexOutOfBoundsCheck(index, taskListSize);
        return new DeleteCommand(allArgs);
    }

    private Command parseFind(String inputString, String firstWord, List<String> wordsList)
            throws BasilSeedInvalidInputException {
        wrongArgNumCheck(wordsList, 1, firstWord);
        List<String> allArgs = getAllArgs(inputString, ToDoCommand.KEYWORDS);
        return new FindCommand(allArgs);
    }


    // TODO: Extract out the bottom part of parse to become parseStorage

    private List<String> splitStringIntoList(String inputString){
        return Arrays.asList(inputString.split("\\s+"));
    }

    private String takeFirstWord(List<String> wordsList) throws BasilSeedInvalidInputException {
        if (wordsList.isEmpty()) {
            throw new BasilSeedInvalidInputException(this.uiError.returnInvalidCommand());
        }
        return wordsList.get(0);
    }

    /**
     * Parses and validates the given input string against the current task list size.
     * Will only run checks.
     *
     * @param inputString User input.
     * @param taskListSize Size of the task list.
     * @return a Command object that can take in a task manager for execution, empty string otherwise.
     */
    public Command parse(String inputString, int taskListSize) throws BasilSeedInvalidInputException {
        /*
        Function to parse user input, checking if its a command keyword. i.e. List
        Modify the passed in arrayList as needed by the command.
        Separated by the space, the first word is the command while the second word is the argument
        */
        // conversion from string arrays to List string Solution below inspired by
        // https://stackoverflow.com/questions/2607289/converting-array-to-list-in-java
        List<String> wordsList = splitStringIntoList(inputString);
        String firstWord = takeFirstWord(wordsList);
        switch (firstWord) {
        case "list":
            return parseList();
        case "mark":
            return parseMark(inputString, firstWord, taskListSize);
        case "unmark":
            return parseUnMark(inputString, firstWord, taskListSize);
        case "todo":
            return parseTodo(inputString, firstWord, wordsList);
        case "deadline":
            return parseDeadline(inputString, firstWord, wordsList);
        case "event":
            return parseEvent(inputString, firstWord, wordsList);
        case "delete":
            return parseDelete(inputString, firstWord, wordsList, taskListSize);
        case "find":
            return parseFind(inputString, firstWord, wordsList);
        default: // From invalid command.
            throw new BasilSeedInvalidInputException(this.uiError.returnInvalidCommand());
        }
    }

    /**
     * Parses given input string from storage against the current task list size.
     * Will only run checks.
     *
     * @param inputString User input.
     * @param taskListSize Size of the task list.
     * @return a Command object that can take in a task manager for execution, empty string otherwise.
     */
    public Command parseFromStorage(String inputString, int taskListSize) throws BasilSeedInvalidInputException {
        List<String> wordsList = splitStringIntoList(inputString);
        String firstWord = takeFirstWord(wordsList);
        switch (firstWord.substring(0, STORAGE_COMMAND_STRING_LENGTH)) {
        case "[T]":
            return processToDo(inputString, wordsList);
        case "[E]":
            return processEvent(inputString, wordsList);
        case "[D]":
            return processDeadline(inputString, wordsList);
        default: // should not happen as storage strings are all correct
            throw new BasilSeedInvalidInputException(this.uiError.returnInvalidCommand());
        }
    }

}
