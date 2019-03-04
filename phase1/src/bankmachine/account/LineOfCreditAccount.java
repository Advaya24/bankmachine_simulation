package bankmachine.account;

import bankmachine.Client;

import java.util.Date;

public class LineOfCreditAccount extends DebtAccount{

    public LineOfCreditAccount(int balance, Client client, Date creationDate) {
        super(balance, client, creationDate);
    }
    public String toString(){
        String output = "";
        output += "ID: " + getID() +" Type: Line of Credit Account Balance: " + balance;
        return output;
    }
}
