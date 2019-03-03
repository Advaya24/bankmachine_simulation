package bankmachine;
import java.util.Scanner;

public class InputManager {

    public static void main(String[] args) {
        // Login page
        // TODO: Change the conditions in the while loops so that it tries to find the list of usernames and
        //  corresponding password
        String a = "John";
        String b = "123abc";
        String username = userNamePrompt();
        while (!username.equals(a)) {
            username = userNamePrompt();
        }
        String password = passwordPrompt();
        while (!password.equals(b)) {
            password = passwordPrompt();
        }

        System.out.println("Welcome!");


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


}
