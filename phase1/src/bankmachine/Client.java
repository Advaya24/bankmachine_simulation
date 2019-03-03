package bankmachine;

import bankmachine.account.*;

import java.util.Date;
import java.util.ArrayList;

/**A Client within this system.**/
// Person working on this: Varun
public class Client extends BankMachineUser {
    /**Name of this Client**/
    private String name;
    /**All the accounts this Client has**/
    private ArrayList<Account> clientsAccounts = new ArrayList<>(); // Instead of initializing here, do this same thing in the constructor maybe? ~ Advaya
    /**Primary email address of this Client**/
    private String email;
    /**This Client's Phone Number**/
    private String phoneNumber;

    public Client(String name, String email, String phoneNumber, String username, String default_password){
        super(username, default_password);
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;

    }

    /**All the getters for this class' private variables:**/
    public String getName(){
        return name;
    }
    public ArrayList<Account> getClientsAccounts(){
        return clientsAccounts;
    }
    public String getEmail(){
        return email;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**All the setters for this class' private variables:**/
    public void setName(String new_name){
        name = new_name;
    }
    public void setEmail(String new_email){
        email = new_email;
    }
    public void setPhoneNumber(String new_phoneNumber) {
        phoneNumber = new_phoneNumber;
    }

    /**
     * Adds a newly created account for this client.
     * @param newAccount the Account just created for this client
     */
    public void addAccount(Account newAccount){
        clientsAccounts.add(newAccount);
    }


    //TODO: Figure out whether most recent transaction is outgoing, both ways, or what
    /**
     * Returns the most recent transaction across all accounts .
     * @return the most recent transaction of this client
     */
    public Transaction mostRecentTransaction(){
        if (clientsAccounts.size()==0)
        {
            return null;
        }
        else {
            Account firstAccount = clientsAccounts.get(0);
            Transaction recentTransaction = firstAccount.getTransactions().get(firstAccount.getTransactions().size() - 1);
            for (Account a : clientsAccounts) {
                Date mostRecentFromAccount = a.getTransactions().get(a.getTransactions().size() - 1).getDate();
                if (mostRecentFromAccount.compareTo(recentTransaction.getDate()) > 0) {
                    recentTransaction = a.getTransactions().get(a.getTransactions().size() - 1);
                }
            }
            return recentTransaction;
        }
    }

    //TODO: method to request BankManager to create a new account

    /**
     * Returns the creation date of an account, if this user owns that account
     * @param account the account that we want the creation date of.
     * @return the creation date of the account parameter.
     */
    public Date getAccountCreationDate(Account account){
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
                debt+=a.getBalance();
            }
            else if(a instanceof AssetAccount){
                asset+=a.getBalance();
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

}
