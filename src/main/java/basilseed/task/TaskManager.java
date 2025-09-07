package basilseed.task;

import basilseed.Storage;
import basilseed.exception.BasilSeedException;
import basilseed.exception.BasilSeedIOException;
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

    private void updateStorage() throws BasilSeedIOException {
        ArrayList<String> outputString = new ArrayList<>();
        for (Task task : tasks) {
            String taskString = task.toString();
            outputString.add(taskString);
        }
        storage.write(outputString);
    }

    private void addTask(Task task, boolean isDone) throws BasilSeedIOException {
        task.setDone(isDone);
        this.tasks.add(task);
        updateStorage();
        this.uiSuccess.displayTaskAdded(task.toString(), this.tasks.size());
    }

    /**
     * Returns the number of tasks in the task list.
     *
     * @return Number of tasks.
     */
    public int getTaskCount(){
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
     */
    public void listTasks(List<String> taskStringList){
        uiSuccess.displayTaskList(taskStringList);
    }

    /**
     * Sets the completion status of the specified task.
     *
     * @param index 1-based index of the task in the list.
     * @param done True if the task should be marked done, false otherwise.
     */
    public void setTaskDone(int index, boolean done) throws BasilSeedIOException {
        this.tasks.get(index - 1).setDone(done);
        updateStorage();
        String outMsg;
        this.uiSuccess.displayTaskMarked(this.tasks.get(index - 1).toString(), done);
    }

    public void findTask (String keyword) {
        ArrayList<String> taskStringList = new ArrayList<>(getAllTasks());
        ArrayList<String> foundTaskList = new ArrayList<>();
        for (String taskString : taskStringList) {
            if (taskString.matches(".*" + Pattern.quote(keyword) + ".*")) {
                foundTaskList.add(taskString);
            }
        }
        listTasks(foundTaskList);
    }

    public void deleteTask (int index) throws BasilSeedIOException {
        Task task = tasks.get(index - 1);
        this.tasks.remove(index - 1);
        updateStorage();
        this.uiSuccess.displayTaskDeleted(task.toString(), this.tasks.size());
    }

    public void addToDoTask(String taskName, boolean isDone) throws BasilSeedIOException {
        Task task = new ToDo(taskName);
        addTask(task, isDone);
    }

    public void addDeadlineTask(String taskName, boolean isDone, String dueDate, String dateType)
            throws BasilSeedIOException {
        Task task = new Deadline(taskName, dueDate, dateType);
        addTask(task, isDone);
    }

    public void addEventTask(String eventName, boolean isDone, String fromDate, String toDate, String dateType)
            throws BasilSeedIOException {
        Task task = new Event(eventName, fromDate, toDate, dateType);
        addTask(task, isDone);
    }


}
