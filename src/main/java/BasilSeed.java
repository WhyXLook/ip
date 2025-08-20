import java.util.Scanner;

public class BasilSeed {
    // String[] userInputs = new String[100];


    public static void commandParser (String inputString, String[] inputArray){
        /*
        Function to parse user input, checking if its a command keyword. i.e. List
        Modify the passed in array as needed by the command.
        A null variable marks the end of the array while a empty string marks a deleted one.
        */;
        switch (inputString){
            case "list":
                for (int i = 0; i < inputArray.length; i++) {
                    if (inputArray[i] == null) break;
                    System.out.println(i+1 +". " + inputArray[i]);
                }
                break;
            default:
                for (int i = 0; i < inputArray.length; i++) {
                    if (inputArray[i] == null || inputArray[i].isEmpty()) {
                        inputArray[i] = inputString;
                        break;
                    }
                }
                String outMsg = "____________________________________________________________\n" +
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
        String[] userInputArray = new String[100];
        // Use of scanner to get user input came from https://stackoverflow.com/questions/5287538/how-to-get-the-user-input-in-java
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter a string: ");
        String userInput = reader.nextLine();
        for(;!userInput.equals("bye"); userInput = reader.nextLine()){
            commandParser(userInput, userInputArray);
        }
        System.out.println("""
                    ____________________________________________________________
                     Bye. Hope to see you again soon!
                    ____________________________________________________________\
                """);
        reader.close();
    }
}
