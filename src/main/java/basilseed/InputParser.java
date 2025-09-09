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

/**
 * Parses and validates user input into commands, arguments, and task attributes.
 * Ensures correctness of argument count, types, keywords, and date formats.
 * Extracts command details (task names, arguments)
 */
public class InputParser {
    private static final String STORAGE_DATE_FORMAT = Task.STORAGE_DATE_FORMAT;
    private static final String INPUT_DATE_FORMAT = Task.INPUT_DATE_FORMAT;


    /**
     * Creates an InputParser
     */
    public InputParser() {
    }

    private void wrongArgNumCheck(List<String> wordsList, int argNum, String command)
            throws BasilSeedInvalidInputException {
        // We are assuming command has already been verified.
        if (wordsList.size() <= argNum) {
            String outMsg = String.format("Wrong number of arguments. %s should have %d argument. \n",
                command, argNum);
            throw new BasilSeedInvalidInputException(outMsg);
        }
    }

    private void argNotIntegerCheck(String command, List<String> wordsList) throws BasilSeedInvalidInputException {
        try {
            int index = Integer.parseInt(wordsList.get(1));
        } catch (NumberFormatException e) {
            String outMsg = String.format(
                "Argument is not a number! \nExample usage: %s 2 \n", command);
            throw new BasilSeedInvalidInputException(outMsg);
        }
    }

    private void indexOutOfBoundsCheck(int index, int bounds) throws BasilSeedInvalidInputException {
        if (index <= 0 || index > bounds) {
            String outMsg = "Index out of bounds! Has to be more than zero and equal or less than task list size!\n";
            throw new BasilSeedInvalidInputException(outMsg);
        }
    }

    private void taskNameNotFoundCheck(List<String> wordsList, String firstArgKeyword, String command)
            throws BasilSeedInvalidInputException {
        // We are assuming command has already been verified.
        if (wordsList.indexOf(firstArgKeyword) == 1) {
            String outMsg = String.format(
                "No task name detected. Provide one between the command %s and %s as an argument. \n",
                command, firstArgKeyword);
            throw new BasilSeedInvalidInputException(outMsg);
        }
    }

