package bankmachine.account;

import bankmachine.Client;

import java.util.Date;

/**
 * A credit card account where it is not possible to transfer out
 * but it is possible to transfer in, withdraw an pay bills
 */
public class CreditCardAccount extends DebtAccount{
    public CreditCardAccount(int balance, Client client, Date creationDate) {
        super(balance, client, creationDate);
    }


    /**
     * Cannot transfer out
     * @return always false
     */
    public boolean transferOut(int amount){
        return false;
    }
    public String toString(){
        String output = "";
        output += "ID: " + id +" Type: Credit Card Account Balance: " + balance;
        return output;
    }
}
