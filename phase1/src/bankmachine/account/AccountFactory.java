package bankmachine.account;

import bankmachine.*;

import java.time.LocalDateTime;

public class AccountFactory extends TrackingFactory<Account> {
    public AccountFactory(UserManager users){
        for(BankMachineUser client : users.getInstances()){
            if(client instanceof Client) {
                this.extend(((Client) client).getClientsAccounts());
            }
        }
    }

    public ChequingAccount newCqAccount(int amount, Client client, LocalDateTime creationDate){
        return newCqAccount(false, amount, client, creationDate);
    }
    public ChequingAccount newCqAccount(boolean primary, int amount, Client client, LocalDateTime creationDate){
        ChequingAccount a = new ChequingAccount(getNextID(), amount, client, creationDate);
        addInstance(a);
        return a;
    }
    public CreditCardAccount newCCAccount(int balance, Client client, LocalDateTime creationDate){
        CreditCardAccount a = new CreditCardAccount(getNextID(), balance, client, creationDate);
        addInstance(a);
        return a;
    }
    public LineOfCreditAccount newLOCAccount(int balance, Client client, LocalDateTime creationDate){
        LineOfCreditAccount a = new LineOfCreditAccount(getNextID(), balance, client, creationDate);
        addInstance(a);
        return a;
    }
    public SavingsAccount newSavingsAccount(int balance, Client client, LocalDateTime creationDate){
        SavingsAccount a = new SavingsAccount(getNextID(), balance, client, creationDate);
        addInstance(a);
        return a;
    }
}
