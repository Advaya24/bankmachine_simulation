package bankmachine.account;

/**
 * An account
 */
public abstract class Account {
    /* The current balance of the account*/
    protected double balance;

    /**
     * Transfer money from an account into this account
     * @param other the other account
     * @param amount the amount to transfer
     * @return whether the transaction succeded
     */
    boolean transferIn(Account other, double amount){
        if (amount < 0){ return false; }
        boolean status = other.transferOut(amount);
        if (!status){
            return false;
        }
        status = this.transferIn(amount);
        if (!status){
            // Attempt to give money back if we cannot receive
            status = other.transferIn(amount);
            assert status; // Transfer back should always succeed
            return false;
        }
        return true;
    }

    /**
     * Transfer money out of this account. see transferIn
     * @param other the account to transfer into
     * @param amount the amount to transfer
     * @return whether the transaction succeeded
     */
    boolean transferOut(Account other, double amount){
        return other.transferIn(this, amount);
    }

    /**
     * Transfer money in
     * @param amount amount to add to balance
     * @return always true
     */
    boolean transferIn(double amount){
        if (amount > 0) {
            balance += amount;
            return true;
        }
        return false;
    }

    /**
     * Transfer money out. Returns false if account doesn't have enough money
     * @param amount to remove
     * @return always true
     */
    boolean transferOut(double amount) {
        if (amount < balance && amount > 0){
            balance -= amount;
            return true;
        }
        return false;
    }
    boolean payBill(double amount) { return transferOut(amount); }
    boolean withDraw(double amount){ return transferOut(amount); }
    public double getBalance(){ return balance; }

}
