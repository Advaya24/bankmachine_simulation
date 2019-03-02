package bankmachine.account;

import bankmachine.User;


import bankmachine.BankMachine;

/**
 * An account for withdrawals and deposits
 */
public class ChequingAccount extends Account {
    /* Whether this is a primary account. Unused but here for now */
    boolean primary = false;
    /* Overdraw limit in dollars, currently fixed at 100, may change */
    private int overdrawLimit = 100;


    public ChequingAccount(boolean primary, int amount, User user){
        super(amount, user);
        this.primary = primary;
    }
    public ChequingAccount(int amount, User user){
        super(amount, user);
        this.primary = false;
    }
    public ChequingAccount(User user){
        super(0, user);
        this.primary = false;
    }

    /**
     * Transfer money out. Can overdraw up to $100
     * @param amount to remove
     * @return see superclass
     */
    public boolean transferOut(int amount){
        if (amount < 0 || this.balance < 0
                || this.balance - amount < -100*overdrawLimit){
            return false;
        }
        return super.transferOut(amount);
    }

    // I had to add this method so that withdraw works nicely ~ Lorenzo
    /**
     * Return whether it is possible to transfer money equivalent to amount out.
     * @param amount to be transferred
     * @return true if and only if amount can be withdrawn
     */
    private boolean canTransferOut(int amount) {
        return (!(amount < 0 || this.balance < 0
                || this.balance - amount < -100*overdrawLimit));
    }

    public boolean payBill (int amount){ return transferOut(amount); }

    public boolean withdraw(int amount){
        boolean canTransfer = canTransferOut(amount);
        boolean withdraw = false;
        if (canTransfer) {
            withdraw = BankMachine.withdraw(amount);
        }
        return withdraw && transferOut(amount);
    }
}
