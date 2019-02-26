package bankmachine;

import java.util.ArrayList;

/**A User within this system.**/
// Person working on this: Varun
public class User {
    /**Name of this User**/
    String name;
    /**All the accounts this User has**/
    ArrayList<Account> users_accounts;
    /**Primary email address of this User**/
    String email;
    /**This User's Phone Number**/
    String phone_number;
    /**Username of this User, used for authentication**/
    String username;
    /**Password of this User, used for authentication**/
    String password;
}
