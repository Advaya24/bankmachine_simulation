package bankmachine.account;

/* Most behaviour is the same as account atm */
public class SavingsAccount extends Account{
    /* The interest that is applied when applyInterest() is called */
    private double interestRate = 0.001;

    /**
     * Applies interest to the balance. Partial cents are rounded down
     */
    public void applyInterest(){
        double newBalance = this.balance * this.interestRate;
        this.balance = (int) Math.floor(newBalance);
    }
}
