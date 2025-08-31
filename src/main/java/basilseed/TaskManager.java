package basilseed;

import basilseed.task.Deadline;
import basilseed.task.Event;
import basilseed.task.Task;
import basilseed.task.ToDo;
import basilseed.ui.UiSuccess;

import java.util.ArrayList;
import java.util.List;

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

    public void listTasks(){
        List<String> taskStringList = tasks.stream()
            .map(task -> task.toString())
            .toList();
        uiSuccess.displayTaskList(taskStringList);
    }

    public boolean indexOutOfBounds(int index){
        return index <= 0 || index > this.tasks.size();
    }

    public void setTaskDone (int index, boolean done){
        this.tasks.get(index).setDone(done);
        updateStorage();
        String outMsg;
        this.uiSuccess.displayTaskMarked(this.tasks.get(index).toString(), done);
    }

    public void addTask (String command, String taskName, ArrayList<String> args, boolean isDone,
                         String dateType){
        Task task;
        switch(command){
        case "todo":
            task = new ToDo(taskName);
            break;
        case "deadline":
            task = new Deadline(taskName, args.get(0), dateType);
            break;
        case "event":
            task = new Event(taskName, args.get(0), args.get(1), dateType);
            break;
        default:
            // will not happen.
            throw new IllegalArgumentException("Unknown command");
        }
        task.setDone(isDone);
        this.tasks.add(task);
        updateStorage();
        this.uiSuccess.displayTaskAdded(task.toString(), this.tasks.size());
    }

    public void deleteTask (int index){
        Task task = tasks.get(index);
        // .remove came from javadocs and https://www.w3schools.com/java/ref_arraylist_remove.asp
        this.tasks.remove(index);
        updateStorage();
        this.uiSuccess.displayTaskDeleted(task.toString(), this.tasks.size());
    }

}
