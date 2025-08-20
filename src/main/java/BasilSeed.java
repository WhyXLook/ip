import java.util.Scanner;

public class BasilSeed {
    public static void main(String[] args) {
        String outMsg = "____________________________________________________________\n" +
                " Hello! I'm BasilSeed \n" +
                " What can I do for you?\n" +
                "____________________________________________________________\n";
        System.out.println(outMsg);
        // Use of scanner to get user input came from https://stackoverflow.com/questions/5287538/how-to-get-the-user-input-in-java
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter a string: ");
        String userInput = reader.next();
        for(;!userInput.equals("bye"); userInput = reader.next()){
            System.out.println("User typed: " + userInput);
        }
        System.out.println("""
                    ____________________________________________________________
                     Bye. Hope to see you again soon!
                    ____________________________________________________________\
                """);
        reader.close();
    }
}
