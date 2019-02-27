package bankmachine;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.Optional;

/**
 * Manages user login authentication for BankMachine
 */
// Managed by: Advaya
public class Authenticator {
    private HashMap<String, Pair<String, Loginable>> userLoginData;
    private HashMap<String, Pair<String, Loginable>> managerLoginData;

    Authenticator() {
        userLoginData = new HashMap<>();
        managerLoginData = new HashMap<>();
    }

    /**
    * Authenticates login of user
    * @param userName username input from user
    * @param password entered password
    * @return the user object if login successful, empty optional otherwise
     */
    Optional<Loginable> authenticate(String userName, String password, LoginType type) {
        HashMap<String, Pair<String, Loginable>> dataToCheck;
        if (type == LoginType.USERLOGIN) {
            dataToCheck = userLoginData;
        } else {
            dataToCheck = managerLoginData;
        }
        for (String tempUserName: dataToCheck.keySet()) {
            if (tempUserName.equals(userName)) {
                if (dataToCheck.get(userName).getKey().equals(password)) {
                    return Optional.of(dataToCheck.get(userName).getValue());
                } else {
                    return Optional.empty();
                }
            }
        }

        return Optional.empty();
    }

    /**
     * Adds new user login information
     * @param userName username of newUser
     * @param password password of newUser
     * @param newUser User instance for newUser
     */
    void addUser(String userName, String password, User newUser) {
        userLoginData.put(userName, new Pair<>(password, newUser));
    }

    void addBankManager(String userName, String password, BankManager newBankManager) {
        managerLoginData.put(userName, new Pair<>(password, newBankManager));
    }
}
