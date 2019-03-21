package bankmachine.account;

import bankmachine.*;
import bankmachine.exception.*;
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
    protected Client primaryClient;
    /** The multiple owners of this account*/
    private ArrayList<Client> clients;
    /** The list of transactions made on this account */
    ArrayList<Transaction> transactions = new ArrayList<>();
    /** The date of creation of this account */
    protected LocalDateTime creationDate;

    public Account(int id, double balance, Client client, LocalDateTime creationDate) {
        client.addAccount(this);
        this.id = id;
        this.primaryClient = client;
        this.clients = new ArrayList<>();
        this.clients.add(client);
        this.balance = balance;
        this.creationDate = creationDate;
    }

    /**
     * Transfer money from an account into this account
     *
     * @param other  the other account
     * @param amount the amount to transfer
     */
    public void transferIn(Account other, double amount) throws BankMachineException{
        if (amount < 0) {
            throw new NegativeQuantityException();
        }
        other.transferOut(amount);
        this.transferIn(amount);
    }

    /**
     * Transfer money out of this account. see transferIn
     *
     * @param other  the account to transfer into
     * @param amount the amount to transfer
     */
    public void transferOut(Account other, double amount) throws BankMachineException {
        other.transferIn(this, amount);
    }

    /**
     * Transfer money out. Returns false if account doesn't have enough money
     *
     * @param amount the amount to transfer
     */
    public void transferOut(double amount) throws TransferException {
        if (amount < 0) {
            throw new NegativeQuantityException();
        }
        if (!canTransferOut(amount)) {
            throw new NotEnoughMoneyException(this);
        }
        balance -= amount;
    }

    /**
     * Transfer money in
     *
     * @param amount amount to add to balance
     */
    public void transferIn(double amount) throws NegativeQuantityException {
        if (amount < 0) {
            throw new NegativeQuantityException();
        }
        balance += amount;
    }

    /**
     * Pays the bill using this account, logs to outgoing.txt file
     *
     * @param amount the amount to be payed
     */
    public void payBill(double amount) throws TransferException {
        transferOut(amount);
        WriteFile out = new WriteFile("outgoing.txt");
        out.writeData(
                primaryClient.getName() + " paid a bill of $" + (amount / 100),
                true
        );
    }

    /**
     * Deposit money into this account if possible.
     * If deposits.txt is one line,
     */
    public void deposit() throws NegativeQuantityException {
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
     */
    public void withdraw(double amount) throws BankMachineException {
        if(!canTransferOut(amount)){
            throw new NotEnoughMoneyException(this);
        }
        BankMachine.getBillManager().withdrawBills(amount);
        transferOut(amount);
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
        return primaryClient;
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
