package bankmachine.account;

import bankmachine.Client;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * An account which allows negative balance
 */
public abstract class DebtAccount extends Account{
    public DebtAccount(int balance, Client client, LocalDateTime creationDate) {
        super(balance, client, creationDate);
    }

    /**
     * Subtracts from the balance, balance can go negative
     * @param amount amount to subtract
     * @return always true
     */
    public boolean transferOut(int amount){
        if (amount < 0){ return false; }
        this.balance -= amount;
        return true;
    }
}
