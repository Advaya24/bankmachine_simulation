package bankmachine.account;

import bankmachine.Client;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

import java.lang.reflect.Type;
import java.util.Date;

public class AccountFactory {
    private Type[] types = {
            ChequingAccount.class,
            SavingsAccount.class,
            CreditCardAccount.class,
            LineOfCreditAccount.class
    };
    public Account getAccount(int type, int id, int balance, Client client, Date creationDate){
        Account account;
        switch(type){
            case 0: account = new ChequingAccount(balance, client, creationDate); break;
            case 1: account = new SavingsAccount(balance, client, creationDate); break;
            case 2: account = new CreditCardAccount(balance, client, creationDate); break;
            case 3: account = new LineOfCreditAccount(balance, client, creationDate); break;
            default: throw new ValueException("Type is not between 0 and 3");
        }
        return account;
    }
    public Account fromRow(String[] columns){
        int argcount = columns.length;
        int type = Integer.parseInt(columns[0]);
        // TODO: self id and get client and get date
        int balance = Integer.parseInt(columns[2]);
        // TODO: call getAccount and return whatever is needed
        return null;

    }
}
