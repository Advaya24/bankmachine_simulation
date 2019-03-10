package bankmachine;

import bankmachine.account.Account;
import bankmachine.account.CreditCardAccount;

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
        if(type==TransactionType.BILL){
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

    public String toString(){
        String output = "Transaction between " + getFrom().getClient().getUsername() + " and " +
                getTo().getClient().getUsername() + " of $" + getAmount();
        return output;
    }

    /**
     * Performs the transaction between the two Accounts
     *
     * @return true iff the transaction is successful
     */
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
}
