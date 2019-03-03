package bankmachine;

import bankmachine.account.*;
import bankmachine.Exceptions.*;

import java.util.Date;

/**A Bank Manager within the system**/
//Working on: Varun (if that's okay with y'all, seeing that I'm working on Client and the two are kinda linked.
public class BankManager extends BankMachineUser {
    BankManager(String username, String password) {
        super(username, password);
    }

    /**
     * Create a new account for client of type accountType. Return true if the account was created.
     * @param client
     * @param accountType
     * @return whether account creation succeeded
     */
    public boolean createAccount(Client client, String accountType, Date creationDate) {
        Account account1;
        switch (accountType) {
            case "Chequing account":
                account1 = new ChequingAccount(0, client, creationDate);
                break;
            case "Credit card account":
                account1 = new CreditCardAccount(0,client, creationDate);
                break;
            case "Line of credit account":
                account1 = new LineOfCreditAccount(0, client, creationDate);
                break;
            case "Savings account":
                account1 = new SavingsAccount(0, client, creationDate);
                break;
            default:
                System.out.println("Invalid account type. Please try again.");
                return false;
        }
        client.addAccount(account1);
        return true;
    }

    public void createClient(String name, String email, String phoneNumber, String username, String default_password){
        Client newClient = new Client(name, email, phoneNumber, username, default_password);
        ClientManager.addClient(newClient);
    }

    public boolean undoRecentTransaction(Transaction transaction){
        if(transaction.getType()==TransactionType.BILL) {
            return false;
        }
        else {
            transaction.getFrom().transferIn(transaction.getAmount());
            transaction.getTo().transferOut(transaction.getAmount());
            return true;
        }
    }


}
