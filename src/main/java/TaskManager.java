import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class TaskManager {
    private ArrayList<Task> tasks = new ArrayList<>();

    public void listTasks(){
        System.out.println("____________________________________________________________");
        for (int i = 0; i < this.tasks.size(); i++) {
            System.out.println(i+1 + "." + this.tasks.get(i));
        }
        System.out.println("____________________________________________________________");
    }

    public void setTaskDone (int index, boolean done){
        this.tasks.get(index).setDone(done);
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

    public void addTask (String taskType, List<String> args){
        String outMsg = "";
        Task task;
        String taskArg1;
        String taskArg2;
        String taskName;
        switch(taskType){
            case "todo":
                // use of streams came from 2030
                taskName = args.stream()
                        .reduce((x,y) -> x + " " + y)
                        .orElse("");
                task = new ToDo(taskName);
                this.tasks.add(task);
                outMsg = "____________________________________________________________\n" +
                        "Got it. I've added this task:\n" +
                        task + "\n" +
                        "Now you have " + this.tasks.size() + " tasks in the list.\n" +
                        "____________________________________________________________\n";
                System.out.println(outMsg);
                break;
            case "deadline":
                // discovery of indexof function came from https://www.baeldung.com/java-array-find-index
                taskName = args.subList(0, args.indexOf("/by"))
                        .stream()
                        .reduce((x,y) -> x + " " + y)
                        .orElse("");
                taskArg1 = args.subList(args.indexOf("/by") + 1, args.size())
                        .stream()
                        .reduce((x,y) -> x + " " + y)
                        .orElse("");
                task = new Deadline(taskName, taskArg1);
                this.tasks.add(task);
                outMsg = "____________________________________________________________\n" +
                        "Got it. I've added this task:\n" +
                        task + "\n" +
                        "Now you have " + this.tasks.size() + " tasks in the list.\n" +
                        "____________________________________________________________\n";
                System.out.println(outMsg);
                break;
            case "event":
                // discovery of indexof function came from https://www.baeldung.com/java-array-find-index
                taskName = args.subList(0, args.indexOf("/from"))
                        .stream()
                        .reduce((x,y) -> x + " " + y)
                        .orElse("");
                taskArg1 = args.subList(args.indexOf("/from") + 1, args.indexOf("/to"))
                        .stream()
                        .reduce((x,y) -> x + " " + y)
                        .orElse("");
                taskArg2 = args.subList(args.indexOf("/to") + 1, args.size())
                        .stream()
                        .reduce((x,y) -> x + " " + y)
                        .orElse("");
                task = new Event(taskName, taskArg1, taskArg2);
                this.tasks.add(task);
                outMsg = "____________________________________________________________\n" +
                        "Got it. I've added this task:\n" +
                        task + "\n" +
                        "Now you have " + this.tasks.size() + " tasks in the list.\n" +
                        "____________________________________________________________\n";
                System.out.println(outMsg);
                break;
        }
    }

}
