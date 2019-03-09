package bankmachine.account;

import bankmachine.BankMachine;
import bankmachine.Client;


import bankmachine.BillManager;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * An account for withdrawals and deposits
 */
public class ChequingAccount extends AssetAccount {
    /* Whether this is a primary account. Unused but here for now */
    private boolean primary = false;
    /* Overdraw limit in dollars, currently fixed at 100, may change */
    private int overdrawLimit = 100;


    public ChequingAccount(boolean primary, int id, int amount, Client client, LocalDateTime creationDate) {
        super(id, amount, client, creationDate);
        this.primary = primary;
    }

    public ChequingAccount(int id, int amount, Client client, LocalDateTime creationDate) {
        super(id, amount, client, creationDate);
        this.primary = false;
    }

    /**
     * Transfer money out. Returns false if account doesn't have enough money
     *
     * @param amount the amount to transfer
     * @return true iff transfer was successful
     */
    public boolean transferOut(int amount) {
        if (!canTransferOut(amount)) {
            return false;
        }
        balance -= amount;
        return true;
    }

    /**
     * Indicates whether this account can transfer out the given amount
     *
     * @param amount the amount to be transferred out
     * @return true iff this account can transfer out this amount
     */
    public boolean canTransferOut(int amount) {
        return (!(amount < 0 || this.balance < 0
                || this.balance - amount < -100 * overdrawLimit));
    }

    public String toString() {
        String output = "";
        output += "ID: " + getID() + " Type: Chequing Account Balance: $" + getDoubleBalance();
        if (isPrimary()) {
            output += " [Primary]";
        }
        return output;
    }

    /**
     * Indicates whether this is a primary account
     *
     * @return true iff this is a primary account
     */
    public boolean isPrimary() {
        return primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }
}
