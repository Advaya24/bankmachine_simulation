package bankmachine;

import bankmachine.FileManager.FileSearcher;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.util.*;

public class InputManager {
    private boolean exit = false;
    private boolean userIsClient = true;
    private UserManager<BankMachineUser> userManager;

//  TODO: Read this
//  Initialize an userManager each for Clients and BankManagers separately:
    private static UserManager<Client> clientAuthenticator;
    private static UserManager<BankManager> bankManagerAuthenticator;

    // You should make fileManagerPath a private variable
    private String fileManagerPath;
    private Scanner input;

    public static void main(String[] args) {

        // TODO: Read this
        // Initializes fileManagerLocation correctly
        final FileSearcher fileSearcher = new FileSearcher();
        fileSearcher.setFileNameToSearch("FileManager");
        fileSearcher.searchForDirectory(new File(System.getProperty("user.dir")));
        final String fileManagerPath = fileSearcher.getResult().get(0);

        // Initialize the authenticators correctly
        clientAuthenticator = new UserManager<>(fileManagerPath + "/clientData.ser");
        bankManagerAuthenticator = new UserManager<>(fileManagerPath + "/bankManagerData.ser");

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

    //Choosing an account
    private void selectItemTest(){
        ArrayList<String> strings = new ArrayList<>(Arrays.asList(
                "Credit Card Account", "Line of Credit Account", "Asset Account", "Savings Account"
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
            /*String a = "John";
            String b = "123";*/
            System.out.println("Type 'exit' to quit");
            isUserClient();
            /*String username = getInput("Enter username: ");
            if(username.equalsIgnoreCase("exit")){
                break;
            }
            String password = getInput("Enter password: ");

            // TODO: Read this
            // Optional<Client> optionalClient = clientAuthenticator.authenticate(username, password);
            // if (optionalClient.isPresent()) {
            //     Client retrivedClient = optionalClient.get();
            //     // Do something with retrievedClient here
            // }
            if (username.equals(a) && password.equals(b)){
                System.out.println("Welcome!");
                selectItemTest();
                selectActionTest();
                // TODO: when finished, add a "do you wish to continue?" option
                System.out.println("Goodbye");
                exit = true;
            } else {
                System.out.println("Incorrect username/password");
            }*/
        }
    }

    // TODO: sequence of inputs:
    // 1) Ask whether a bank manager or client
    private String itemizeChoice(ArrayList<String> strings) {
        System.out.println("Please choose an option");
        String response = selectItem(strings);
        System.out.println("You chose " + response);
        return response;
    }

        // TODO: link to person and see what accounts they have... if no accounts open one?
    private void isUserClient() {
        ArrayList<String> users = new ArrayList<>(Arrays.asList(
                "Bank Manager", "Client"
        ));
        String u = itemizeChoice(users);
        if (u.equals("Bank Manager")) {
            userIsClient = false;
        }
        logIn(userIsClient);
    }

    private void logIn(boolean user) {
        while (!exit) {
            String username = getInput("Enter username: ");
            String password = getInput("Enter password: ");
            if (user) {
                Optional<Client> optionalClient = clientAuthenticator.authenticate(username, password);
                if (optionalClient.isPresent()) {
                    System.out.println("Welcome!");
                    Client retrievedClient = optionalClient.get();
                    //return retrievedClient.getClientsAccounts();
                } else {
                    System.out.println("Incorrect username/password");
                }
            } else {
                Optional<BankManager> optionalBankManager = bankManagerAuthenticator.authenticate(username, password);
                if (optionalBankManager.isPresent()) {
                    System.out.println("Welcome!");
                    BankManager retrievedBankManager = optionalBankManager.get();
                    //TODO: displays a list of names
                }
            }
            if (username.equalsIgnoreCase("exit")) {
                exit = true;
            }
        }

        // 2) ask which account they would like to access
        // 3) ask what type of transaction would like to be performed or what they would like to see
        // 4) OPTIONAL: confirmation?
        // 5) exit option

    }
}
