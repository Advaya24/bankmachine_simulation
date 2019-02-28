package bankmachine;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Manages user login authentication for BankMachine
 */
// Managed by: Advaya
public class Authenticator {
    private ArrayList<Loginable> userLoginData;
    private ArrayList<Loginable> managerLoginData;

    Authenticator() {
        userLoginData = new ArrayList<>();
        managerLoginData = new ArrayList<>();
    }

    /**
     * Authenticates login of user or bank manager
     * @param userName username input from user
     * @param password entered password
     * @param type specifies whether it is a User or BankManager
     * @return the user object if login successful, empty optional otherwise
     */
    Optional<Loginable> authenticate(String userName, String password, LoginType type) {
        ArrayList<Loginable> dataToCheck;

        switch (type) {
            case USER_LOGIN: dataToCheck = userLoginData; break;
            case BANK_MANAGER_LOGIN: dataToCheck = managerLoginData; break;
            default: dataToCheck = new ArrayList<>();
        }

        for (Loginable tempLoginable: dataToCheck) {
            if (tempLoginable.getUsername().equals(userName)) {
                if (tempLoginable.getPassword().equals(password)) {
                    return Optional.of(tempLoginable);
                } else {
                    return Optional.empty();
                }
            }
        }

        return Optional.empty();
    }

    /**
     * Adds new user login information
     * @param newUser User instance for newUser
     */
    void addUser(User newUser) {
        userLoginData.add(newUser);
    }

    /**
     * Adds new bank manager login information
     * @param newBankManager BankManager instance for BankManager
     */
    void addBankManager(BankManager newBankManager) {
        managerLoginData.add(newBankManager);
    }
}
