package bankmachine.account;

/**
 * An account which allows negative balance
 */
public abstract class DebtAccount extends Account{
    /**
     * Subtracts from the balance, balance can go negative
     * @param amount amount to subtract
     * @return always true
     */
    boolean transferOut(double amount){
        if (amount < 0){ return false; }
        this.balance -= amount;
        return true;
    }
    public boolean payBill (double amount){ return transferOut(amount); }
    public boolean withdraw(double amount){ return transferOut(amount); }
}