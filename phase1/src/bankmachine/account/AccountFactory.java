package bankmachine.account;

import bankmachine.*;

import java.time.LocalDateTime;

/**
 *  Creates and stores accounts
 */
public class AccountFactory extends TrackingFactory<Account> {
    public AccountFactory(UserManager users){
        for(BankMachineUser client : users.getInstances()){
            if(client instanceof Client) {
                this.extend(((Client) client).getClientsAccounts());
            }
        }
    }

    /**
     * Creates new chequing account
     * @param amount the initial amount held by this account
     * @param client the client whose account this is
     * @param creationDate the date of creation for this account
     * @return the new chequing account
     */
    public ChequingAccount newCqAccount(int amount, Client client, LocalDateTime creationDate){
        return newCqAccount(false, amount, client, creationDate);
    }

    /**
     * Creates new chequing account
     * @param primary whether or not this is a primary account
     * @param amount the initial amount held by this account
     * @param client the client whose account this is
     * @param creationDate the date of creation for this account
     * @return the new chequing account
     */
    public ChequingAccount newCqAccount(boolean primary, int amount, Client client, LocalDateTime creationDate){
        ChequingAccount a = new ChequingAccount(getNextID(), amount, client, creationDate);
        addInstance(a);
        return a;
    }

    /**
     * Creates new credit card account
     * @param balance initial balance of this account
     * @param client the client whose account this is
     * @param creationDate the date of creation for this account
     * @return the new credit card account
     */
    public CreditCardAccount newCCAccount(int balance, Client client, LocalDateTime creationDate){
        CreditCardAccount a = new CreditCardAccount(getNextID(), balance, client, creationDate);
        addInstance(a);
        return a;
    }

    /**
     * Creates new line of credit account
     * @param balance the initial balance of this account
     * @param client the client whose account this is
     * @param creationDate the date of creation for this account
     * @return the new line of credit account
     */
    public LineOfCreditAccount newLOCAccount(int balance, Client client, LocalDateTime creationDate){
        LineOfCreditAccount a = new LineOfCreditAccount(getNextID(), balance, client, creationDate);
        addInstance(a);
        return a;
    }

    /**
     * Creates new savings account
     * @param balance the initial balance of this account
     * @param client the client whose account this is
     * @param creationDate the date of creation for this account
     * @return the new savings account
     */
    public SavingsAccount newSavingsAccount(int balance, Client client, LocalDateTime creationDate){
        SavingsAccount a = new SavingsAccount(getNextID(), balance, client, creationDate);
        addInstance(a);
        return a;
    }
}
