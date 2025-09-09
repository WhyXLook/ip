package basilseed.task;

import basilseed.Storage;
import basilseed.exception.BasilSeedIoException;
import basilseed.ui.UiSuccess;

import java.util.ArrayList;
import java.util.List;

import java.util.regex.Pattern;

/**
 * Manages tasks and provides operations such as add, delete, mark, unmark, and list.
 * Synchronizes tasks with persistent storage and communicates results through UiSuccess.
 */
public class TaskManager {
    private ArrayList<Task> tasks = new ArrayList<>();
    private UiSuccess uiSuccess;
    private Storage storage;

    /**
     * Creates a TaskManager with the specified UiSuccess handler.
     *
     * @param uiSuccess UI handler for displaying task operations.
     */
    public TaskManager(UiSuccess uiSuccess, Storage storage) {
        this.uiSuccess = uiSuccess;
        this.storage = storage;
    }

    private void updateStorage() throws BasilSeedIoException {
        ArrayList<String> outputString = new ArrayList<>();
        for (Task task : tasks) {
            String taskString = task.toString();
            outputString.add(taskString);
        }
        storage.write(outputString);
    }

    private String addTask(Task task, boolean isDone) throws BasilSeedIoException {
        task.setDone(isDone);
        this.tasks.add(task);
        updateStorage();
        return this.uiSuccess.displayTaskAdded(task.toString(), this.tasks.size());
    }

    /**
     * Returns the number of tasks in the task list.
     *
     * @return Number of tasks.
     */
    public int getTaskCount() {
        return tasks.size();
    }

    /**
     * Returns a list of string representations of all tasks.
     *
     * @return List of task descriptions.
     */
    public List<String> getAllTasks() {
        List<String> taskStringList = tasks.stream()
                .map(task -> task.toString())
                .toList();
        return taskStringList;
    }

    /**
     * Displays the provided list of tasks to the user.
     *
     * @param taskStringList List of task descriptions to display.
     * @return a String of all tasks as their string representation
     */
    public String listTasks(List<String> taskStringList) {
        return uiSuccess.displayTaskList(taskStringList);
    }

    /**
     * Sets the completion status of the specified task.
     *
     * @param index 1-based index of the task in the list.
     * @param done  True if the task should be marked done, false otherwise.
     * @return a String stating task has been marked done or not depending on done param
     */
    public String setTaskDone(int index, boolean done) throws BasilSeedIoException {
        this.tasks.get(index - 1).setDone(done);
        updateStorage();
        String outMsg;
        return this.uiSuccess.displayTaskMarked(this.tasks.get(index - 1).toString(), done);
    }

    /**
     * Searches for tasks whose string representation contains the given keyword.
     * Matching is done using regular expression matching.
     *
     * @param keyword Keyword to search for within task descriptions.
     * @return a String of all matched tasks
     */
    public String findTask(String keyword) {
        ArrayList<String> taskStringList = new ArrayList<>(getAllTasks());
        ArrayList<String> foundTaskList = new ArrayList<>();
        for (String taskString : taskStringList) {
            if (taskString.matches(".*" + Pattern.quote(keyword) + ".*")) {
                foundTaskList.add(taskString);
            }
        }
        return listTasks(foundTaskList);
    }

    /**
     * Deletes the task at the specified 1-based index from the task list.
     * Updates persistent storage and displays the deletion to the user.
     *
     * @param index 1-based index of the task to delete.
     * @return a String stating task has been deleted
     * @throws BasilSeedIoException If an I/O error occurs while updating storage.
     */
    public String deleteTask(int index) throws BasilSeedIoException {
        Task task = tasks.get(index - 1);
        this.tasks.remove(index - 1);
        updateStorage();
        return this.uiSuccess.displayTaskDeleted(task.toString(), this.tasks.size());
    }

    /**
     * Adds a new ToDo task to the task list.
     * Updates persistent storage and displays confirmation to the user.
     *
     * @param taskName Name of the ToDo task.
     * @param isDone   Whether the task should be initially marked as done.
     * @return a String stating task has been added
     * @throws BasilSeedIoException If an I/O error occurs while updating storage.
     */
    public String addToDoTask(String taskName, boolean isDone) throws BasilSeedIoException {
        Task task = new ToDo(taskName);
        return addTask(task, isDone);
    }

    /**
     * Adds a new Deadline task to the task list with the given due date.
     * Updates persistent storage and displays confirmation to the user.
     *
     * @param taskName Name of the deadline task.
     * @param isDone   Whether the task should be initially marked as done.
     * @param dueDate  Deadline date string.
     * @param dateType Date format pattern used to parse dueDate
     * @return a String stating task has been added
     * @throws BasilSeedIoException If an I/O error occurs while updating storage.
     */
    public String addDeadlineTask(String taskName, boolean isDone, String dueDate, String dateType)
            throws BasilSeedIoException {
        Task task = new Deadline(taskName, dueDate, dateType);
        return addTask(task, isDone);
    }

    /**
     * Adds a new Event task to the task list with the given start and end dates.
     * Updates persistent storage and displays confirmation to the user.
     *
     * @param eventName Name of the event task.
     * @param isDone    Whether the task should be initially marked as done.
     * @param fromDate  Start date string of the event.
     * @param toDate    End date string of the event.
     * @param dateType  Date format pattern used to parse the date arguments
     * @return a String stating task has been added
     * @throws BasilSeedIoException If an I/O error occurs while updating storage.
     */
    public String addEventTask(String eventName, boolean isDone, String fromDate, String toDate, String dateType)
            throws BasilSeedIoException {
        Task task = new Event(eventName, fromDate, toDate, dateType);
        return addTask(task, isDone);
    }


}
