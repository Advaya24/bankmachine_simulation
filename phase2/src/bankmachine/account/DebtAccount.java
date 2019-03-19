package bankmachine.account;

import bankmachine.exception.NegativeQuantityException;
import bankmachine.exception.TransferException;
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
     */
    public void transferOut(int amount) throws TransferException {
        if (amount < 0) {
            throw new NegativeQuantityException();
        }
        this.balance -= amount;
    }

    @Override
    public double getBalance() {
        return -balance;
    }
}
