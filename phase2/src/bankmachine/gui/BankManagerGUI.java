package bankmachine.gui;

import bankmachine.*;
import bankmachine.account.Account;
import bankmachine.transaction.Transaction;
import bankmachine.users.BankMachineUser;
import bankmachine.users.BankManager;
import bankmachine.users.Client;
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
     * Displays outstanding account creation requests and allows manager to choose one to remove
     * @param m the input manager handling this
     */
    public void removeCompletedRequests(InputManager m) {
        if (manager.getCreationRequests().size() == 0) {
            System.out.println("No pending creation requests");
        } else {
            manager.getCreationRequests().remove(m.selectItem(manager.getCreationRequests()));
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

    private void handleSelection(InputManager m, String s){
        switch (s) {
            case "Shutdown":
                m.exit(); return;
            case "Exit":
                m.mainLoop(); return;
            case "Settings":
                new UserGUI(manager).handleInput(m); break;
            case "Create Account":
                inputCreateAccount(m); break;
            case "Undo a Transaction":
                inputUndoTransaction(m); break;
            case "Set Time":
                inputSetTime(m); break;
            case "Create Client":
                inputCreateClient(m); break;
            case "Add Bills":
                inputAddBills(m); break;
            case "View Account Creation Requests":
                manager.viewAccountCreationRequests(); break;
            case "Remove Completed Creation Requests":
                removeCompletedRequests(m); break;
            default: break;
        }
        handleInput(m);
    }

    /**
     * Handles the input from the bank manager
     *
     * @param m the input manager handling this
     */
    @Override
    public void handleInput(InputManager m) {
        System.out.println("Welcome, " + manager.getName()+"!");
        String[] options = {
            "View Account Creation Requests", "Remove Completed Creation Requests",
            "Create Account", "Create Client", "Set Time", "Undo a Transaction", "Add Bills",
            "Settings", "Exit", "Shutdown"
        };
        m.setPanel(new OptionsForm<String>(options) {
            @Override
            public void onSelection(String s) {
                handleSelection(m, s);
            }
        });

    }
}
