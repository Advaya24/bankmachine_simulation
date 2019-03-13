package bankmachine;

import bankmachine.account.*;
import bankmachine.exception.ShutdownException;
import com.sun.istack.internal.Nullable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A Bank Manager within the system
 **/
//Working on: Varun
public class BankManager extends BankMachineUser {
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
     * Allows the Manager to undo the most recent transaction on any account, except for Bill Payments.
     *
     * @param transaction the Transaction that needs to be undone.
     * @return whether the action was successful or not.
     */
    public boolean undoRecentTransaction(Transaction transaction) {
        if (transaction.getType() == TransactionType.BILL) {
            System.out.println("Error, you cannot undo a Bill Payment.");
            return false;
        } else {
            if (!transaction.getTo().canTransferOut(transaction.getAmount())) {
                System.out.println("You cannot undo this transaction; the account doesn't have enough money!");
                return false;
            }
            if (transaction.getTo() instanceof CreditCardAccount) {
                System.out.println("You cannot undo this transaction; it was made to a Credit Card Account");
                return false;
            } else {
                transaction.getFrom().transferIn(transaction.getAmount());
                transaction.getTo().transferOut(transaction.getAmount());
                transaction.getFrom().getTransactions().remove(transaction);
                transaction.getTo().getTransactions().remove(transaction);
                return true;
            }
        }
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

    /**
     * Displays outstanding account creation requests and allows manager to choose one to remove
     * @param m the input manager handling this
     */
    public void removeCompletedRequests(InputManager m) {
        if (outstandingCreationRequests.size() == 0) {
            System.out.println("No pending creation requests");
        } else {
            outstandingCreationRequests.remove(m.selectItem(outstandingCreationRequests));
        }
    }
}
