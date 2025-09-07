package basilseed.task;

import basilseed.Storage;
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

    /**
     * Creates a TaskManager with the specified UiSuccess handler.
     *
     * @param uiSuccess UI handler for displaying task operations.
     */
    public TaskManager(UiSuccess uiSuccess) {
        this.uiSuccess = uiSuccess;
    }

    private void updateStorage() {
        ArrayList<String> outputString = new ArrayList<>();
        for (Task task : tasks) {
            String taskString = task.toString();
            outputString.add(taskString);
        }
        Storage storage = new Storage();
        storage.write(outputString);
    }

    private void addTask(Task task, boolean isDone) {
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

    public List<String> getAllTasks() {
        List<String> taskStringList = tasks.stream()
                .map(task -> task.toString())
                .toList();
        return taskStringList;
    }

    public void listTasks(List<String> taskStringList){
        uiSuccess.displayTaskList(taskStringList);
    }

    public void setTaskDone(int index, boolean done) {
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

    public void deleteTask (int index) {
        Task task = tasks.get(index - 1);
        this.tasks.remove(index - 1);
        updateStorage();
        this.uiSuccess.displayTaskDeleted(task.toString(), this.tasks.size());
    }

    public void addToDoTask(String taskName, boolean isDone) {
        Task task = new ToDo(taskName);
        addTask(task, isDone);
    }

    public void addDeadlineTask(String taskName, boolean isDone, String dueDate, String dateType) {
        Task task = new Deadline(taskName, dueDate, dateType);
        addTask(task, isDone);
    }

    public void addEventTask(String eventName, boolean isDone, String fromDate, String toDate, String dateType) {
        Task task = new Event(eventName, fromDate, toDate, dateType);
        addTask(task, isDone);
    }


}
