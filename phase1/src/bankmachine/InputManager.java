package bankmachine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

//TODO: sequence of inputs:
// 1) ask and check validity of username and password (note this should already determine if they are a manager or client)
// 2) ask which account they would like to access
// 3) ask what type of transaction would like to be performed or what they would like to see
// 4) OPTIONAL: confirmation?
// 5) exit option

public class InputManager {
    private boolean exit = false;
    private Authenticator<BankMachineUser> authenticator;
    private Scanner input;
    public static void main(String[] args) {

        new InputManager().mainLoop();
    }

    public InputManager(){
        input = new Scanner(System.in);
    }

    // Prompts for input
    private String getInput(String s) {
        System.out.print(s);
        return input.next();
    }

    private String getInput(Object[] objects){
        printObjects(objects);
        return input.next();
    }
    private void printObjects(Object[] objects){
        for (Object o:objects){
            System.out.print(o);
        }
    }

    private <T> T selectItem(List<T> items){
        if(items.size() == 0){ return null; }
        else if(items.size() == 1){ return items.get(0); }

        for(int i=0; i<items.size(); i++){
            printObjects(new Object[]{"[", i, "] "});
            System.out.println(items.get(i).toString());
        }

        int index;
        while(true){
            String number = getInput(new Object[]{
                    "Enter a number from 0 to ", items.size()-1, ": "
            });
            try{
                index = Integer.parseInt(number);
            } catch(NumberFormatException e){
                continue;
            }
            if (0<=index && index < items.size()){
                break;
            }
        }
        return items.get(index);
    }

    //Choosing an account
    private void selectItemTest(){
        ArrayList<String> strings = new ArrayList<>(Arrays.asList(
                "Credit Card Account", "Line Credit Account", "Asset Account", "Savings Account"
        ));
        System.out.println("Choose an account");
        String response = selectItem(strings);
        System.out.println("You chose " + response);
        // TODO: link to person and see what accounts they have... if no accounts open one?
    }

    private void selectActionTest(){
        ArrayList<String> strings = new ArrayList<>(Arrays.asList(
                "Withdraw", "Pay bills", "Deposit", "Transfer", "Undo Transaction"
        ));
        System.out.println("Choose a type of transaction");
        String response = selectItem(strings);
        System.out.println("You chose " + response);
        // TODO: link to the account and see what transactions the account can do
        //  (NOTE: array of choices of transaction depends on the account)
        // TODO: make more individual functions (cases) for each class? call more functions?
    }

    public void mainLoop(){
        // Login page
        // TODO: Change the conditions in the while loops so that it tries to find the list of usernames and
        //  corresponding password
        while(!exit) {
            String a = "John";
            String b = "123";
            System.out.println("Type 'exit' to quit");
            String username = getInput("Enter username: ");
            if(username.equalsIgnoreCase("exit")){
                break;
            }
            String password = getInput("Enter password: ");
            // TODO: determine what file is being looked at? Or is it the array with usernames?
            if (authenticator.authenticate(username, password).isPresent()){
            //if (username.equals(a) && password.equals(b)){
                System.out.println("Welcome!");
                selectItemTest();
                selectActionTest();
                // TODO: when finished, add a "do you wish to continue?" option
                System.out.println("Goodbye");
                exit = true;
            } else {
                System.out.println("Incorrect username/password");
            }
        }
    }


}
