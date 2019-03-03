package bankmachine;
import java.util.Scanner;

public class InputManager {

    public static void main(String[] args) {
        mainLoop();


    }
    // Creates prompt for username
    private static String userNamePrompt() {
        Scanner input = new Scanner(System.in);
        String username;
        System.out.println("Enter username: ");
        username = input.next();
        return username;
    }
    //Creates prompt for password
    private static String passwordPrompt() {
        Scanner input = new Scanner(System.in);
        String password;
        System.out.println("Enter password: ");
        password = input.next();
        return password;
    }
    public static void mainLoop(){
        // Login page
        // TODO: Change the conditions in the while loops so that it tries to find the list of usernames and
        //  corresponding password
        while(true) {
            String a = "John";
            String b = "123abc";
            String username = userNamePrompt();
            if(username.equalsIgnoreCase("exit")){
                break;
            }
            String password = passwordPrompt();
            if (username.equals(a) && password.equals(b)){
                System.out.println("Welcome!");
            } else {
                System.out.println("Incorrect username/password");
            }


        }
    }


}
