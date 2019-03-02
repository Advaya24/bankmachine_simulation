package bankmachine;

import bankmachine.account.Account;
import java.util.Date;
import java.util.ArrayList;

/**A Client within this system.**/
// Person working on this: Varun
public class Client extends BankMachineUser {
    /**Name of this Client**/
    private String name;
    /**All the accounts this Client has**/
    private ArrayList<Account> usersAccounts = new ArrayList<>(); // Instead of initializing here, do this same thing in the constructor maybe? ~ Advaya
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
    public ArrayList<Account> getUsersAccounts(){
        return usersAccounts;
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
        usersAccounts.add(newAccount);
    }


    //TODO: Figure out whether most recent transaction is outgoing, both ways, or what
    /**
     * Returns the most recent transaction across all accounts .
     * @return the most recent transaction of this client
     */
    //TODO: Finish this
    public Transaction mostRecentTrasaction(){
        if (usersAccounts.size()==0)
        {
            return null;
        }
        else {
            Account firstAccount = usersAccounts.get(0);
            Transaction recentTransaction = firstAccount.getTransactions().get(firstAccount.getTransactions().size() - 1);
            for (Account a : usersAccounts) {
                Date mostRecentFromAccount = a.getTransactions().get(a.getTransactions().size() - 1).getDate();
                if (mostRecentFromAccount.compareTo(recentTransaction.getDate()) > 0) {
                    recentTransaction = a.getTransactions().get(a.getTransactions().size() - 1);
                }
            }
            return recentTransaction;
        }
    }

    //TODO: method to request BankManager to create a new account
}
