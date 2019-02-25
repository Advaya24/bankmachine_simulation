package BankMachine;

/**
 * An account
 */
public abstract class Account {
    protected float balance;
    boolean transferIn(Account other, float amount){
        boolean status = other.transferOut(this, amount);
        if (status){
            this.balance += amount;
        }
        return status;
    }
    abstract boolean transferOut(Account other, float amount);
}
