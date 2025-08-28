package basilseed;

import basilseed.task.Deadline;
import basilseed.task.Event;
import basilseed.task.Task;
import basilseed.task.ToDo;

import java.util.ArrayList;

public class TaskManager {
    private ArrayList<Task> tasks = new ArrayList<>();

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
        System.out.println("____________________________________________________________");
        for (int i = 0; i < this.tasks.size(); i++) {
            System.out.println(i+1 + "." + this.tasks.get(i));
        }
        System.out.println("____________________________________________________________");
    }

    public boolean indexOutOfBounds(int index){
        return index <= 0 || index > this.tasks.size();
    }

    public void setTaskDone (int index, boolean done){
        this.tasks.get(index).setDone(done);
        updateStorage();
        String outMsg;
        if (done){
            outMsg = "____________________________________________________________\n" +
                    "Nice! I've marked this task as done:\n" +
                    this.tasks.get(index) + "\n" +
                    "____________________________________________________________\n";
        }
        else {
            outMsg = "____________________________________________________________\n" +
                    "OK, I've marked this task as not done yet:\n" +
                    this.tasks.get(index) + "\n" +
                    "____________________________________________________________\n";
        }
        System.out.println(outMsg);
    }

    public void addTask (String command, String taskName, ArrayList<String> args, boolean isDone){
        String outMsg = "";
        Task task;
        String taskArg1;
        String taskArg2;
        switch(command){
            case "todo":
                task = new ToDo(taskName);
                task.setDone(isDone);
                this.tasks.add(task);
                updateStorage();
                outMsg = "____________________________________________________________\n" +
                        "Got it. I've added this task:\n" +
                        task + "\n" +
                        "Now you have " + this.tasks.size() + " tasks in the list.\n" +
                        "____________________________________________________________\n";
                System.out.println(outMsg);
                break;
            case "deadline":
                task = new Deadline(taskName, args.get(0));
                task.setDone(isDone);
                this.tasks.add(task);
                updateStorage();
                outMsg = "____________________________________________________________\n" +
                        "Got it. I've added this task:\n" +
                        task + "\n" +
                        "Now you have " + this.tasks.size() + " tasks in the list.\n" +
                        "____________________________________________________________\n";
                System.out.println(outMsg);
                break;
            case "event":
                task = new Event(taskName, args.get(0), args.get(1));
                task.setDone(isDone);
                this.tasks.add(task);
                updateStorage();
                outMsg = "____________________________________________________________\n" +
                        "Got it. I've added this task:\n" +
                        task + "\n" +
                        "Now you have " + this.tasks.size() + " tasks in the list.\n" +
                        "____________________________________________________________\n";
                System.out.println(outMsg);
                break;
        }
    }

    public void deleteTask (int index){
        Task task = tasks.get(index);
        // .remove came from javadocs and https://www.w3schools.com/java/ref_arraylist_remove.asp
        this.tasks.remove(index);
        updateStorage();
        String outMsg = "____________________________________________________________\n" +
                "Noted. I've removed this task:\n" +
                task + "\n" +
                "Now you have " + this.tasks.size() + " tasks in the list.\n" +
                "____________________________________________________________\n";
        System.out.println(outMsg);
    }

}
