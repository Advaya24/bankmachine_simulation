package bankmachine.account;

import bankmachine.Client;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * A credit card account where it is not possible to transfer out
 * but it is possible to transfer in, withdraw an pay bills
 */
public class CreditCardAccount extends DebtAccount{
    public CreditCardAccount(int id, int balance, Client client, LocalDateTime creationDate) {
        super(id, balance, client, creationDate);
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
        output += "ID: " + getID() +" Type: Credit Card Account Balance: " + balance;
        return output;
    }
}
