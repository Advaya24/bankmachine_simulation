package bankmachine.account;

import bankmachine.TrackingFactory;
import bankmachine.users.BankMachineUser;
import bankmachine.users.Client;
import bankmachine.users.UserManager;

import java.time.LocalDateTime;

/**
 * Creates and stores accounts
 */
public class AccountFactory extends TrackingFactory<Account> {
    public AccountFactory(UserManager users) {
        for (BankMachineUser client : users.getInstances()) {
            if (client instanceof Client) {
                this.extend(((Client) client).getClientsAccounts());
            }
        }
    }

    /**
     * Creates new chequing account
     *
     * @param amount       the initial amount held by this account
     * @param client       the owner of the account
     * @param creationDate the date of creation for this account
     */
    public void newChequingAccount(int amount, Client client, LocalDateTime creationDate) {
        newChequingAccount(false, amount, client, creationDate);
    }

    /**
     * Creates new chequing account
     *
     * @param primary      whether or not this is a primary account
     * @param amount       the initial amount held by this account
     * @param client       the owner of the account
     * @param creationDate the date of creation for this account
     */
    public void newChequingAccount(boolean primary, int amount, Client client, LocalDateTime creationDate) {
        ChequingAccount a = new ChequingAccount(getNextID(), amount, client, creationDate);
        addInstance(a);
        a.setPrimary(primary);
    }

    /**
     * Creates new credit card account
     *
     * @param balance      initial balance of this account
     * @param client       the owner of the account
     * @param creationDate the date of creation for this account
     */
    public void newCreditCardAccount(int balance, Client client, LocalDateTime creationDate) {
        CreditCardAccount a = new CreditCardAccount(getNextID(), balance, client, creationDate);
        addInstance(a);
    }

    /**
     * Creates new line of credit account
     *
     * @param balance      the initial balance of this account
     * @param client       the owner of the account
     * @param creationDate the date of creation for this account
     */
    public void newLineOfCreditAccount(int balance, Client client, LocalDateTime creationDate) {
        LineOfCreditAccount a = new LineOfCreditAccount(getNextID(), balance, client, creationDate);
        addInstance(a);
    }

    /**
     * Creates new savings account
     *
     * @param balance      the initial balance of this account
     * @param client       the owner of the account
     * @param creationDate the date of creation for this account
     */
    public void newSavingsAccount(int balance, Client client, LocalDateTime creationDate) {
        SavingsAccount a = new SavingsAccount(getNextID(), balance, client, creationDate);
        addInstance(a);
    }

    /**
     * Creates new retirement account
     *
     * @param client       the client whose account this is
     * @param creationDate the date of creation for this account
     */
    public void newRetirementAccount(Client client, LocalDateTime creationDate) {
        RetirementAccount a = new RetirementAccount(getNextID(), client, creationDate);
        addInstance(a);
    }
}
