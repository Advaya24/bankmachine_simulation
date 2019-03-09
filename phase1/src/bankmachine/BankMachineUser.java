package bankmachine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observable;

import java.io.Serializable;

// managed by: Advaya
public abstract class BankMachineUser extends Observable implements Serializable, Identifiable, Inputtable {
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
    // (UI should ask for old password to authenticate first, or should the client be logged in to access this? What if a bank manager loses their password?)
    public void setPassword(String new_password) {
        password = new_password;
    }

    /**
     * Presents setting options for the user
     *
     * @param m the input manager that handles this
     */
    protected void userSettings(InputManager m) {
        List<String> options = new ArrayList<>(Arrays.asList(
                "Phone Number", "Email", "Password", "Exit"
        ));
        System.out.println("Select an option");
        String action = m.selectItem(options);
        if (action.equals("Exit")) {
            return;
        }
        String value;
        switch (action) {
            case "Phone Number":
                value = m.getPhone();
                setPhoneNumber(value);
                break;
            case "Email":
                value = m.getEmail();
                setEmail(value);
                break;
            case "Password":
                value = m.getInput("Enter a new password");
                setPassword(value);
                break;
            default:
                return;
        }
        System.out.println("Set new " + action + " to " + value);
    }
}
