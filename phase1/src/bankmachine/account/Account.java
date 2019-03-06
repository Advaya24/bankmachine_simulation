package bankmachine.account;

import bankmachine.Client;
import bankmachine.Transaction;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * An account containing a balance
 */
//TODO: Check Account class hierarchy and possibly move things out of Account
public abstract class Account implements Serializable {
    public static List<Account> accounts = new ArrayList<>();
    /* The current balance of the account, in cents*/
    protected int balance;
    private int id;
    protected Client client;
    ArrayList<Transaction> transactions = new ArrayList<>();
    protected LocalDateTime creationDate;
    public Account(int balance, Client client, LocalDateTime creationDate){
        client.addAccount(this);
        id = numAccounts();
        this.client = client;
        this.balance = balance;
        this.creationDate = creationDate;
        accounts.add(this);
    }
    public Account(Client client){
        this(0, client, LocalDateTime.now());
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
    public static int numAccounts(){
        return accounts.size();
    }

    public LocalDateTime getCreationDate(){
        return this.creationDate;
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
    public abstract boolean transferOut(int amount);
    public boolean payBill(int amount) { return transferOut(amount); }
    //TODO: decide if we want the withdraw method here or only in chequing
    //public boolean withdraw(int amount){ return transferOut(amount); }
    abstract public String toString();

    public boolean payBill(double amount) { return transferOut((int)(amount*100)); }
    //public boolean withdraw(double amount){ return transferOut((int)(amount*100)); }

    public boolean transferIn(Account acc, double amount){
        return this.transferIn(acc, (int)(amount*100));
    }
    public boolean transferIn(double amount){
        return this.transferIn((int)(amount*100));
    }
    public boolean transferOut(Account acc, double amount){
        return this.transferOut(acc, (int)(amount*100));
    }
    public boolean transferOut(double amount){
        return this.transferOut((int)(amount*100));
    }

    public int getBalance(){ return balance; }
    public int getID(){ return id;}
    public double getDoubleBalance() {
        return balance/100.0;
    }
    //TODO: Make a getTransactionList class
    public ArrayList<Transaction> getTransactions(){
        return transactions;
    }
    public Client getClient(){
        return client;
    }

    public void changeBalance(int amount){
        this.balance += amount;
    }
}
