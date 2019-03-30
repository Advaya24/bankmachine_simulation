package bankmachine.transaction;

import bankmachine.Identifiable;
import bankmachine.account.Account;
import bankmachine.account.CreditCardAccount;
import bankmachine.exception.BankMachineException;
import bankmachine.exception.TransactionUndoException;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Represents a Transaction within the system.
 * This class does not actually have any methods; it just is a container for information.
 **/
// Person working on this: Varun
public class Transaction implements Serializable, Identifiable {
    /**
     * The amount of money involve in this transaction
     **/
    private double amount;
    /**
     * The account this transaction was made from
     **/
    private Account transactionMadeFrom;
    /**
     * The account this transaction was made to
     **/
    private Account transactionMadeTo;
    /**
     * The LocalDateTime this transaction was made
     **/
    private LocalDateTime transactionDate;
    /**
     * The type of transaction made
     **/
    private TransactionType transactionType;
    /**
     * The id of this transaction
     **/
    private int id;

    public Transaction(int id, double amount, Account from, Account to, LocalDateTime datetime, TransactionType type) {
        this.amount = amount;
        transactionMadeFrom = from;
        transactionMadeTo = to;
        transactionDate = datetime;
        transactionType = type;
        if (type == TransactionType.BILL) {
            transactionMadeTo = null;
        }
        this.id = id;
    }

    /**
     * All the getters
     **/
    public double getAmount() {
        return amount;
    }

    public Account getFrom() {
        return transactionMadeFrom;
    }

    public Account getTo() {
        return transactionMadeTo;
    }

    public LocalDateTime getDate() {
        return transactionDate;
    }

    public TransactionType getType() {
        return transactionType;
    }

    public int getID() {
        return id;
    }

    /**
     * All the setters
     **/
    public void setAmount(double new_amount) {
        amount = new_amount;
    }

    public void setFrom(Account new_from) {
        transactionMadeFrom = new_from;
    }

    public void setTo(Account new_to) {
        transactionMadeTo = new_to;
    }

    public void setLocalDateTime(LocalDateTime new_date) {
        transactionDate = new_date;
    }

    public void setType(TransactionType new_type) {
        transactionType = new_type;
    }

    public String toString() {
        return "Transaction between " + getFrom().getClient().getUsername() + " and " +
                getTo().getClient().getUsername() + " of $" + getAmount();
    }


    /**
     * Allows the Manager to undo the most recent transaction on any account, except for Bill Payments.
     */

    public void undo() throws BankMachineException {
        if (this.getType() == TransactionType.BILL) {
            throw new TransactionUndoException("Error, you cannot undo a Bill Payment.");
        } else {
            if (!getTo().canTransferOut(getAmount())) {
                throw new TransactionUndoException("You cannot undo this transaction; the account doesn't have enough money!");
            }
            if (getTo() instanceof CreditCardAccount) {
                throw new TransactionUndoException("You cannot undo this transaction; it was made to a Credit Card Account");
            } else {
                getFrom().transferIn(getAmount());
                getTo().transferOut(getAmount());
                getFrom().getTransactions().remove(this);
                getTo().getTransactions().remove(this);
            }
        }
    }
    //Following method kept here as a record
    /*
     * Performs the transaction between the two Accounts
     *
     * @return true iff the transaction is successful

    public boolean performTransaction() {
        if (!getFrom().canTransferOut(getAmount())) {
            System.out.println("You cannot do this transaction; the account doesn't have enough money!");
            return false;
        }
        if (getFrom() instanceof CreditCardAccount) {
            System.out.println("You cannot do this transaction; it was made from a Credit Card Account");
            return false;
        } else {
            getTo().transferIn(getAmount());
            getFrom().transferOut(getAmount());
            return true;
        }
    }
    */
}
