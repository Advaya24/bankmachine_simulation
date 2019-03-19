package bankmachine.users;

import bankmachine.BankMachine;
import bankmachine.exception.BankMachineException;
import bankmachine.exception.TransactionUndoException;
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


    public String toString(){
        return "Manager "+getName();
    }


    /**
     * Allows the Manager to undo the most recent transaction on any account, except for Bill Payments.
     *
     * @param transaction the Transaction that needs to be undone.
     */

    public void undoRecentTransaction(Transaction transaction) throws BankMachineException {
        if (transaction.getType() == TransactionType.BILL) {
            throw new TransactionUndoException("Error, you cannot undo a Bill Payment.");
        } else {
            if (!transaction.getTo().canTransferOut(transaction.getAmount())) {
                throw new TransactionUndoException("You cannot undo this transaction; the account doesn't have enough money!");
            }
            if (transaction.getTo() instanceof CreditCardAccount) {
                throw new TransactionUndoException("You cannot undo this transaction; it was made to a Credit Card Account");
            } else {
                transaction.getFrom().transferIn(transaction.getAmount());
                transaction.getTo().transferOut(transaction.getAmount());
                transaction.getFrom().getTransactions().remove(transaction);
                transaction.getTo().getTransactions().remove(transaction);
            }
        }
    }

}
