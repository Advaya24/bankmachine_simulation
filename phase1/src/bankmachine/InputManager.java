package bankmachine;

import bankmachine.account.Account;
import bankmachine.exception.ShutdownException;
import com.sun.istack.internal.Nullable;

import java.util.*;
import java.util.regex.Pattern;

public class InputManager {

    private boolean exit = false;
    private boolean userIsClient = true;
    private UserManager authenticator = BankMachine.USER_MANAGER;
    private Pattern emailRe = Pattern.compile(
        "^[a-zA-Z0-9.\\-_]+@[a-zA-Z0-9.\\-_]+\\.[a-zA-Z]{2,3}$"
    );
    private Pattern phoneRe = Pattern.compile(
        "^\\(?\\d{3}\\)?-\\d{3}-\\d{4}$"
    );
    private Pattern doubleRe = Pattern.compile(
        "^\\d+\\.\\d{2}$"
    );

    private String getValue(Pattern p, String name){
        while (true) {
            String email = getInput("Please enter an "+name+": ");
            if(p.matcher(email).matches()){
                return email;
            }
            System.out.println("Invalid "+name);
        }
    }
    public String getEmail() {
        return getValue(emailRe, "email");
    }
    public String getPhone(){
        return getValue(phoneRe, "phone number");
    }
    public double getMoney(){
        while(true) {
            String s = getValue(doubleRe, "amount");
            try{
                return Double.parseDouble(s);
            } catch (NumberFormatException e){
                System.out.println("Invalid amount");
            }
        }
    }
    public int getInteger(String prompt){
        while(true) {
            int i;
            try{
                i = Integer.parseInt(getInput(prompt));
            } catch (NumberFormatException e){
                System.out.println("Invalid integer");
                continue;
            }
            if (i<0) { System.out.println("Positive numbers only please"); }
            else { return i; }
        }
    }

    // You should make DATA_PATH a private variable
    private String fileManagerPath;
    private Scanner input;

    public static void main(String[] args) {
        new InputManager().mainLoop();
    }

    public InputManager(){
        input = new Scanner(System.in);
    }

    // Prompts for input
    public String getInput(String s) {
        System.out.print(s);
        return input.nextLine();
    }

    private String getInput(Object[] objects){
        printObjects(objects);
        return input.nextLine();
    }
    private void printObjects(Object[] objects){
        for (Object o:objects){
            System.out.print(o);
        }
    }

    @Nullable
    <T> T selectItem(List<T> items){
        if(items.size() == 0){ return null; }

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

    public void mainLoop(){
        // Login page
        while(!exit) {
            // userIsClient = isUserClient();
            BankMachineUser user = logIn();
            try {
                user.handleInput(this);
            } catch (ShutdownException e){
                exit = true;
                return;
            }
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
        while (true) {
            String username = getInput("Enter username: ");
            String password = getInput("Enter password: ");
            BankMachineUser user = authenticator.authenticate(username, password);
            if (user == null){
                System.out.println("Incorrect username/password");
            } else {
                return user;
            }
        }
    }

    private void bankManagerTasks(BankManager bankManager){
        ArrayList<String> tasks = new ArrayList<>(Arrays.asList(
                "Change client information", "Create new user", "Restock machine"
        )); //Change client info includes undoing most recent transaction, and anything that a user can do
        String t = itemizeChoice(tasks);
    }

    private void clientInfo(Client c, Account a){
        System.out.println("\n Client information:"
                + "\n Name:" + c.getName()
                + "\n Account balance:" + a.getBalance());
    }

    private void PerformTransaction(Account a){
        // call methods from the specific account
    }

    private void chooseToExit(boolean b){
        System.out.println("Would you like to continue to next transaction or exit?");
        ArrayList<String> choices = new ArrayList<>(Arrays.asList(
                "Next transaction", "Exit"
        ));
        String choice = itemizeChoice(choices);
        if (choice.equals("Exit")) {
            exit = true;
        }
    }

}
