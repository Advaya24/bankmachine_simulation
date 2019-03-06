package bankmachine;

import bankmachine.FileManager.FileSearcher;
import bankmachine.account.Account;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.util.*;

public class InputManager {

    // TODO: use static user managers
    private boolean exit = false;
    private boolean userIsClient = true;
    private UserManager userManager = new UserManager(BankMachineUser.users);

    //    final private UserManager<Client> clientManager = BankMachine.getClientManager();
//    final private UserManager<BankManager> bankManagerUserManager = BankMachine.getBankManagerUserManager();


    // You should make fileManagerPath a private variable
    private String fileManagerPath;
    private Scanner input;

    public static void main(String[] args) {

        // Initializes fileManagerLocation correctly
        final FileSearcher fileSearcher = new FileSearcher();
        fileSearcher.setFileNameToSearch("FileManager");
        fileSearcher.searchForDirectory(new File(System.getProperty("user.dir")));
        final String fileManagerPath = fileSearcher.getResult().get(0);


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
            printObjects(new Object[]{"[", i+1, "] "});
            System.out.println(items.get(i).toString());
        }

        int index;
        while(true){
            String number = getInput(new Object[]{
                    "Enter a number from 1 to ", items.size(), ": "
            });
            try{
                index = Integer.parseInt(number);
            } catch(NumberFormatException e){
                continue;
            }
            if (0 < index && index <= items.size()){
                break;
            }
        }
        return items.get(index-1);
    }

    /*//Choosing an account
    private void selectItemTest(){
        ArrayList<String> strings = new ArrayList<>(Arrays.asList(
                "Credit Card Account", "Line of Credit Account", "Asset Account", "Savings Account"
        ));
        System.out.println("Choose an account");
        String response = selectItem(strings);
        System.out.println("You chose " + response);
    }

    private void selectActionTest(){
        ArrayList<String> strings = new ArrayList<>(Arrays.asList(
                "Withdraw", "Pay bills", "Deposit", "Transfer", "Undo Transaction"
        ));
        System.out.println("Choose a type of transaction");
        String response = selectItem(strings);
        System.out.println("You chose " + response);
    }*/

    public void mainLoop(){
        // Login page
        while(!exit) {
            // userIsClient = isUserClient();
            BankMachineUser user = logIn();
            // TODO: choose and account
            // TODO: choose a transaction
            // TODO: note that the bank manager has the option of adding people and doing other things normal users cant...
            // TODO: continue/exit

           /* if (username.equals(a) && password.equals(b)){
                System.out.println("Welcome!");
                selectItemTest();
                selectActionTest();
                System.out.println("Goodbye");
                exit = true;
            } else {
                System.out.println("Incorrect username/password");
            }*/
        }
    }
    // Display when there are multiple choices
    private String itemizeChoice(ArrayList<String> strings) {
        System.out.println("Please choose an option");
        String response = selectItem(strings);
        System.out.println("You chose " + response);
        return response;
    }

    // 1) Ask whether a bank manager or client
    private boolean isUserClient() {
        ArrayList<String> users = new ArrayList<>(Arrays.asList(
                "Bank Manager", "Client"
        ));
        String u = itemizeChoice(users);
        if (u.equals("Bank Manager")) {
            userIsClient = false;
        }
        return userIsClient;
    }
    // 2) Verify if username and password exist and returns the accounts of the user.
    private BankMachineUser logIn() {
        while (!exit) {
            String username = getInput("Enter username: ");
            String password = getInput("Enter password: ");
            BankMachineUser user = userManager.authenticate(username, password);
            if (user == null){
                System.out.println("Incorrect username/password");
            } else {
                return user;
            }
        }

        // 2) ask which account they would like to access
        // 3) ask what type of transaction would like to be performed or what they would like to see
        // 4) OPTIONAL: confirmation?
        // 5) exit option

        return null;
    }
}
