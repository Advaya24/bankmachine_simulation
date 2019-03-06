package bankmachine;

import bankmachine.Exceptions.NameTakenException;

import java.io.Serializable;
import java.util.HashMap;

// managed by: Advaya
public class BankMachineUser implements Serializable {
    private String userName;
    private String password;
    private int id;
    public static HashMap<String, BankMachineUser> users;

    public BankMachineUser(String userName, String password) throws NameTakenException {
        if(users.containsKey(userName)){
            throw new NameTakenException();
        }
        this.userName = userName;
        this.password = password;
        id = getNumUsers();
    }
    public int getNumUsers(){
        return users.size();
    }
    public String getUsername() { return userName; }
    public String getPassword() { return password; }
    public int getID(){ return id; }
    public void setUserName(String new_username) {
        users.remove(userName);
        userName = new_username;
        users.put(userName, this);
    }
    /**Allows the client to change their password if necessary**/
    // (UI should ask for old password to authenticate first, or should the client be logged in to access this? What if a bank manager loses their password?)
    public void setPassword(String new_password){
        password = new_password;
    }
}
