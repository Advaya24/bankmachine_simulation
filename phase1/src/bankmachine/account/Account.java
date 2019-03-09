package bankmachine.account;

import bankmachine.*;
import bankmachine.fileManager.WriteFile;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * An account containing a balance
 */
public abstract class Account implements Serializable, Identifiable, Inputtable {
    /* The current balance of the account, in cents*/
    protected int balance;
    /* The unique id for this account */
    private final int id;
    /* The client whose account this is */
    protected Client client;
    /* The list of transactions made on this account */
    ArrayList<Transaction> transactions = new ArrayList<>();
    /* The date of creation of this account */
    protected LocalDateTime creationDate;

    public Account(int id, int balance, Client client, LocalDateTime creationDate){
        client.addAccount(this);
        this.id = id;
        this.client = client;
        this.balance = balance;
        this.creationDate = creationDate;
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
     * @return true, if and only if amount is non-negative
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
    public boolean transferOut(int amount){
        if (canTransferOut(amount)){
            balance -= amount;
            return true;
        }
        return false;
    }

    /**
     * Pays the bill using this account
     * @param amount the amount to be payed
     * @return
     */
    public boolean payBill(int amount) {
        boolean status = transferOut(amount);
        if (!status){ return false; }

        WriteFile out = new WriteFile("outgoing.txt");
        out.writeData(
            client.getName() + " paid a bill of $" + (amount/100),
            true
        );
        return true;
    }
    public boolean deposit(int amount) {
        boolean status = transferIn(amount);
        if (!status){ return false; }

        WriteFile out = new WriteFile("deposits.txt");
        out.writeData(
            client.getName() + " deposited $" + (amount/100),
            true
        );
        return true;
    }
    public boolean withdraw(int amount) {
        boolean canTransfer = canTransferOut(amount);
        boolean withdraw = false;
        if (canTransfer) {
            withdraw = BankMachine.getBillManager().withdrawBills(amount);
        }
        return withdraw && transferOut(amount);
    }
    abstract public String toString();

    public boolean payBill(double amount) { return payBill((int)(amount*100)); }
    public boolean deposit(double amount) { return deposit((int)(amount*100)); }
    public boolean withdraw(double amount){ return withdraw((int)(amount*100)); }
    public boolean canTransferOut(double amount){ return canTransferOut((int)(amount*100));}
    abstract public boolean canTransferOut(int amount);
    public boolean transferIn(Account acc, double amount){
        return this.transferIn(acc, (int)(amount*100));
    }
    public boolean transferIn(double amount){
        return this.transferIn((int)(amount*100));
    }
    public boolean transferOut(Account acc, double amount){ return this.transferOut(acc, (int)(amount*100)); }
    public boolean transferOut(double amount){
        return this.transferOut((int)(amount*100));
    }

    public int getBalance(){ return balance; }
    public int getID(){ return id;}
    public double getDoubleBalance() {
        return balance/100.0;
    }
    public ArrayList<Transaction> getTransactions(){
        return transactions;
    }
    public Client getClient(){
        return client;
    }

    public void changeBalance(int amount){
        this.balance += amount;
    }

    @Override
    public void handleInput(InputManager m){
        //TODO: Implement this and in subclasses
    }
}
