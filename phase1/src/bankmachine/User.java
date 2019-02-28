package bankmachine;

import bankmachine.account.Account;
import java.util.Date;
import java.util.ArrayList;

/**A User within this system.**/
// Person working on this: Varun
public class User extends Loginable {
    /**Name of this User**/
    private String name;
    /**All the accounts this User has**/
    private ArrayList<Account> usersAccounts = new ArrayList<>(); // Instead of initializing here, do this same thing in the constructor maybe? ~ Advaya
    /**Primary email address of this User**/
    private String email;
    /**This User's Phone Number**/
    private String phoneNumber;
//    /**Username of this User, used for authentication**/
//    private String username;
//    /**Password of this User, used for authentication**/
//    private String password;

    public User(String name, String email, String phoneNumber, String username, String default_password){
        super(username, default_password);
        this.name = name;
        //TODO: Should it be ArrayList<Account> or ArrayList<>?
        this.email = email;
        this.phoneNumber = phoneNumber;
//        this.username = username;
//        this.password = default_password;

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
//    public String getUsername() {
//        return username;
//    }
//    public String getPassword(){
//        return password;
//    }

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
//    //TODO: Decide if we really want to be able to change username
//    public void setUsername(String new_username) {
//        username = new_username;
//    }
//    //TODO: Remember to ask twice!
//    /**Allows the user to change their password if necessary**/
//    public void setPassword(String new_password){
//        password = new_password;
//    }
    //TODO: Figure out whether most recent transaction is outgoing, both ways, or what
    /**
     * Returns the most recent transaction across all accounts .
     * @return the most recent transaction of this user
     */
    //TODO: Finish this
    public Transaction mostRecentTrasaction(){
        //TODO: Account for a case where the user has no accounts
        Account firstAccount = usersAccounts.get(0);
        Transaction recentTransaction = firstAccount.getTransactions().get(firstAccount.getTransactions().size()-1);
        for(Account a:usersAccounts) {
            Date mostRecentFromAccount =a.getTransactions().get(a.getTransactions().size()-1).getDate();
            if(mostRecentFromAccount.compareTo(recentTransaction.getDate())>0){
                recentTransaction = a.getTransactions().get(a.getTransactions().size()-1);
            }
        }
        return recentTransaction;
    }
}
