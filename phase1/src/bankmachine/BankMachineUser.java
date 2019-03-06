package bankmachine;

import java.io.Serializable;

// managed by: Advaya
public class BankMachineUser implements Serializable {
    /** Username of the BankMachineUser */
    private String userName;
    /** Password of the BankMachineUser */
    private String password;
    /** static variable used to get ids for all BankMachineUsers */
    static int num_users = 0;
    /** The unique ID of this BankMachineUser */
    private int id;

    public BankMachineUser(String userName, String password) {
        this.userName = userName;
        this.password = password;
        num_users++;
        id = num_users;
    }
    /**All getters for the various attributes */
    public String getUsername() { return userName; }
    public String getPassword() { return password; }
    public int getID(){ return id; }
    public void setUserName(String new_username) {
        userName = new_username;
    }
    /**Allows the client to change their password if necessary**/
    // (UI should ask for old password to authenticate first, or should the client be logged in to access this? What if a bank manager loses their password?)
    public void setPassword(String new_password){
        password = new_password;
    }
}
