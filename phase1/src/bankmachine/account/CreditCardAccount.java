package bankmachine.account;

import bankmachine.Client;

/**
 * A credit card account where it is not possible to transfer out
 * but it is possible to transfer in, withdraw an pay bills
 */
public class CreditCardAccount extends DebtAccount{
    public CreditCardAccount(int balance, Client client) {
        super(balance, client);
    }
    public CreditCardAccount(Client client) {
        super(client);
    }


    /**
     * Cannot transfer out
     * @return always false
     */
    public boolean transferOut(int amount){
        return false;
    }

}
