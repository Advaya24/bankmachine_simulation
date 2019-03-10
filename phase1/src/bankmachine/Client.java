package bankmachine;

import bankmachine.account.*;
import com.sun.istack.internal.Nullable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**A Client within this system.**/
// Person working on this: Varun
public class Client extends BankMachineUser {
    /**All the accounts this Client has**/
    private ArrayList<Account> clientsAccounts = new ArrayList<>();
    public Client(int id, String name, String email, String phoneNumber, String username, String default_password) {
        super(id,name,email, phoneNumber, username, default_password);
    }

    /**All the getters for this class' private variables:**/

    public ArrayList<Account> getClientsAccounts(){
        return clientsAccounts;
    }

    /**
     * Adds a newly created account for this client.
     * @param newAccount the Account just created for this client
     */
    public void addAccount(Account newAccount){
        clientsAccounts.add(newAccount);
        if (!(newAccount instanceof ChequingAccount)){
            return;
        }
        for (Account a : clientsAccounts){
            if (a instanceof ChequingAccount && ((ChequingAccount) a).isPrimary()){
                return;
            }
        }
        for (Account a : clientsAccounts){
            if (a instanceof ChequingAccount){
                ((ChequingAccount) a).setPrimary(true);
                return;
            }
        }

        //AccountManager.addAccount(newAccount);
    }


    /**
     * Returns the most recent transaction across all accounts .
     * @return the most recent transaction of this client
     */
    @Nullable
    public Transaction mostRecentTransaction(Account a1){
        if (!getClientsAccounts().contains(a1))
        {
            return null;
        }
        else {
            return a1.getTransactions().get(a1.getTransactions().size() - 1);
        }
    }

    /**
     * Returns the creation date of an account, if this user owns that account
     * @param account the account that we want the creation date of.
     * @return the creation date of the account parameter.
     */
    @Nullable
    public LocalDateTime getAccountCreationDate(Account account){
        if (clientsAccounts.contains(account)){
            return account.getCreationDate();
        }
        else{
            return null;
        }
    }

    /**
     * Returns the net total of the client
     * @return the net total of the client
     */
    public double getNetTotal(){
        double debt=0;
        double asset =0;
        for (Account a: clientsAccounts){
            if(a instanceof DebtAccount){
                debt+=a.getDoubleBalance();
            }
            else if(a instanceof AssetAccount){
                asset+=a.getDoubleBalance();
            }
        }
        return asset-debt;
    }

    /**
     * Prints the summary of all Accounts this Client has.
     */
    public void printAccountSummary(){
        System.out.println("Account summary for username: " + getUsername());
        for(Account a: clientsAccounts){
            System.out.println(a);
        }
        System.out.println("Net total: "+getNetTotal());
    }
    @Override
    public String toString(){
        return "Client "+getName();
    }

    @SuppressWarnings("Duplicates")
    public void newAccountCreationInput(InputManager m){
        System.out.println("Choose a type of account:");
        List<String> accTypes = new ArrayList<>(Arrays.asList(
                "Chequing account", "Credit card account",
                "Line of credit account", "Savings account", "Cancel"
        ));
        String selection = m.selectItem(accTypes);
        if (selection.equals("Cancel")){
            return;
        }
        BankManager manager = BankMachine.USER_MANAGER.getBankManagers().get(0);
        manager.addCreationRequest(getUsername() + " requests a " + selection);
    }

    @Override
    public void handleInput(InputManager m){
        System.out.println("Welcome, "+getName()+"!");
        while (true){
            System.out.println("Select an action");
            List<String> options = new ArrayList<>(Arrays.asList(
                    "Accounts","Request Creation Of A New Account", "Settings", "Exit"
            ));
            String action = m.selectItem(options);
            switch (action){
                case "Exit": return;
                case "Request Creation Of A New Account":
                    newAccountCreationInput(m);
                    break;
                case "Settings": userSettings(m); break;
                case "Accounts":
                    printAccountSummary();
                    Account account = m.selectItem(getClientsAccounts());
                    if(account==null){
                        break;
                    }
                    account.handleInput(m);
                    break;
                default:
                    break;
            }
        }
    }
    public Account getPrimaryAccount(){
        for (Account a:this.clientsAccounts){
            if (a instanceof ChequingAccount && ((ChequingAccount) a).isPrimary()){
                return a;
            }
        }
        if(this.clientsAccounts.size() == 0){
            return null;
        }
        return this.clientsAccounts.get(0);
    }


}
