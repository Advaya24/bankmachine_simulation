package bankmachine;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Manages client login authentication for BillManager
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
     * Authenticates login of client or bank manager
     * @param userName username input from client
     * @param password entered password
     * @param type specifies whether it is a Client or BankManager
     * @return the client object if login successful, empty optional otherwise
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
     * Adds new client login information
     * @param newClient Client instance for newClient
     */
    void addClient(Client newClient) {
        clientLoginData.add(newClient);
    }

    /**
     * Adds new bank manager login information
     * @param newBankManager BankManager instance for BankManager
     */
    void addBankManager(BankManager newBankManager) {
        managerLoginData.add(newBankManager);
    }
}
