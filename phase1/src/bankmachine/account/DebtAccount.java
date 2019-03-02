package bankmachine.account;

import bankmachine.Client;

/**
 * An account which allows negative balance
 */
public abstract class DebtAccount extends Account{
    public DebtAccount(int balance, Client client) {
        super(balance, client);
    }
    public DebtAccount(Client client) {
        super(client);
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
