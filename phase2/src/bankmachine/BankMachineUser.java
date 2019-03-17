package bankmachine;

import java.util.Observable;

import java.io.Serializable;

// managed by: Advaya
public abstract class BankMachineUser extends Observable implements Serializable, Identifiable {
    /**
     * Name of this BankMachineUser
     */
    private String name;
    /**
     * Primary email address of this Client
     **/
    private String email;
    /**
     * This Client's Phone Number
     **/
    private String phoneNumber;
    /**
     * Username of the BankMachineUser
     */
    private String userName;
    /**
     * Password of the BankMachineUser
     */
    private String password;
    /**
     * Unique ID of this BankMachineUser
     */
    private int id;

    public BankMachineUser(int id, String name, String email, String phoneNumber, String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    /**
     * All getters for the various attributes
     */
    public String getName() {
        return name;
    }

    public String getUsername() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public int getID() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setUserName(String new_username) {
        String oldName = userName;
        userName = new_username;
        notifyObservers(oldName);
    }

    public void setName(String new_name) {
        name = new_name;
    }

    public void setEmail(String new_email) {
        email = new_email;
    }

    public void setPhoneNumber(String new_phoneNumber) {
        phoneNumber = new_phoneNumber;
    }

    /**
     * Allows the client to change their password if necessary
     **/
    public void setPassword(String new_password) {
        password = new_password;
    }

}
