package bankmachine.users;

import bankmachine.BankMachine;
import bankmachine.transaction.Transaction;
import bankmachine.transaction.TransactionType;
import bankmachine.account.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * A Bank Manager within the system
 **/
//Working on: Varun
public class BankManager extends BankEmployee {
    final private ArrayList<String> outstandingCreationRequests = new ArrayList<>();

    public BankManager(int id, String name, String email, String phoneNumber, String username, String default_password) {
        super(id, name, email, phoneNumber, username, default_password);
    }

    /**
     * Create a new account for client of type accountType. Return true if the account was created.
     *
     * @param client      the client for whom the account needs to be created
     * @param accountType the type of account to create
     * @return whether account creation succeeded
     */
    public boolean createAccount(Client client, String accountType, LocalDateTime creationDate) {
        AccountFactory factory = BankMachine.accFactory;
        switch (accountType) {
            case "Chequing account":
                factory.newCqAccount(0, client, creationDate);
                break;
            case "Credit card account":
                factory.newCCAccount(0, client, creationDate);
                break;
            case "Line of credit account":
                factory.newLOCAccount(0, client, creationDate);
                break;
            case "Savings account":
                factory.newSavingsAccount(0, client, creationDate);
                break;
            default:
                System.out.println("Invalid account type. Please try again.");
                return false;
        }
        return true;
    }

    /**
     * Creates a new Client for the Bank
     *
     * @param name             name of the client
     * @param email            email address of the client
     * @param phoneNumber      phone number of the client
     * @param username         username of the client
     * @param default_password default password of the client
     */
    public Client createClient(String name, String email, String phoneNumber, String username, String default_password) {
        return BankMachine.USER_MANAGER.newClient(name, email, phoneNumber, username, default_password);
    }

    /**
     * Allows the Manager to add bills of a certain denomination to the ATM
     *
     * @param denomination the denomination of the bills being added
     * @param amount       the number of bills being added.
     */
    public void addBills(int denomination, int amount) {
        BankMachine.getBillManager().addBills(denomination, amount);
    }



    public String toString(){
        return "Manager "+getName();
    }

    /**
     * Adds an account creation request to the list of creation requests
     * @param newRequest the description for the creation request
     */
    public void addCreationRequest(String newRequest) {
        outstandingCreationRequests.add(newRequest);
    }

    /**
     * Displays the outstanding account creation requests
     */
    public void viewAccountCreationRequests() {
        if (outstandingCreationRequests.size() == 0) {
            System.out.println("No pending creation requests");
        }
        for(String request: outstandingCreationRequests) {
            System.out.println(request);
        }
    }

    public ArrayList<String> getCreationRequests() {
        return outstandingCreationRequests;
    }
}
