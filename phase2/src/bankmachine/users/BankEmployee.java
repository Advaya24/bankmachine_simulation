package bankmachine.users;

import bankmachine.BankMachine;
import bankmachine.account.Account;
import bankmachine.account.AccountFactory;
import bankmachine.exception.NegativeQuantityException;

import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class BankEmployee extends Client {

    /**
     * Abstract class to describe a bank employee.
     * Any bank employee can refill the ATM with more bills, and add and create new account requests.
     */

    final private static ArrayList<String> outstandingCreationRequests = new ArrayList<>();
    protected double salary;


    public BankEmployee(int id, String name, String email, String phoneNumber, String username, String default_password) {
        super(id, name, email, phoneNumber, username, default_password);
    }


    /**
     * Adds an account creation request to the list of creation requests
     *
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
        for (String request : outstandingCreationRequests) {
            System.out.println(request);
        }
    }

    public String[] getCreationRequestArray() {
        String[] arrayOutstandingCreationRequests = new String[outstandingCreationRequests.size()];
        for (int i = 0; i < outstandingCreationRequests.size(); i++) {
            arrayOutstandingCreationRequests[i] = "Request " + (i + 1) + ": " + outstandingCreationRequests.get(i);
        }
        return arrayOutstandingCreationRequests;
    }

    public ArrayList<String> getCreationRequests() {
        return outstandingCreationRequests;
    }

    public void removeCreationRequest(int requestIndex) {
        outstandingCreationRequests.remove(requestIndex);
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
                factory.newChequingAccount(0, client, creationDate);
                break;
            case "Credit card account":
                factory.newCreditCardAccount(0, client, creationDate);
                break;
            case "Line of credit account":
                factory.newLineOfCreditAccount(0, client, creationDate);
                break;
            case "Savings account":
                factory.newSavingsAccount(0, client, creationDate);
                break;
            case "Retirement account":
                factory.newRetirementAccount(client, creationDate);
                break;
            default:
                System.out.println("Invalid account type. Please try again.");
                return false;
        }
        return true;
    }


    public void receivePayment() {
        Account primaryAccount = this.getPrimaryAccount();
        try {
            primaryAccount.transferIn(this.salary);
        } catch (NegativeQuantityException e) {
            //salaries are always positive
        }
    }


}
