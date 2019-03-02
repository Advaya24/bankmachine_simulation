package bankmachine.account;

import bankmachine.User;

/**
 * An account which allows negative balance
 */
public abstract class DebtAccount extends Account{
    public DebtAccount(int balance, User user) {
        super(balance, user);
    }
    public DebtAccount(User user) {
        super(user);
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
