package bankmachine.users;

import bankmachine.TrackingFactory;
import bankmachine.account.AccountFactory;
import bankmachine.account.ChequingAccount;
import bankmachine.fileManager.ObjectFileReader;
import bankmachine.fileManager.ObjectFileWriter;
import com.sun.istack.internal.Nullable;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;

public class UserManager extends TrackingFactory<BankMachineUser>
        implements Observer {
    private String rootPath;

    private HashMap<String, BankMachineUser> users = new HashMap<>();
    private AccountFactory accountFactory;

    public UserManager(String path) {
        this.rootPath = path;
        this.loadData();
        this.accountFactory = new AccountFactory(this);
        if (this.users.size() == 0) {
            this.newManager("Brad", "brad@hi.com", "01231", "admin", "admin");
            this.saveData();
        }
    }

    public AccountFactory getAccountFactory() {
        return this.accountFactory;
    }


    /**
     * Creates a new client
     *
     * @param name            the name of the client
     * @param email           the email id of the client
     * @param phoneNumber     the phone number of the client
     * @param username        the username of the client
     * @param defaultPassword the default password for this client's login
     * @return the new client if successful, null otherwise
     */
    @Nullable
    public Client newClient(String name, String email, String phoneNumber, String username, String defaultPassword) {
        Client c = new Client(nextID, name, email, phoneNumber, username, defaultPassword);
        if (users.containsKey(c.getUsername())) {
            return null;
        }
        addInstance(c);
        createPrimaryAccount(c);
        return c;
    }

    /**
     * Creates a new bank manager
     *
     * @param name        the name of the manager
     * @param email       the email id of the manager
     * @param phoneNumber the phone number of the manager
     * @param username    the username of the manager
     * @param password    the password for this manager's login
     */
    @Nullable
    public void newManager(String name, String email, String phoneNumber, String username, String password) {
        BankManager c = new BankManager(nextID, name, email, phoneNumber, username, password);
        if (users.containsKey(c.getUsername())) {
            return;
        }
        addInstance(c);
        createPrimaryAccount(c);
    }

    /**
     * Creates a new bank intern
     *
     * @param name        the name of the manager
     * @param email       the email id of the manager
     * @param phoneNumber the phone number of the manager
     * @param username    the username of the manager
     * @param password    the password for this manager's login
     */
    @Nullable
    public BankEmployee newIntern(String name, String email, String phoneNumber, String username, String password) {
        BankIntern c = new BankIntern(nextID, name, email, phoneNumber, username, password);
        if (users.containsKey(c.getUsername())) {
            return null;
        }
        addInstance(c);
        createPrimaryAccount(c);
        return c;
    }

    public void createPrimaryAccount(Client client) {
        this.accountFactory.newChequingAccount(true, 0, client, LocalDateTime.now());
        ((ChequingAccount) client.getClientsAccounts().get(0)).setPrimary(true);
    }

    /**
     * Update username
     *
     * @param o   BankMachineUser
     * @param arg the old username
     */
    @Override
    public void update(Observable o, Object arg) {
        String oldName = (String) arg;
        BankMachineUser user = (BankMachineUser) o;
        users.remove(oldName);
        users.put(user.getUsername(), user);
    }

    public List<BankMachineUser> getInstances() {
        return new ArrayList<>(users.values());
    }

    /**
     * See superclass docs
     *
     * @param instances the instances to add
     */
    public void extend(List<BankMachineUser> instances) {
        for (BankMachineUser i : instances) {
            users.put(i.getUsername(), i);
        }
        int max = 0;
        for (BankMachineUser t : this.getInstances()) {
            if (t.getID() > max) {
                max = t.getID();
            }
        }
        this.nextID = max + 1;
    }

    /**
     * Add an instance of user to the thing
     *
     * @param user the user instance to be inserted
     */
    @Override
    public void addInstance(BankMachineUser user) {
        users.put(user.getUsername(), user);
        this.nextID++;
    }

    public HashMap<String, BankMachineUser> getMap() {
        return users;
    }

    /**
     * Maps a function over all users
     *
     * @param function the function to map
     */
    public void runOnAll(Function<BankMachineUser, Void> function) {
        for (BankMachineUser user : users.values()) {
            function.apply(user);
        }
    }

    /**
     * Get users by username
     *
     * @param username the user's username
     * @return the bankmachine user with the username
     */
    @Nullable
    public BankMachineUser get(String username) {
        return users.get(username);
    }

    /**
     * Matches userName to password for login
     *
     * @param username for this user
     * @param password for this user
     * @return The user if login successful, null otherwise
     */
    @Nullable
    public BankMachineUser authenticate(String username, String password) {
        BankMachineUser user = users.get(username);
        if (user == null || !user.getPassword().equals(password)) {
            return null;
        }
        return user;
    }

    /**
     * Loads data from a serialized file
     */
    public void loadData() {
        List<BankMachineUser> clients = loadFile();
        this.extend(clients);
    }

    /**
     * Reads data from the file
     *
     * @return the array list of objects stored in the file
     */
    public List<BankMachineUser> loadFile() {
        ObjectFileReader<BankMachineUser> reader = new ObjectFileReader<>(rootPath);
        ArrayList<BankMachineUser> objects;
        try {
            objects = reader.read();
        } catch (RuntimeException e) {
            objects = new ArrayList<>();
        }
        return objects;
    }

    /**
     * Saves data to the designated file
     */
    public void saveData() {
        ObjectFileWriter<BankMachineUser> writer = new ObjectFileWriter<>(rootPath);
        writer.clear();
        writer.writeAll(this.getInstances());
    }

    /**
     * Get all BankManagers
     */
    public List<BankManager> getBankManagers() {
        List<BankManager> bankManagers = new ArrayList<>();
        for (BankMachineUser c : getInstances()) {
            if (c instanceof BankManager) {
                bankManagers.add((BankManager) c);
            }
        }
        return bankManagers;
    }

    /**
     * Get all Clients
     */
    public List<Client> getClients() {
        List<Client> bankManagers = new ArrayList<>();
        for (BankMachineUser c : getInstances()) {
            if (c instanceof Client) {
                bankManagers.add((Client) c);
            }
        }
        return bankManagers;
    }
}
