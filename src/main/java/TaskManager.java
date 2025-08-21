import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class TaskManager {
    private ArrayList<Task> tasks = new ArrayList<>();

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
                taskArg1 = args.subList(args.indexOf("/by"), args.size())
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
        }
    }

    public void commandParser (String inputString){
        /*
        Function to parse user input, checking if its a command keyword. i.e. List
        Modify the passed in arrayList as needed by the command.
        Separated by the space, the first word is the command while the second word is the argument
        */
        // TODO make it so that double space can also be divided with regex
        // conversion from string arrays to List string https://stackoverflow.com/questions/2607289/converting-array-to-list-in-java
        List<String> wordsList = Arrays.asList(inputString.split(" "));
        String command = wordsList.get(0);
        String outMsg = "";
        int index = -1;
        switch (command){
            case "list":
                System.out.println("____________________________________________________________");
                for (int i = 0; i < this.tasks.size(); i++) {
                    System.out.println(i+1 + "." + this.tasks.get(i));
                }
                System.out.println("____________________________________________________________");
                break;
            case "mark":
                // TODO check if second arg exist and is more than 0
                index = Integer.parseInt(wordsList.get(1)) - 1;
                this.tasks.get(index).setDone(true);
                outMsg = "____________________________________________________________\n" +
                        "Nice! I've marked this task as done:\n" +
                        this.tasks.get(index) + "\n" +
                        "____________________________________________________________\n";
                System.out.println(outMsg);
                break;
            case "unmark":
                // TODO check if second arg exist and is more than 0
                index = Integer.parseInt(wordsList.get(1)) - 1;
                this.tasks.get(index).setDone(false);
                outMsg = "____________________________________________________________\n" +
                        "OK, I've marked this task as not done yet:\n" +
                        this.tasks.get(index) + "\n" +
                        "____________________________________________________________\n";
                System.out.println(outMsg);
                break;
            // witches if you don't break it will run the last peice of code. So can abuse that
            case "todo":
            case "deadline":
            case "event":
                // use of sublist came from 2030, but also available on java docs anyway
                this.addTask(command, wordsList.subList(1, wordsList.size()));
                break;
            default:
                outMsg = "____________________________________________________________\n" +
                        "Woops, thats not a valid command. Try again! \n" +
                        "____________________________________________________________\n";
                System.out.println(outMsg);

        }
    }
}
