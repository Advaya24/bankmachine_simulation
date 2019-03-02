package bankmachine;

import bankmachine.account.*;

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
    public boolean createAccount(Client client, String accountType) {
        Account account1;
        switch (accountType) {
            case "Chequing account":
                account1 = new ChequingAccount();
                break;
            case "Credit card account":
                account1 = new CreditCardAccount();
                break;
            case "Line of credit account":
                account1 = new LineOfCreditAccount();
                break;
            case "Savings account":
                account1 = new SavingsAccount();
                break;
            default:
                System.out.println("Invalid account type. Please try again.");
                return false;
        }
        client.addAccount(account1);
        return true;
    }
    //TODO: setPassword
//    public boolean setPassword(Client user) {
//
//    }

    //TODO: restock
//    public boolean restock(int quantity, ) {
//    }

    //TODO: undoRecentTransaction


}
