package bankmachine;

import bankmachine.FileManager.ObjectFileReader;
import bankmachine.FileManager.ObjectFileWriter;

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
    private final ObjectFileWriter<BankMachineUser> clientWriter = new ObjectFileWriter<>("src/bankmachine/FileManager/clientData.ser");
    private final ObjectFileWriter<BankMachineUser> managerWriter = new ObjectFileWriter<>("src/bankmachine/FileManager/managerData.ser");
    private final ObjectFileReader<BankMachineUser> clientReader = new ObjectFileReader<>("src/bankmachine/FileManager/clientData.ser");
    private final ObjectFileReader<BankMachineUser> managerReader = new ObjectFileReader<>("src/bankmachine/FileManager/managerData.ser");

    Authenticator() {
        try {
            clientLoginData = clientReader.read();
        } catch (Exception e) {
            clientLoginData = new ArrayList<>();
        }
        try {
            managerLoginData = managerReader.read();
        } catch (Exception e) {
            managerLoginData = new ArrayList<>();
        }
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
    public void addClient(Client newClient) {
        clientLoginData.add(newClient);
        clientWriter.write(newClient);
    }

    /**
     * Adds new bank manager login information
     * @param newBankManager BankManager instance for BankManager
     */
    public void addBankManager(BankManager newBankManager) {
        managerLoginData.add(newBankManager);
        managerWriter.write(newBankManager);
    }

    public void clearClientData() {
        clientWriter.clear();
    }

    public void clearManagerData() {
        managerWriter.clear();
    }
}
