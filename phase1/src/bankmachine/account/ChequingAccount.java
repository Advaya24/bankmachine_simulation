package bankmachine.account;


/**
 * An account for withdrawals and deposits
 */
// TODO: Read this:
// Consider naming it CheckingAccount instead?
public class ChequingAccount extends Account {
    /* Whether this is a primary account. Unused but here for now */
    boolean primary = false;
    /* Overdraw limit in dollars, currently fixed at 100, may change */
    private int overdrawLimit = 100;

    public ChequingAccount(){ super(); }
    public ChequingAccount(int amount){ super(amount); }
    public ChequingAccount(boolean primary){
        super();
        this.primary = primary;
    }
    public ChequingAccount(boolean primary, int amount){
        super(amount);
        this.primary = primary;
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

    public boolean payBill (int amount){ return transferOut(amount); }
    public boolean withdraw(int amount){ return transferOut(amount); }

}
