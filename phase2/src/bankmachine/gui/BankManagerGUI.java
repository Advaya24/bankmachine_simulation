package bankmachine.gui;

import bankmachine.*;
import bankmachine.account.Account;
import bankmachine.exception.ShutdownException;
import com.sun.istack.internal.Nullable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BankManagerGUI implements Inputtable {
    private BankManager manager;
    public BankManagerGUI(BankManager b){
        manager = b;
    }


    /**
     * Displays options for adding bills to the bankmachine
     *
     * @param m the input manager handling this
     */
    private void inputAddBills(InputManager m) {
        int[] denominations = {5, 10, 20, 50};
        for (int i : denominations) {
            int quantity = m.getInteger("How many " + i + "s? ");
            BankMachine.getBillManager().addBills(i, quantity);
        }
    }

    private void inputUndoTransaction(InputManager m){
        Client client = inputGetClient(m);
        if (client == null) {
            return;
        }
        if(client.getClientsAccounts().size()==0){
            System.out.println("There are no accounts!");
            return;
        }
        System.out.println("Select an account");
        Account account = m.selectItem(client.getClientsAccounts());
        if(account.getTransactions().size()==0){
            System.out.println("There are no transactions!");
            return;
        }
        System.out.println("Select a transaction");
        Transaction transaction = m.selectItem(account.getTransactions());
        if(manager.undoRecentTransaction(transaction)){
            System.out.println("Successful!");
        }
    }

    private void inputSetTime(InputManager m){
        BankMachine.getTimeInfo().setTime(m.getDate());
    }



    /**
     * Displays all clients and allows the manager to select a client
     * @param m the input manager handling this
     * @return returns the selected client, null if there are no clients
     */
    @Nullable
    private Client inputGetClient(InputManager m) {
        List<Client> clients = new ArrayList<>();
        for (BankMachineUser b : BankMachine.USER_MANAGER.getInstances()) {
            if (b instanceof Client) {
                clients.add((Client) b);
            }
        }
        if(clients.size() == 0){
            System.out.println("There are no clients!");
            return null;
        }
        System.out.println("Select a client");
        return m.selectItem(clients);
    }

    /**
     * Displays options for creating a new account
     *
     * @param m the input manager handling this
     */
    private void inputCreateAccount(InputManager m) {
        Client client = inputGetClient(m);
        if (client == null) {
            return;
        }
        List<String> accTypes = new ArrayList<>(Arrays.asList(
                "Chequing account", "Credit card account",
                "Line of credit account", "Savings account", "Cancel"
        ));

        String selection = m.selectItem(accTypes);
        if (selection.equals("Cancel")){
            return;
        }
        if (manager.createAccount(client, selection, LocalDateTime.now())) {
            System.out.println("Account created successfully");
        }
    }

    /**
     * Displays options for creating a new client
     *
     * @param m the input manager handling this
     */
    private void inputCreateClient(InputManager m){
        String name = m.getInput("Enter a name: ");
        String username = m.getInput("Enter a username: ");
        String phone = m.getPhone();
        String email = m.getEmail();
        String pwd = m.getInput("Enter a password: ");
        Client client = BankMachine.USER_MANAGER.newClient(name, email, phone, username, pwd);
        if(client == null){
            System.out.println("A client with that username exists!");
        } else {
            System.out.println("Client created");
        }
    }

    /**
     * Handles the input from the bank manager
     *
     * @param m the input manager handling this
     */
    @Override
    public void handleInput(InputManager m) {
        System.out.println("Welcome, " + manager.getName()+"!");
        while (true) {
            System.out.println("Select an action");
            List<String> options = new ArrayList<>(Arrays.asList(
                    "View Account Creation Requests", "Remove Completed Creation Requests",
                    "Create Account", "Create Client", "Set Time", "Undo a Transaction", "Add Bills",
                    "Settings", "Exit", "Shutdown"
            ));
            String action = m.selectItem(options);
            // Options for bank manager
            switch (action) {
                case "Shutdown":
                    throw new ShutdownException();
                case "Exit":
                    return;
                case "Settings":
                    new UserGUI(manager).handleInput(m);
                    break;
                case "Create Account":
                    inputCreateAccount(m);
                    break;
                case "Undo a Transaction":
                    inputUndoTransaction(m); break;
                case "Set Time":
                    inputSetTime(m);
                case "Create Client":
                    inputCreateClient(m);
                    break;
                case "Add Bills":
                    inputAddBills(m);
                    break;
                case "View Account Creation Requests":
                    manager.viewAccountCreationRequests();
                    break;
                case "Remove Completed Creation Requests":
                    manager.removeCompletedRequests(m);
                    break;
                default:
                    break;
            }
        }

    }
}
