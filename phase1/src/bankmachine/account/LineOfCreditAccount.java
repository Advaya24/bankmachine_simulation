package bankmachine.account;

import bankmachine.Client;

public class LineOfCreditAccount extends DebtAccount{

    public LineOfCreditAccount(int balance, Client client) {
        super(balance, client);
    }
    public LineOfCreditAccount(Client client) {
        super(client);
    }
}
