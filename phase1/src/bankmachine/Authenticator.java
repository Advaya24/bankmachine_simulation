package bankmachine;

import bankmachine.FileManager.ObjectFileReader;
import bankmachine.FileManager.ObjectFileWriter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/**
 * Manages client login authentication for BillManager
 */
// Managed by: Advaya
public class Authenticator<T extends BankMachineUser> {
    /* List of Client instances registered for login */
//    private ArrayList<BankMachineUser> clientLoginData;
//    /* List of BankManager instances registered for login*/
//    private ArrayList<BankMachineUser> managerLoginData;
//    private final ObjectFileWriter<BankMachineUser> clientWriter = new ObjectFileWriter<>("src/bankmachine/FileManager/clientData.ser");
//    private final ObjectFileWriter<BankMachineUser> managerWriter = new ObjectFileWriter<>("src/bankmachine/FileManager/managerData.ser");
//    private final ObjectFileReader<BankMachineUser> clientReader = new ObjectFileReader<>("src/bankmachine/FileManager/clientData.ser");
//    private final ObjectFileReader<BankMachineUser> managerReader = new ObjectFileReader<>("src/bankmachine/FileManager/managerData.ser");
    private ArrayList<T> loginData;
    private ObjectFileWriter<T> writer;

    public Authenticator(String fileName) {
        writer = new ObjectFileWriter<>(fileName);
        ObjectFileReader<T> reader = new ObjectFileReader<>(fileName);
        try {
            loginData = reader.read();
        } catch (Exception e) {
            loginData = new ArrayList<>();
        }
//        try {
//            managerLoginData = managerReader.read();
//        } catch (Exception e) {
//            managerLoginData = new ArrayList<>();
//        }
    }

//    /**
//     * Authenticates login of client or bank manager
//     * @param userName username input from client
//     * @param password entered password
//     * @param type specifies whether it is a Client or BankManager
//     * @return the client object if login successful, empty optional otherwise
//     */
//    public Optional<BankMachineUser> authenticate(String userName, String password, LoginType type) {
//        ArrayList<BankMachineUser> dataToCheck;
//
//        switch (type) {
//            case CLIENT_LOGIN: dataToCheck = clientLoginData; break;
//            case BANK_MANAGER_LOGIN: dataToCheck = managerLoginData; break;
//            default: dataToCheck = new ArrayList<>();
//        }
//
//        for (BankMachineUser tempBankMachineUser : dataToCheck) {
//            if (tempBankMachineUser.getUsername().equals(userName)) {
//                if (tempBankMachineUser.getPassword().equals(password)) {
//                    return Optional.of(tempBankMachineUser);
//                } else {
//                    return Optional.empty();
//                }
//            }
//        }
//
//        return Optional.empty();
//    }

    public Optional<T> authenticate(String userName, String password) {
        for (T user: loginData) {
            if (user.getUsername().equals(userName)) {
                if (user.getPassword().equals(password)) {
                    return Optional.of(user);
                } else {
                    return Optional.empty();
                }
            }
        }
        return Optional.empty();
    }
//    /**
//     * Adds new client login information
//     * @param newClient Client instance for newClient
//     */
//    public void addClient(Client newClient) {
//        clientLoginData.add(newClient);
//        clientWriter.write(newClient);
//    }
//
//    /**
//     * Adds new bank manager login information
//     * @param newBankManager BankManager instance for BankManager
//     */
//    public void addBankManager(BankManager newBankManager) {
//        managerLoginData.add(newBankManager);
//        managerWriter.write(newBankManager);
//    }
//
//    public void clearClientData() {
//        clientWriter.clear();
//    }
//
//    public void clearManagerData() {
//        managerWriter.clear();
//    }
    public void add(T newUser) {
        loginData.add(newUser);
        writer.write(newUser);
    }

    public void addAll(ArrayList<T> newUsersArrayList) {
        loginData.addAll(newUsersArrayList);
        writer.writeAll(newUsersArrayList);
    }

    public Optional<T> get(String userName) {
        for (T user: loginData) {
            if (user.getUsername().equals(userName)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public void clearData() {
        loginData = new ArrayList<>();
        writer.clear();
    }
}
