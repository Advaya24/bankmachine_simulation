package bankmachine;

import java.util.HashMap;
import java.util.function.Function;


// Managed by: Advaya

/**
 * Manages client login authentication for BillManager
 */
public class Authenticator {
    /* List of user instances registered for login */
    private HashMap<String, BankMachineUser> users;

    public Authenticator(HashMap<String, BankMachineUser> users) {
        this.users = users;
    }
    /**
     * Matches userName to password for login
     *
     * @param username for this user
     * @param password for this user
     * @return Optional containing the required user of type T if login successful, empty Optional otherwise
     */
    public BankMachineUser authenticate(String username, String password) {
        BankMachineUser user = users.get(username);
        if (user == null || !user.getPassword().equals(password)){
            return null;
        }
        return user;
    }

    public void runOnAll(Function<BankMachineUser, Void> function) {
        for (BankMachineUser user : users.values()) {
            function.apply(user);
        }
    }

    public BankMachineUser get(String username){
        return users.get(username);
    }

}
