package bankmachine;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Manages user login authentication for BankMachine
 */
// Managed by: Advaya
public class Authenticator {
    /* List of Client instances registered for login */
    private ArrayList<BankMachineUser> clientLoginData;
    /* List of BankManager instances registered for login*/
    private ArrayList<BankMachineUser> managerLoginData;

    Authenticator() {
        clientLoginData = new ArrayList<>();
        managerLoginData = new ArrayList<>();
    }

    /**
     * Authenticates login of user or bank manager
     * @param userName username input from user
     * @param password entered password
     * @param type specifies whether it is a User or BankManager
     * @return the user object if login successful, empty optional otherwise
     */
    Optional<BankMachineUser> authenticate(String userName, String password, LoginType type) {
        ArrayList<BankMachineUser> dataToCheck;

        switch (type) {
            case USER_LOGIN: dataToCheck = clientLoginData; break;
            case BANK_MANAGER_LOGIN: dataToCheck = managerLoginData; break;
            default: dataToCheck = new ArrayList<>();
        }

        for (BankMachineUser tempBankMachineUser : dataToCheck) {
            if (tempBankMachineUser.getUsername().equals(userName)) {
                if (tempBankMachineUser.getPassword().equals(password)) {
                    return Optional.of(tempBankMachineUser);
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
        clientLoginData.add(newUser);
    }

    /**
     * Adds new bank manager login information
     * @param newBankManager BankManager instance for BankManager
     */
    void addBankManager(BankManager newBankManager) {
        managerLoginData.add(newBankManager);
    }
}
