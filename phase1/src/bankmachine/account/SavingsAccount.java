package bankmachine.account;

import bankmachine.Client;

import java.util.Date;

/* Most behaviour is the same as account atm */
public class SavingsAccount extends AssetAccount{
    /* The interest that is applied when applyInterest() is called */
    private double interestRate = 0.001;

    public SavingsAccount(int balance, Client client, Date creationDate) {
        super(balance, client, creationDate);
    }

    /**
     * Applies interest to the balance. Partial cents are rounded down
     */
    public void applyInterest(){
        double newBalance = this.balance * (1 + this.interestRate);
        this.balance = (int) Math.round(newBalance);
    }
    public boolean transferOut(int amount) {
        if (amount < balance && amount > 0){
            balance -= amount;
            return true;
        }
        return false;
    }
    public String toString(){
        String output = "";
        output += "ID: " + getID() +" Type: Savings Account Balance: " + balance;
        return output;
    }
}
