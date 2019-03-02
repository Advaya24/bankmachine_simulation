package bankmachine.account;

import bankmachine.User;

/**
 * A credit card account where it is not possible to transfer out
 * but it is possible to transfer in, withdraw an pay bills
 */
public class CreditCardAccount extends DebtAccount{
    public CreditCardAccount(int balance, User user) {
        super(balance, user);
    }
    public CreditCardAccount(User user) {
        super(user);
    }


    /**
     * Cannot transfer out
     * @return always false
     */
    public boolean transferOut(int amount){
        return false;
    }

}
