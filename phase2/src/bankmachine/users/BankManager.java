package bankmachine.users;

import bankmachine.BankMachine;

/**
 * A Bank Manager within the system
 **/
//Working on: Varun
public class BankManager extends BankEmployee {


    public BankManager(int id, String name, String email, String phoneNumber, String username, String default_password) {
        super(id, name, email, phoneNumber, username, default_password);
        this.salary = 5280;
    }

    /**
     * Creates a new Client for the Bank
     *
     * @param name             name of the client
     * @param email            email address of the client
     * @param phoneNumber      phone number of the client
     * @param username         username of the client
     * @param default_password default password of the client
     */
    public Client createClient(String name, String email, String phoneNumber, String username, String default_password) {
        return BankMachine.USER_MANAGER.newClient(name, email, phoneNumber, username, default_password);
    }


    public String toString() {
        return "Manager " + getName() + " (" + getUsername() + ")";
    }


}
