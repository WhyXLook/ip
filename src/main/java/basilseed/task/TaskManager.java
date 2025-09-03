package basilseed.task;

import basilseed.Storage;
import basilseed.ui.UiSuccess;

import java.util.ArrayList;
import java.util.List;

import java.util.regex.Pattern;

public class TaskManager {
    private ArrayList<Task> tasks = new ArrayList<>();
    private UiSuccess uiSuccess;

    public TaskManager(UiSuccess uiSuccess) {
        this.uiSuccess = uiSuccess;
    }

    private void updateStorage(){
        ArrayList<String> outputString = new ArrayList<>();
        for (Task task : tasks){
            String taskString = task.toString();
            outputString.add(taskString);
        }
        Storage storage = new Storage();
        storage.write(outputString);
    }

    private List<String> getAllTasks(){
        List<String> taskStringList = tasks.stream()
                .map(task -> task.toString())
                .toList();
        return taskStringList;
    }

    private void listTasks(List<String> taskStringList){
        uiSuccess.displayTaskList(taskStringList);
    }

    public int getTaskCount(){
        return tasks.size();
    }

    private void setTaskDone (int index, boolean done){
        this.tasks.get(index).setDone(done);
        updateStorage();
        String outMsg;
        this.uiSuccess.displayTaskMarked(this.tasks.get(index).toString(), done);
    }

    private void addTask (Task task, boolean isDone){
        task.setDone(isDone);
        this.tasks.add(task);
        updateStorage();
        this.uiSuccess.displayTaskAdded(task.toString(), this.tasks.size());
    }

    private void deleteTask (int index){
        Task task = tasks.get(index);
        this.tasks.remove(index);
        updateStorage();
        this.uiSuccess.displayTaskDeleted(task.toString(), this.tasks.size());
    }

    private List<String> findTask (String keyword) {
        ArrayList<String> taskStringList = new ArrayList<>(getAllTasks());
        ArrayList<String> foundTaskList = new ArrayList<>();
        for (String taskString : taskStringList) {
            if (taskString.matches(".*" + Pattern.quote(keyword) + ".*")) {
                foundTaskList.add(taskString);
            }
        }
        return foundTaskList;
    }

    public void processCommand (String command, String taskName, List<String> args, boolean isDone,
                         String dateType){
        Task task;
        switch(command){
        case "list":
            listTasks(getAllTasks());
            break;
        case "mark":
            setTaskDone(Integer.parseInt(args.get(0)) - 1, true);
            break;
        case "unmark":
            setTaskDone(Integer.parseInt(args.get(0)) - 1, false);
            break;
        case "todo":
            task = new ToDo(taskName);
            addTask(task, isDone);
            break;
        case "deadline":
            task = new Deadline(taskName, args.get(0), dateType);
            addTask(task, isDone);
            break;
        case "event":
            task = new Event(taskName, args.get(0), args.get(1), dateType);
            addTask(task, isDone);
            break;
        case "delete":
            deleteTask(Integer.parseInt(args.get(0)) - 1);
            break;
        case "find":
            listTasks(findTask(taskName));
            break;
        default:
            break;
        }
    }


}
