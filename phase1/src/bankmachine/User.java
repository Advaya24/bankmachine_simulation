package bankmachine;

import bankmachine.account.Account;

import java.util.ArrayList;

/**A User within this system.**/
// Person working on this: Varun
public class User {
    /**Name of this User**/
    private String name;
    /**All the accounts this User has**/
    private ArrayList<Account> users_accounts = new ArrayList<>();
    /**Primary email address of this User**/
    private String email;
    /**This User's Phone Number**/
    private String phone_number;
    /**Username of this User, used for authentication**/
    private String username;
    /**Password of this User, used for authentication**/
    private String password;

    public User(String name, String email, String phone_number, String username, String default_password){
        this.name = name;
        //TODO: Should it be ArrayList<Account> or ArrayList<>?
        this.email = email;
        this.phone_number = phone_number;
        this.username = username;
        this.password = default_password;
    }

    /**All the getters for this class' private variables:**/
    public String getName(){
        return name;
    }
    public ArrayList<Account> getUsersAccounts(){
        return users_accounts;
    }
    public String getEmail(){
        return email;
    }
    public String getPhoneNumber() {
        return phone_number;
    }
    public String getUsername() {
        return username;
    }
    //TODO: Decide if we really want access to this
    public String getPassword(){
        return password;
    }

    /**All the setters for this class' private variables:**/
    public void setName(String new_name){
        name = new_name;
    }
    public void setEmail(String new_email){
        email = new_email;
    }
    public void setPhoneNumber(String new_phone_number) {
        phone_number = new_phone_number;
    }
    //TODO: Decide if we really want to be able to change username
    public void setUsername(String new_username) {
        username = new_username;
    }
    //TODO: Remember to ask twice!
    /**Allows the user to change their password if necessary**/
    public void setPassword(String new_password){
        password = new_password;
    }
}
