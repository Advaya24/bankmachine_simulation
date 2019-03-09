package bankmachine;

import bankmachine.account.*;

import java.time.LocalDateTime;

/**A Bank Manager within the system**/
//Working on: Varun (if that's okay with y'all, seeing that I'm working on Client and the two are kinda linked.
public class BankManager extends BankMachineUser {
    BankManager(int id, String username, String password) {
        super(id, username, password);
    }

    /**
     * Create a new account for client of type accountType. Return true if the account was created.
     * @param client
     * @param accountType
     * @return whether account creation succeeded
     */
    public boolean createAccount(Client client, String accountType, LocalDateTime creationDate) {
        AccountFactory factory = BankMachine.accFactory;
        Account account1;
        switch (accountType) {
            case "Chequing account":
                account1 = factory.newCqAccount(0, client, creationDate);
                break;
            case "Credit card account":
                account1 = factory.newCCAccount(0,client, creationDate);
                break;
            case "Line of credit account":
                account1 = factory.newLOCAccount(0, client, creationDate);
                break;
            case "Savings account":
                account1 = factory.newSavingsAccount(0, client, creationDate);
                break;
            default:
                System.out.println("Invalid account type. Please try again.");
                return false;
        }
        client.addAccount(account1);
        return true;
    }

    /**
     * Creates a new Client for the Bank
     * @param name name of the client
     * @param email email address of the client
     * @param phoneNumber phone number of the client
     * @param username username of the client
     * @param default_password default password of the client
     */
    public Client createClient(String name, String email, String phoneNumber, String username, String default_password){
        return BankMachine.USER_MANAGER.newClient(name, email, phoneNumber, username, default_password);
        // Authenticator<Client> clientUserManager = BankMachine.getClientManager();
        // clientUserManager.add(newClient);
    }

    /**
     * Allows the Manager to undo the most recent transaction on any account, except for Bill Payments.
     * @param transaction the Transaction that needs to be undone.
     * @return whether the action was successful or not.
     */
    public boolean undoRecentTransaction(Transaction transaction){

        // TODO: handle corner case where one of the accounts does not have enough balance
        if(transaction.getType()==TransactionType.BILL) {
            return false;
        }
        else {
            transaction.getFrom().transferIn(transaction.getAmount());
            transaction.getTo().transferOut(transaction.getAmount());
            return true;
        }
    }

    /**
     * Allows the Manager to add bills of a certain denomination to the ATM
     * @param denomination the denomination of the bills being added
     * @param amount the number of bills being added.
     */
    public void addBills(int denomination, int amount) {
        BankMachine.getBillManager().addBills(denomination, amount);
    }
}
