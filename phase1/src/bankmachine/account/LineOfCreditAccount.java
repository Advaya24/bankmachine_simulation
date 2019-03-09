package bankmachine.account;

import bankmachine.Client;

import java.time.LocalDateTime;
import java.util.Date;

public class LineOfCreditAccount extends DebtAccount{

    public LineOfCreditAccount(int id, int balance, Client client, LocalDateTime creationDate) {
        super(id, balance, client, creationDate);
    }
    @Override
    public boolean canTransferOut(int amount){
        return true;
    }

    public String toString(){
        String output = "";
        output += "ID: " + getID() +" Type: Line of Credit Account Balance: $" + getDoubleBalance();
        return output;
    }
}
