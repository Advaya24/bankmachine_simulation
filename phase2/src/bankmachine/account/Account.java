package bankmachine.account;

import bankmachine.*;
import bankmachine.fileManager.DepositReader;
import bankmachine.fileManager.WriteFile;
import bankmachine.transaction.Transaction;
import bankmachine.users.Client;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * An account containing a balance
 */
public abstract class Account implements Serializable, Identifiable {
    /** The current balance of the account, in dollars */
    protected double balance;
    /** The unique id for this account */
    private final int id;
    /** The client whose account this is */
    protected Client client;
    /** The list of transactions made on this account */
    ArrayList<Transaction> transactions = new ArrayList<>();
    /** The date of creation of this account */
    protected LocalDateTime creationDate;

    public Account(int id, double balance, Client client, LocalDateTime creationDate) {
        client.addAccount(this);
        this.id = id;
        this.client = client;
        this.balance = balance;
        this.creationDate = creationDate;
    }

    /**
     * Transfer money from an account into this account
     *
     * @param other  the other account
     * @param amount the amount to transfer
     * @return whether the transaction succeeded
     */
    public boolean transferIn(Account other, double amount) {
        if (amount < 0) {
            return false;
        }
        boolean status = other.transferOut(amount);
        if (!status) {
            return false;
        }
        status = this.transferIn(amount);
        if (!status) {
            // Attempt to give money back if we cannot receive
            status = other.transferIn(amount);
            assert status; // Transfer back should always succeed
            return false;
        }
        return true;
    }

    /**
     * Transfer money out of this account. see transferIn
     *
     * @param other  the account to transfer into
     * @param amount the amount to transfer
     * @return true iff transaction was successful
     */
    public boolean transferOut(Account other, double amount) {
        return other.transferIn(this, amount);
    }

    /**
     * Transfer money out. Returns false if account doesn't have enough money
     *
     * @param amount the amount to transfer
     * @return true iff transfer was successful
     */
    public boolean transferOut(double amount) {
        if (canTransferOut(amount)) {
            balance -= amount;
            return true;
        }
        return false;
    }

    /**
     * Transfer money in
     *
     * @param amount amount to add to balance
     * @return true, if and only if amount is non-negative
     */
    public boolean transferIn(double amount) {
        if (amount < 0) {
            return false;
        }
        balance += amount;
        return true;
    }

    /**
     * Pays the bill using this account, logs to outgoing.txt file
     *
     * @param amount the amount to be payed
     * @return true iff transaction was successful
     */
    public boolean payBill(double amount) {
        boolean status = transferOut(amount);
        if (!status) {
            return false;
        }
        WriteFile out = new WriteFile("outgoing.txt");
        out.writeData(
                client.getName() + " paid a bill of $" + (amount / 100),
                true
        );
        return true;
    }

    /**
     * Deposit money into this account if possible.
     * If deposits.txt is one line,
     */
    public void deposit() {
        DepositReader deposit = new DepositReader("/deposits.txt");
        this.transferIn(deposit.getQuantity());
        if(!deposit.isCheque()){
            int denominations[] = BillManager.DENOMINATIONS;
            int quantities[] = deposit.getBillCounts();
            for (int i=0; i<4; i++){
                BankMachine.getBillManager().addBills(denominations[i], quantities[i]);
            }
        }
    }

    /**
     * Withdraw specified amount, if possible
     *
     * @param amount the amount to withdraw
     * @return true iff withdraw was successful
     */
    public boolean withdraw(double amount) {
        boolean canTransfer = canTransferOut(amount);
        boolean withdraw = false;
        if(amount%1!=0){
            return false;
        }
        if (canTransfer) {
            withdraw = BankMachine.getBillManager().withdrawBills((int)amount);
        }
        return withdraw && transferOut(amount);
    }

    abstract public String toString();

    /**
     * Indicates whether this account can transfer out the given amount
     *
     * @param amount the amount to be transferred out
     * @return true iff this account can transfer out this amount
     */
    public abstract boolean canTransferOut(double amount);

    /* Getters */
    public int getID() {
        return id;
    }
    public double getBalance() {
        return ((double)Math.round(100*balance))/100.0;
    }
    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }
    public Client getClient() {
        return client;
    }
    public LocalDateTime getCreationDate() {
        return this.creationDate;
    }

    /**
     * Change the balance by the given amount
     *
     * @param amount the amount to change the balance by
     */
    public void changeBalance(double amount) {
        this.balance += amount;
    }

}
