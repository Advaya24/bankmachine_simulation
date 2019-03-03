package bankmachine.account;

import bankmachine.Client;

import java.util.Date;

public class LineOfCreditAccount extends DebtAccount{

    public LineOfCreditAccount(int balance, Client client, Date creationDate) {
        super(balance, client, creationDate);
    }
}
