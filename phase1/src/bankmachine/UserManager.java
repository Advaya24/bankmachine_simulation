package bankmachine;

import bankmachine.fileManager.ObjectFileReader;
import bankmachine.fileManager.ObjectFileWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Function;

// Managed by: Advaya

/**
 * Manages client login authentication for BillManager
 *
 * @param <T> a BankMachineUser type
 */
public class UserManager<T extends BankMachineUser> {
    /* List of user instances registered for login */
    private ArrayList<T> loginData;
    /* HashMap of user instances */
    private HashMap<Integer, T> loginHashMap;
    /* File writer for this authenticator */
    private ObjectFileWriter<T> writer;

    public UserManager(String fileName) {
        writer = new ObjectFileWriter<>(fileName);
        ObjectFileReader<T> reader = new ObjectFileReader<>(fileName);
        try {
            loginData = reader.read();
        } catch (Exception e) {
            loginData = new ArrayList<>();
        }
        loginHashMap = new HashMap<>();
        for (T user : loginData) {
            loginHashMap.put(user.getID(), user);
        }
    }

    /**
     * Matches userName to password for login
     *
     * @param userName for this user
     * @param password for this user
     * @return Optional containing the required user of type T if login successful, empty Optional otherwise
     */
    public Optional<T> authenticate(String userName, String password) {
        for (T user : loginData) {
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
     *
     * @param newUser user to add
     */
    public void add(T newUser) {
        loginData.add(newUser);
        loginHashMap.put(newUser.getID(), newUser);
        writer.write(newUser);
    }

    /**
     * Add all users from an array list of users
     *
     * @param newUsersArrayList array list of users to add
     */
    public void addAll(ArrayList<T> newUsersArrayList) {
        loginData.addAll(newUsersArrayList);
        for (T user : newUsersArrayList) loginHashMap.put(user.getID(), user);
        writer.writeAll(newUsersArrayList);
    }

    /**
     * Get user corresponding to userName
     *
     * @param userName for the required user
     * @return Optional containing the required user of type T if it exists, empty Optional otherwise
     */
    public Optional<T> get(String userName) {
        for (T user : loginData) {
            if (user.getUsername().equals(userName)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    /**
     * Get user corresponding to ID.
     *
     * @param ID of the user
     * @return Optional containing the required user of type T if it exists, empty Optional otherwise
     */
    public Optional<T> get(int ID) {
        return Optional.of(loginHashMap.get(ID));
    }

    /**
     * Clears all login data for this type.
     */
    public void clearData() {
        loginData = new ArrayList<>();
        loginHashMap = new HashMap<>();
        writer.clear();
    }

    /**
     * Returns all the stored users of this type.
     *
     * @return an array list of users of type T
     */
    public ArrayList<T> getAll() {
        return loginData;
    }

    /**
     * Overwrite currently stored data with given data
     *
     * @param newLoginData an array list of users of type T to be stored in the file
     */
    public void overwrite(ArrayList<T> newLoginData) {
        clearData();
        addAll(newLoginData);
    }

    /**
     * Updates file with changes made to elements of loginData. MUST CALL EVERY TIME A CHANGE IS MADE.
     */
    public void updateFile() {
        writer.clear();
        writer.writeAll(loginData);
    }

    /**
     * Run given function on all users stored.
     *
     * @param function the function to be applied to all the users.
     */
    public void runOnAll(Function<T, Void> function) {
        for (T user : loginData) {
            function.apply(user);
        }
        updateFile();
    }

    /**
     * Deletes given object.
     * @param obj to delete
     */
    public void delete(T obj) {
        loginData.remove(obj);
        loginHashMap.remove(obj.getID());
        updateFile();
    }
}
