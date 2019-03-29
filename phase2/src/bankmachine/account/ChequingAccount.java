package bankmachine.account;

import bankmachine.exception.NegativeQuantityException;
import bankmachine.exception.NotEnoughMoneyException;
import bankmachine.exception.TransferException;
import bankmachine.users.Client;

import java.time.LocalDateTime;

/**
 * An account for withdrawals and deposits
 */
public class ChequingAccount extends AssetAccount {
    /**
     * Whether this is a primary account. Unused but here for now
     */
    private boolean primary = false;
    /**
     * Overdraw limit in dollars, currently fixed at 100, may change
     */
    private int overdrawLimit = 100;


    public ChequingAccount(int id, int amount, Client client, LocalDateTime creationDate) {
        super(id, amount, client, creationDate);
        this.primary = false;
    }

    /**
     * Transfer money out. Returns false if account doesn't have enough money
     *
     * @param amount the amount to transfer
     */
    public void transferOut(int amount) throws TransferException {
        if (amount < 1) {
            throw new NegativeQuantityException();
        }
        if (!canTransferOut(amount)) {
            throw new NotEnoughMoneyException(this);
        }
        balance -= amount;
    }

    /**
     * Indicates whether this account can transfer out the given amount
     *
     * @param amount the amount to be transferred out
     * @return true iff this account can transfer out this amount
     */
    public boolean canTransferOut(double amount) {
        return (!(amount < 0 || this.balance < 0
                || this.balance - amount < -overdrawLimit));
    }

    public String toString() {
        String output = "";
        output += "ID: " + getID() + " Type: Chequing Account Balance: $" + getBalance();
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
