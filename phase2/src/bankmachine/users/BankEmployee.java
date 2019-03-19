package bankmachine.users;

import bankmachine.BankMachine;
import bankmachine.account.CreditCardAccount;
import bankmachine.exception.BankMachineException;
import bankmachine.exception.TransactionUndoException;
import bankmachine.transaction.Transaction;
import bankmachine.transaction.TransactionType;

import java.util.ArrayList;

public abstract class BankEmployee extends BankMachineUser {

    /**
     * Abstract class to describe a bank employee.
     * Any bank employee can undo the most recent transaction of a client.
     *
     */

    final private static ArrayList<String> outstandingCreationRequests = new ArrayList<>();

    //TODO: decide what our bank employee will do.

    public BankEmployee(int id, String name, String email, String phoneNumber, String username, String default_password) {
        super(id, name, email, phoneNumber, username, default_password);
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
