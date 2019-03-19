package bankmachine.account;

import bankmachine.users.Client;

import java.time.LocalDateTime;

/**
 * An account which allows negative balance
 */
public abstract class DebtAccount extends Account {
    public DebtAccount(int id, int balance, Client client, LocalDateTime creationDate) {
        super(id, balance, client, creationDate);
    }

    /**
     * Subtracts from the balance, balance can go negative
     *
     * @param amount amount to subtract
     * @return always true
     */
    public boolean transferOut(int amount) {
        if (amount < 0) {
            return false;
        }
        this.balance -= amount;
        return true;
    }

    @Override
    public double getBalance() {
        return -balance;
    }
}
