package bankmachine.account;

import bankmachine.Transaction;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * An account containing a balance
 */
//TODO: Check Account class hierarchy and possibly move things out of Account
public abstract class Account implements Serializable {
    /* The current balance of the account, in cents*/
    protected int balance;
    ArrayList<Transaction> transactions = new ArrayList<>();
    public Account(){
        this(0);
    }
    public Account(int balance){
        this.balance = balance;
    }

    /**
     * Transfer money from an account into this account
     * @param other the other account
     * @param amount the amount to transfer
     * @return whether the transaction succeeded
     */
    public boolean transferIn(Account other, int amount){
        if (amount < 0){ return false; }
        boolean status = other.transferOut(amount);
        if (!status){
            return false;
        }
        status = this.transferIn(amount);
        if (!status){
            // Attempt to give money back if we cannot receive
            status = other.transferIn(amount);
            assert status; // Transfer back should always succeed
            return false;
        }
        return true;
    }

    /**
     * Transfer money out of this account. see transferIn
     * @param other the account to transfer into
     * @param amount the amount to transfer
     * @return whether the transaction succeeded
     */
    public boolean transferOut(Account other, int amount){
        return other.transferIn(this, amount);
    }

    /**
     * Transfer money in
     * @param amount amount to add to balance
     * @return always true
     */
    boolean transferIn(int amount){
        if (amount < 0) { return false; }
        balance += amount;
        return true;
    }

    /**
     * Transfer money out. Returns false if account doesn't have enough money
     * @param amount to remove
     * @return always true
     */
    boolean transferOut(int amount) {
        if (amount < balance && amount > 0){
            balance -= amount;
            return true;
        }
        return false;
    }
    public boolean payBill(int amount) { return transferOut(amount); }
    public boolean withdraw(int amount){ return transferOut(amount); }

    public int getBalance(){ return balance; }
    public float getFloatBalance() {
        return Math.round(balance/100.0);
    }
    //TODO: Make a getTransactionList class
    public ArrayList<Transaction> getTransactions(){
        return transactions;
    }
}
