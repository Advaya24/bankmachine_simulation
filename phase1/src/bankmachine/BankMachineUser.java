package bankmachine;

import bankmachine.Exceptions.NameTakenException;
import java.util.Observable;

import java.io.Serializable;

// managed by: Advaya
public class BankMachineUser extends Observable implements Serializable, Identifiable{
    private String userName;
    private String password;
    private int id;

    public BankMachineUser(int id, String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.id = id;
    }

    public String getUsername() { return userName; }
    public String getPassword() { return password; }
    public int getID(){ return id; }
    public void setUserName(String new_username) {
        String oldName = userName;
        userName = new_username;
        notifyObservers(oldName);
    }
    /**Allows the client to change their password if necessary**/
    // (UI should ask for old password to authenticate first, or should the client be logged in to access this? What if a bank manager loses their password?)
    public void setPassword(String new_password){
        password = new_password;
    }
}
