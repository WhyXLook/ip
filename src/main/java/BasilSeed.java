import java.util.Scanner;
import java.util.ArrayList;

public class BasilSeed {
    // String[] userInputs = new String[100];

    // TODO Change String[] to ArrayList<String> and then finally ArrayList<Task>
    //  like this don't need check for null or empty string already. since its dynamic.
    //  also remember to do < ? extends Task > i.e. ArrayList< ? extends Task> in the commandParser parameter


    public static void commandParser (String inputString, ArrayList<Task> inputList){
        /*
        Function to parse user input, checking if its a command keyword. i.e. List
        Modify the passed in arrayList as needed by the command.
        Separated by the space, the first word is the command while the second word is the argument
        */;
        String[] inputWords = inputString.split(" ");
        String command = inputWords[0];
        String outMsg = "";
        int index = -1;
        switch (command){
            case "list":
                System.out.println("____________________________________________________________");
                for (int i = 0; i < inputList.size(); i++) {
                    System.out.println(i+1 + "." + inputList.get(i));
                }
                System.out.println("____________________________________________________________");
                break;
            case "mark":
                index = Integer.parseInt(inputWords[1]) - 1;
                inputList.get(index).setDone(true);
                outMsg = "____________________________________________________________\n" +
                        "Nice! I've marked this task as done:\n" +
                        inputList.get(index) + "\n" +
                        "____________________________________________________________\n";
                System.out.println(outMsg);
                break;
            case "unmark":
                index = Integer.parseInt(inputWords[1]) - 1;
                inputList.get(index).setDone(false);
                outMsg = "____________________________________________________________\n" +
                        "OK, I've marked this task as not done yet:\n" +
                        inputList.get(index) + "\n" +
                        "____________________________________________________________\n";
                System.out.println(outMsg);
                break;
            default:
                if (inputString != null && !inputString.isEmpty()) {
                    inputList.add(new Task(inputString));
                }
                outMsg = "____________________________________________________________\n" +
                        "added: " + inputString + "\n" +
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
        ArrayList<Task> userInputList = new ArrayList<>();
        // Use of scanner to get user input came from https://stackoverflow.com/questions/5287538/how-to-get-the-user-input-in-java
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter a string: ");
        String userInput = reader.nextLine();
        for(;!userInput.equals("bye"); userInput = reader.nextLine()){
            commandParser(userInput, userInputList);
        }
        System.out.println("""
                    ____________________________________________________________
                     Bye. Hope to see you again soon!
                    ____________________________________________________________\
                """);
        reader.close();
    }
}
