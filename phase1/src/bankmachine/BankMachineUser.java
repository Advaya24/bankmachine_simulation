package bankmachine;

import java.io.Serializable;

// managed by: Advaya
public class BankMachineUser implements Serializable {
    private String userName;
    private String password;

    public BankMachineUser(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUsername() { return userName; }
    public String getPassword() { return password; }

    public void setUserName(String new_username) {
        userName = new_username;
    }
    /**Allows the client to change their password if necessary**/
    // (UI should ask for old password to authenticate first, or should the client be logged in to access this? What if a bank manager loses their password?)
    public void setPassword(String new_password){
        password = new_password;
    }
}
