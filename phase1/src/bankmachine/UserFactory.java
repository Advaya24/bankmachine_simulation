package bankmachine;

import java.util.*;
import java.util.function.Function;

// Rename to UserManager???
public class UserFactory extends TrackingFactory<BankMachineUser>
implements Observer{
    private HashMap<String, BankMachineUser> users = new HashMap<>();
    public Client newClient(String name, String email, String phoneNumber, String username, String default_password){
        Client c = new Client(nextID, name, email, phoneNumber, username, default_password);
        if(users.containsKey(c.getUsername())){
            return null;
        }
        addInstance(c);
        return c;
    }

    public BankManager newManager(String username, String password){
        BankManager c = new BankManager(nextID, username, password);
        if(users.containsKey(c.getUsername())){
            return null;
        }
        addInstance(c);
        return c;
    }

    /**
     * Update username
     * @param o BankMachineUser
     * @param arg the old username
     */
    @Override
    public void update(Observable o, Object arg) {
        String oldName = (String) arg;
        BankMachineUser user = (BankMachineUser) o;
        users.remove(oldName);
        users.put(user.getUsername(), user);
    }
    public List<BankMachineUser> getInstances(){
        return new ArrayList<>(users.values());
    }

    /**
     * See superclass docs
     * @param instances the instances to add
     */
    public void extend(List<BankMachineUser> instances){
        for(BankMachineUser i:instances){
            users.put(i.getUsername(), i);
        }
        int max=0;
        for (BankMachineUser t:this.getInstances()){
            if (t.getID() > max){
                max = t.getID();
            }
        }
        this.nextID = max + 1;
    }

    /**
     * Add an instance of user to the thing
     * @param user
     */
    @Override
    public void addInstance(BankMachineUser user){
        users.put(user.getUsername(), user);
        this.nextID++;
    }
    public HashMap<String, BankMachineUser> getMap(){
        return users;
    }

    /**
     * Maps a function over all users
     * @param function
     */
    public void runOnAll(Function<BankMachineUser, Void> function) {
        for (BankMachineUser user : users.values()) {
            function.apply(user);
        }
    }

    /**
     * Get users by username
     * @param username the user's username
     * @return the bankmachine user with the username
     */
    public BankMachineUser get(String username){
        return users.get(username);
    }

    /**
     * Matches userName to password for login
     *
     * @param username for this user
     * @param password for this user
     * @return The user if login successful, null otherwise
     */
    public BankMachineUser authenticate(String username, String password) {
        BankMachineUser user = users.get(username);
        if (user == null || !user.getPassword().equals(password)){
            return null;
        }
        return user;
    }
}
