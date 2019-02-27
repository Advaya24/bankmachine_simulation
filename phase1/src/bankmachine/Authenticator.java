package bankmachine;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Manages user login authentication for BankMachine
 */
// Managed by: Advaya
public class Authenticator {
//    private HashMap<String, Pair<String, Loginable>> userLoginData;
//    private HashMap<String, Pair<String, Loginable>> managerLoginData;
    private ArrayList<Loginable> userLoginData;
    private ArrayList<Loginable> managerLoginData;

    Authenticator() {
//        userLoginData = new HashMap<>();
//        managerLoginData = new HashMap<>();
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
//        HashMap<String, Pair<String, Loginable>> dataToCheck;
        ArrayList<Loginable> dataToCheck;
//        if (type == LoginType.USERLOGIN) {
//            dataToCheck = userLoginData;
//        } else {
//            dataToCheck = managerLoginData;
//        }
        switch (type) {
            case USERLOGIN: dataToCheck = userLoginData; break;
            case BANKMANAGERLOGIN: dataToCheck = managerLoginData; break;
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
//     * @param userName username of newUser
//     * @param password password of newUser
     * @param newUser User instance for newUser
     */
    void addUser(User newUser) {
//        userLoginData.put(userName, new Pair<>(password, newUser));
        userLoginData.add(newUser);
    }

    /**
     * Adds new bank manager login information
//     * @param userName userName of newBankManager
//     * @param password password of newBankManager
     * @param newBankManager BankManager instance for BankManager
     */
    void addBankManager(BankManager newBankManager) {
        managerLoginData.add(newBankManager);
    }
}
