package bankmachine.account;

import bankmachine.*;
import bankmachine.fileManager.ReadFile;
import bankmachine.fileManager.WriteFile;
import bankmachine.gui.InputManager;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * An account containing a balance
 */
public abstract class Account implements Serializable, Identifiable {
    /**
     * The current balance of the account, in cents
     */
    protected int balance;
    /**
     * The unique id for this account
     */
    private final int id;
    /**
     * The client whose account this is
     */
    protected Client client;
    /**
     * The list of transactions made on this account
     */
    ArrayList<Transaction> transactions = new ArrayList<>();
    /**
     * The date of creation of this account
     */
    protected LocalDateTime creationDate;

    public Account(int id, int balance, Client client, LocalDateTime creationDate) {
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
    public boolean transferIn(Account other, int amount) {
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

    public LocalDateTime getCreationDate() {
        return this.creationDate;
    }

    /**
     * Transfer money out of this account. see transferIn
     *
     * @param other  the account to transfer into
     * @param amount the amount to transfer
     * @return true iff transaction was successful
     */
    public boolean transferOut(Account other, int amount) {
        return other.transferIn(this, amount);
    }

    /**
     * Transfer money out of this account. see transferIn
     *
     * @param acc    the account to transfer into
     * @param amount the amount to transfer
     * @return true iff transaction was successful
     */
    public boolean transferOut(Account acc, double amount) {
        return this.transferOut(acc, (int) (amount * 100));
    }

    /**
     * Transfer money out. Returns false if account doesn't have enough money
     *
     * @param amount the amount to transfer
     * @return true iff transfer was successful
     */
    public boolean transferOut(int amount) {
        if (canTransferOut(amount)) {
            balance -= amount;
            return true;
        }
        return false;
    }

    /**
     * Transfer money out. Returns false if account doesn't have enough money
     *
     * @param amount the amount to transfer
     * @return true iff transfer was successful
     */
    public boolean transferOut(double amount) {
        return this.transferOut((int) (amount * 100));
    }

    /**
     * Transfer money in
     *
     * @param amount amount to add to balance
     * @return true, if and only if amount is non-negative
     */
    boolean transferIn(int amount) {
        if (amount < 0) {
            return false;
        }
        balance += amount;
        return true;
    }

    /**
     * Transfer money in
     *
     * @param amount amount to add to balance
     * @return true, if and only if amount is non-negative
     */
    public boolean transferIn(double amount) {
        return this.transferIn((int) (amount * 100));
    }

    /**
     * Transfer money in
     *
     * @param acc    the account to transfer in from
     * @param amount amount to add to balance
     * @return true, iff amount is non-negative
     */
    public boolean transferIn(Account acc, double amount) {
        return this.transferIn(acc, (int) (amount * 100));
    }

    /**
     * Pays the bill using this account, logs to outgoing.txt file
     *
     * @param amount the amount to be payed
     * @return true iff transaction was successful
     */
    public boolean payBill(int amount) {
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
     * Pays the bill using this account, logs to outgoing.txt file
     *
     * @param amount the amount to be payed
     * @return true iff transaction was successful
     */
    public boolean payBill(double amount) {
        return payBill((int) (amount * 100));
    }

    /**
     * Deposit money into this account if possible.
     * If deposits.txt is one line,
     *
     * @return true iff deposit is made
     */
    public boolean deposit() {
        ReadFile reader = new ReadFile("/deposits.txt");
        String contents;
        try{
            contents = reader.getData();
        } catch (IOException e){
            return false;
        }
        String[] lines = contents.split("\\r?\\n");
        if(lines.length == 0){ return false; }
        double balance = 0.0;
        if (lines.length < 4){
            try{
                balance = Double.parseDouble(lines[0]);
            } catch (NumberFormatException e){
                return false;
            }
        } else {
            int[] denominations = {5, 10, 20, 50};
            int[] quantities = {0, 0, 0, 0};
            for(int i=0; i<4; i++){
                int quantity;
                try {
                    quantity = Integer.parseInt(lines[i]);
                } catch (NumberFormatException e){
                    return false;
                }
                quantities[i] = quantity;
                balance += quantity*denominations[i];
            }
            for (int i=0; i<4; i++){
                BankMachine.getBillManager().addBills(denominations[i], quantities[i]);
            }
        }
        WriteFile writer = new WriteFile("/deposits.txt");
        writer.clearData();
        return transferIn(balance);
    }

    /**
     * Withdraw specified amount, if possible
     *
     * @param amount the amount to withdraw
     * @return true iff withdraw was successful
     */
    public boolean withdraw(int amount) {
        boolean canTransfer = canTransferOut(amount);
        boolean withdraw = false;
        if (canTransfer) {
            withdraw = BankMachine.getBillManager().withdrawBills(amount);
        }
        return withdraw && transferOut(amount);
    }

    /**
     * Withdraw specified amount, if possible
     *
     * @param amount the amount to withdraw
     * @return true iff withdraw was successful
     */
    public boolean withdraw(double amount) {
        return withdraw((int) (amount * 100));
    }

    /* Makes sure subclasses implement a toString */
    abstract public String toString();

    /**
     * Indicates whether this account can transfer out the given amount
     *
     * @param amount the amount to be transferred out
     * @return true iff this account can transfer out this amount
     */
    public abstract boolean canTransferOut(int amount);

    /**
     * Indicates whether this account can transfer out the given amount
     *
     * @param amount the amount to be transferred out
     * @return true iff this account can transfer out this amount
     */
    public boolean canTransferOut(double amount) {
        return canTransferOut((int) (100 * amount));
    }

    /* Getters */
    public int getBalance() {
        return balance;
    }

    public int getID() {
        return id;
    }

    public double getDoubleBalance() {
        return balance / 100.0;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public Client getClient() {
        return client;
    }

    /**
     * Change the balance by the given amount
     *
     * @param amount the amount to change the balance by
     */
    public void changeBalance(int amount) {
        this.balance += amount;
    }


    public Account inputTransfer(InputManager m, double amount){
        String username =  m.getInput("Please input the username of the client." +
                " If you would like to transfer between your accounts, enter your own username.");
        Client client;
        if(BankMachine.USER_MANAGER.get(username) instanceof BankManager ||
                BankMachine.USER_MANAGER.get(username) == null){
            System.out.println("This is not the username of one of our clients.");
            return null;
        }
        else {
            client = (Client)BankMachine.USER_MANAGER.get(username);
        }
        Account a;
        if(client.equals(this.client)){
            a = m.selectItem(client.getClientsAccounts());
        } else {
            a = client.getPrimaryAccount();
        }
        if (a == null){
            return null;
        }
        if(this.transferOut(a, amount)){
            return a;
        } else {
            return null;
        }
    }
}
