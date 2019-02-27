package bankmachine.account;

/**
 * A credit card account where it is not possible to transfer out
 * but it is possible to transfer in, withdraw an pay bills
 */
public class CreditCardAccount extends DebtAccount{
    /**
     * Cannot transfer out
     * @return always false
     */
    public boolean transferOut(double amount){
        return false;
    }

}
