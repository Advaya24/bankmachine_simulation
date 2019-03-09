package bankmachine;
import bankmachine.account.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**A Bank Manager within the system**/
//Working on: Varun (if that's okay with y'all, seeing that I'm working on Client and the two are kinda linked.
public class BankManager extends BankMachineUser {
    public BankManager(int id, String name, String email, String phoneNumber, String username, String default_password) {
        super(id,name,email, phoneNumber, username, default_password);
    }

    /**
     * Create a new account for client of type accountType. Return true if the account was created.
     * @param client the client for whom the account needs to be created
     * @param accountType the type of account to create
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
    public boolean undoRecentTransaction(Transaction transaction) {
        if (transaction.getType() == TransactionType.BILL) {
            System.out.println("Error, you cannot undo a Bill Payment.");
            return false;
        }
        else {
            if (!transaction.getTo().canTransferOut(transaction.getAmount())) {
                System.out.println("You cannot undo this transaction; the account doesn't have enough money!");
                return false;
            }
            if (transaction.getTo() instanceof CreditCardAccount) {
                System.out.println("You cannot undo this transaction; it was made to a Credit Card Account");
                return false;
            }
            else {
                transaction.getFrom().transferIn(transaction.getAmount());
                transaction.getTo().transferOut(transaction.getAmount());
                transaction.getFrom().getTransactions().remove(transaction);
                transaction.getTo().getTransactions().remove(transaction);
                return true;
            }
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

    private void inputCreateAccount(InputManager m){
        List<Client> clients = new ArrayList<>();
        for(BankMachineUser b:BankMachine.USER_MANAGER.getInstances()){
            if(b instanceof Client){
                clients.add((Client) b);
            }
        }
        System.out.println("Select a client");
        Client client = m.selectItem(clients);
        List<String> accTypes = new ArrayList<>(Arrays.asList(
            "Chequing account", "Credit card account",
            "Line of credit account", "Savings account"
        ));

        while (true){
            String selection = m.selectItem(accTypes);
            if(createAccount(client, selection, LocalDateTime.now())){
                System.out.println("Account created successfully");
                return;
            }
        }
    }

    private void inputCreateClient(InputManager m){
        String name = m.getInput("Enter a name");
        String username = m.getInput("Enter a username");
        String phone = m.getPhone();
        String email = m.getEmail();
        String pwd = m.getInput("Enter a password");
        BankMachine.USER_MANAGER.newClient(name, email, phone, username, pwd);
    }

    @Override
    public void handleInput(InputManager m) {
        System.out.println("Logged in as manager "+getName());
        while (true){
            System.out.println("Select an action");
            List<String> options = new ArrayList<>(Arrays.asList(
                    "Create Account", "Create Client", "Settings", "Exit"
            ));
            String action = m.selectItem(options);
            switch (action){
                case "Exit": return;
                case "Settings": userSettings(m); break;
                case "Create Account": inputCreateAccount(m); break;
                case "Create Client": inputCreateClient(m); break;
                default: break;
            }
        }

    }
}
