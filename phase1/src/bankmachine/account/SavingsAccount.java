package bankmachine.account;

import bankmachine.Client;
import bankmachine.InputManager;

import java.time.LocalDateTime;
import java.util.Date;

/* Most behaviour is the same as account atm */
public class SavingsAccount extends AssetAccount {
    /**
     * The interest that is applied when applyInterest() is called
     */
    private double interestRate = 0.001;

    public SavingsAccount(int id, int balance, Client client, LocalDateTime creationDate) {
        super(id, balance, client, creationDate);
    }

    /**
     * Transfer money out. Returns false if account doesn't have enough money
     *
     * @param amount the amount to transfer
     * @return true iff transfer was successful
     */
    @Override
    public boolean canTransferOut(int amount) {
        return amount < balance && amount > 0;
    }

    /**
     * Applies interest to the balance. Partial cents are rounded down.
     */
    public void applyInterest() {
        double newBalance = this.balance * (1 + this.interestRate);
        this.balance = (int) Math.round(newBalance);
    }

    public String toString() {
        String output = "";
        output += "ID: " + getID() + " Type: Savings Account Balance: $" + getDoubleBalance();
        return output;
    }
}
