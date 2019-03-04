package bankmachine;

import bankmachine.FileManager.ObjectFileReader;
import bankmachine.FileManager.ObjectFileWriter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/**
 * Manages client login authentication for BillManager
 * @param <T> a BankMachineUser type
 */
// Managed by: Advaya
public class Authenticator<T extends BankMachineUser> {
    /* List of Client instances registered for login */
    private ArrayList<T> loginData;
    /* File writer for this authenticator */
    private ObjectFileWriter<T> writer;

    public Authenticator(String fileName) {
        writer = new ObjectFileWriter<>(fileName);
        ObjectFileReader<T> reader = new ObjectFileReader<>(fileName);
        try {
            loginData = reader.read();
        } catch (Exception e) {
            loginData = new ArrayList<>();
        }
    }

    /**
     * Matches userName to password for login
     * @param userName for this user
     * @param password for this user
     * @return Optional containing the required user of type T if login successful, empty Optional otherwise
     */
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

    /**
     * Add a single user
     * @param newUser user to add
     */
    public void add(T newUser) {
        loginData.add(newUser);
        writer.write(newUser);
    }

    /**
     * Add all users from an array list of users
     * @param newUsersArrayList array list of users to add
     */
    public void addAll(ArrayList<T> newUsersArrayList) {
        loginData.addAll(newUsersArrayList);
        writer.writeAll(newUsersArrayList);
    }

    /**
     * Get user corresponding to userName
     * @param userName for the required user
     * @return Optional containing the required user of type T if it exists, empty Optional otherwise
     */
    public Optional<T> get(String userName) {
        for (T user: loginData) {
            if (user.getUsername().equals(userName)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    /**
     * Clears all loginData for this type.
     */
    public void clearData() {
        loginData = new ArrayList<>();
        writer.clear();
    }
}
