import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;

public class BasilSeed {
    public static void addTask (String taskType, List<String> args, ArrayList<Task> taskArrayList){
        String outMsg = "";
        Task task;
        switch(taskType){
            case "todo":
                task = new ToDo(
                    args.stream()
                        .reduce((x,y) -> x + " " + y)
                        .orElse("")
                );
                taskArrayList.add(task);
                outMsg = "____________________________________________________________\n" +
                        "Got it. I've added this task:\n" +
                        task + "\n" +
                        "Now you have " + taskArrayList.size() + " tasks in the list.\n" +
                        "____________________________________________________________\n";
                System.out.println(outMsg);
        }
    }

    public static void commandParser (String inputString, ArrayList<Task> inputArrayList){
        /*
        Function to parse user input, checking if its a command keyword. i.e. List
        Modify the passed in arrayList as needed by the command.
        Separated by the space, the first word is the command while the second word is the argument
        */;
        // TODO make it so that double space can also be divided with regex
        // conversion from string arrays to List string https://stackoverflow.com/questions/2607289/converting-array-to-list-in-java
        List<String> wordsList = Arrays.asList(inputString.split(" "));
        String command = wordsList.get(0);
        String outMsg = "";
        int index = -1;
        switch (command){
            case "list":
                System.out.println("____________________________________________________________");
                for (int i = 0; i < inputArrayList.size(); i++) {
                    System.out.println(i+1 + "." + inputArrayList.get(i));
                }
                System.out.println("____________________________________________________________");
                break;
            case "mark":
                // TODO check if second arg exist and is more than 0
                index = Integer.parseInt(wordsList.get(1)) - 1;
                inputArrayList.get(index).setDone(true);
                outMsg = "____________________________________________________________\n" +
                        "Nice! I've marked this task as done:\n" +
                        inputArrayList.get(index) + "\n" +
                        "____________________________________________________________\n";
                System.out.println(outMsg);
                break;
            case "unmark":
                // TODO check if second arg exist and is more than 0
                index = Integer.parseInt(wordsList.get(1)) - 1;
                inputArrayList.get(index).setDone(false);
                outMsg = "____________________________________________________________\n" +
                        "OK, I've marked this task as not done yet:\n" +
                        inputArrayList.get(index) + "\n" +
                        "____________________________________________________________\n";
                System.out.println(outMsg);
                break;
            // witches if you don't break it will run the last peice of code. So can abuse that
            case "todo":
            case "deadline":
            case "event":
                // use of sublist came from 2030, but also available on java docs anyway
                addTask(command, wordsList.subList(1, wordsList.size()), inputArrayList);
                break;
            default:
                outMsg = "____________________________________________________________\n" +
                        "Woops, thats not a valid command. Try again! \n" +
                        "____________________________________________________________\n";
                System.out.println(outMsg);

        }
    }


    public static void main(String[] args) {
        String outMsg = "____________________________________________________________\n" +
                " Hello! I'm BasilSeed \n" +
                " What can I do for you?\n" +
                "____________________________________________________________\n";
        System.out.println(outMsg);
        // use of arraylist was from 2030
        ArrayList<Task> userinputArrayList = new ArrayList<>();
        // Use of scanner to get user input came from https://stackoverflow.com/questions/5287538/how-to-get-the-user-input-in-java
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter a string: ");
        String userInput = reader.nextLine();
        for(;!userInput.equals("bye"); userInput = reader.nextLine()){
            commandParser(userInput, userinputArrayList);
        }
        System.out.println("""
                    ____________________________________________________________
                     Bye. Hope to see you again soon!
                    ____________________________________________________________\
                """);
        reader.close();
    }
}