    private void argKeywordNotFoundCheck(List<String> wordsList, String argKeyword, String command)
            throws BasilSeedInvalidInputException {
        // We are assuming command has already been verified.
        if (!wordsList.contains(argKeyword)) {
            String outMsg = String.format(
                "No '%s' detected. %s is a required argument for %s. \n" , argKeyword, argKeyword, command);
            throw new BasilSeedInvalidInputException(outMsg);
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
                String outMsg = "Wrong keyword order.\nKeywords in order: ";
                outMsg = outMsg + String.join(" ", argKeywordList) + "\n";
                throw new BasilSeedInvalidInputException(outMsg);
            }
            prevIndex = index;
        }
    }

    private void noArgSupplied(List<String> wordsList, List<String> argKeywordList,
            String argKeyword, String argType, String command) throws BasilSeedInvalidInputException {
        // We are assuming command has already been verified.
        if ((wordsList.indexOf(argKeyword) + 1 == wordsList.size())
                // next line is basically checking if the next arg after the target keyword is another keyword
                || argKeywordList.contains(wordsList.get(wordsList.indexOf(argKeyword) + 1))) {
            String outMsg = String.format("No %s %s detected. Provide one after %s as an argument. \n",
                    command, argType, argKeyword);
            throw new BasilSeedInvalidInputException(outMsg);
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
        // We are assuming command has already been verified.
        int index = -1;
        wrongArgNumCheck(wordsList, 1, command);
        argNotIntegerCheck(command, wordsList);
        index = Integer.parseInt(wordsList.get(1));
        indexOutOfBoundsCheck(index, bounds);
    }

    private String validDateType(String dateString) throws BasilSeedInvalidInputException {
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
                // do nothing first because the other dates could be correct
            }
        }
        String outMsg = "Wrong date format! Use yyyy-mm-dd e.g. 2019-05-10 \n";
        throw new BasilSeedInvalidInputException(outMsg);
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
     * Returns all argument values parsed from the input string.
     *
     * @param inputString User input.
     * @param argKeywords keywords that are used as signposts to parse the relevant arguments
     * @return List of argument values. Excludes the argument keywords and task name.
     */
    public List<String> getAllArgs(String inputString, List<String> argKeywords) {
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
        List<String> wordsList = Arrays.asList(inputString.split("\\s+"));
        if (wordsList.isEmpty()) {
            String outMsg = "Woops, thats not a valid command. Try again! \n";
            throw new BasilSeedInvalidInputException(outMsg);
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
            markNotValidCheck(inputString, firstWord, taskListSize);
            allArgs = getAllArgs(inputString, MarkCommand.KEYWORDS);
            System.out.println("Mark command all args: " + allArgs);
            return new MarkCommand(allArgs);
        case "unmark":
            markNotValidCheck(inputString, firstWord, taskListSize);
            allArgs = getAllArgs(inputString, UnMarkCommand.KEYWORDS);
            return new UnMarkCommand(allArgs);
        case "todo":
            wrongArgNumCheck(wordsList, 1, firstWord);
            allArgs = getAllArgs(inputString, ToDoCommand.KEYWORDS);
            isDone = isMarked(inputString);
            taskName = getArg(wordsList, "", "");
            return new ToDoCommand(allArgs, taskName, isDone);
        case "deadline":
            argKeywordNotFoundCheck(wordsList, DeadlineCommand.KEYWORDS.get(0), firstWord);
            taskNameNotFoundCheck(wordsList, DeadlineCommand.KEYWORDS.get(0), firstWord);
            noArgSupplied(wordsList, DeadlineCommand.KEYWORDS,
                DeadlineCommand.KEYWORDS.get(0), "date", firstWord);
            allArgs = getAllArgs(inputString, DeadlineCommand.KEYWORDS);
            dateType = validDateType(allArgs.get(0));
            isDone = isMarked(inputString);
            taskName = getArg(wordsList, "", DeadlineCommand.KEYWORDS.get(0));
            return new DeadlineCommand(allArgs, taskName, isDone, dateType);
        case "event":
            argKeywordNotFoundCheck(wordsList, EventCommand.KEYWORDS.get(0), firstWord);
            argKeywordNotFoundCheck(wordsList, EventCommand.KEYWORDS.get(1), firstWord);
            taskNameNotFoundCheck(wordsList, EventCommand.KEYWORDS.get(0), firstWord);
            argKeywordOrderWrongCheck(wordsList, EventCommand.KEYWORDS);
            noArgSupplied(wordsList, EventCommand.KEYWORDS, EventCommand.KEYWORDS.get(0), "date", firstWord);
            noArgSupplied(wordsList, EventCommand.KEYWORDS, EventCommand.KEYWORDS.get(1), "date", firstWord);
            allArgs = getAllArgs(inputString, EventCommand.KEYWORDS);
            for (String arg : allArgs) {
                dateType = validDateType(arg);
            }
            isDone = isMarked(inputString);
            taskName = getArg(wordsList, "", EventCommand.KEYWORDS.get(0));
            return new EventCommand(allArgs, taskName, isDone, dateType);
        case "delete":
            wrongArgNumCheck(wordsList, 1, firstWord);
            argNotIntegerCheck(firstWord, wordsList);
            allArgs = getAllArgs(inputString, DeleteCommand.KEYWORDS);
            index = Integer.parseInt(allArgs.get(0));
            indexOutOfBoundsCheck(index, taskListSize);
            return new DeleteCommand(allArgs);
        case "find":
            wrongArgNumCheck(wordsList, 1, firstWord);
            allArgs = getAllArgs(inputString, ToDoCommand.KEYWORDS);
            return new FindCommand(allArgs);
        default:
            if (firstWord.length() >= 3) {
                switch (firstWord.substring(0, 3)) {
                case "[T]":
                    allArgs = getAllArgs(inputString, ToDoCommand.KEYWORDS);
                    isDone = isMarked(inputString);
                    taskName = getArg(wordsList, "", "");
                    return new ToDoCommand(allArgs, taskName, isDone);
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
                    String outMsg = "Woops, thats not a valid command. Try again! \n";
                    throw new BasilSeedInvalidInputException(outMsg);
                }
            }
            String outMsg = "Woops, thats not a valid command. Try again! \n";
            throw new BasilSeedInvalidInputException(outMsg);
        }
    }

}
