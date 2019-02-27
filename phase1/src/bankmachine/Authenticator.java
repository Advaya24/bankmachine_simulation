package bankmachine;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.Optional;

/**
 * Manages user login authentication for BankMachine
 */
public class Authenticator {
    private HashMap<String, Pair<String, User>> loginData;

    Authenticator() {
        loginData = new HashMap<>();
    }

    /**
    * Authenticates login of user
    * @param userName username input from user
    * @param password entered password
    * @return the user object if login successful, empty optional otherwise
     */
    Optional<User> authenticate(String userName, String password) {
        for (String tempUserName: this.loginData.keySet()) {
            if (tempUserName.equals(userName)) {
                if (loginData.get(userName).getKey().equals(password)) {
                    return Optional.of(loginData.get(userName).getValue());
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
        loginData.put(userName, new Pair<>(password, newUser));
    }
}
