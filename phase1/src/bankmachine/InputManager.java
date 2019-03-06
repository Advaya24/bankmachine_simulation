package bankmachine;

import bankmachine.account.Account;

import java.util.*;

public class InputManager {

    // TODO: use static user managers
    private boolean exit = false;
    private boolean userIsClient = true;
    //private UserManager<BankMachineUser> userManager;

    // Initialize an userManager each for Clients and BankManagers separately:
    //private static UserManager<Client> clientAuthenticator;
    //private static UserManager<BankManager> bankManagerAuthenticator;

    //    final private UserManager<Client> clientManager = BankMachine.getClientManager();
//    final private UserManager<BankManager> bankManagerUserManager = BankMachine.getBankManagerUserManager();


    // You should make DATA_PATH a private variable
    private String fileManagerPath;
    private Scanner input;

    public static void main(String[] args) {

        // Initializes fileManagerLocation correctly
        /*final FileSearcher fileSearcher = new FileSearcher();
        fileSearcher.setFileNameToSearch("FileManager");
        fileSearcher.searchForDirectory(new File(System.getProperty("user.dir")));
        final String DATA_PATH = fileSearcher.getResult().get(0);*/

        // Initialize the authenticators correctly
        //clientAuthenticator = new UserManager<>(DATA_PATH + "/clientData.ser");
        //bankManagerAuthenticator = new UserManager<>(DATA_PATH + "/bankManagerData.ser");

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

    public void mainLoop(){
        // Login page
        while(!exit) {
            userIsClient = isUserClient();
            if (userIsClient) {
                Client client = logInClient(); // verifies login
                while (!exit) {
                    Account a = ChooseAccount(client); //gets an account that the client wants to do things to
                    PerformTransaction(a); // takes an account and performs transactions
                    chooseToExit(exit); //
                }
            } else {
                BankManager bankManager = logInBankManager(); // verifies login
                bankManagerTasks(bankManager);
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
    private Client logInClient() {
        boolean moveOn = true;
        while (moveOn) {
            //System.out.println("Type 'exit' to exit");
            String username = getInput("Enter username: ");
            /*if (username.equalsIgnoreCase("exit")) {
            exit = true;
            }*/
            String password = getInput("Enter password: ");
            Optional<Client> optionalClient = BankMachine.clientManager.authenticate(username, password);
            if (optionalClient.isPresent()) {
                System.out.println("Welcome!");
                Client retrievedClient = optionalClient.get();
                System.out.println(retrievedClient);
                moveOn = false;
                return retrievedClient;
            } else {
                System.out.println("Incorrect username/password");
            }

        }
        return null;
    }

    private BankManager logInBankManager() {
        boolean moveOn = true;
        while (moveOn) {
            System.out.println("Type 'exit' to exit");
            String username = getInput("Enter username: ");
            if (username.equalsIgnoreCase("exit")) {
                exit = true;
            }
            String password = getInput("Enter password: ");
            Optional<BankManager> optionalBankManager = BankMachine.bankManagerUserManager.authenticate(username, password);
            if (optionalBankManager.isPresent()) {
                System.out.println("Welcome!");
                BankManager retrievedBankManager = optionalBankManager.get();
                moveOn = false;
                return retrievedBankManager;
                // gets list of clients
                //UserManager<Client> clientUserManager = BankMachine.getClientManager();
            }else {
                System.out.println("Incorrect username/password");
            }

        }
        return null;
    }
    private Account ChooseAccount(Client client){
        ArrayList<Account> clientAccounts = client.getClientsAccounts();
        // TODO: somehow get names of accounts
        // response = itemize(clientAccounts); // must make sure they are strings though
        // switch(response){
            // case 1:
                // this will be the first account
                // task = itemize(transaction);
                // call transaction
            //case 2:
                // this will be the second account
                // do something account related

        return null;
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
