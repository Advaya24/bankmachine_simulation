package bankmachine;

import bankmachine.Exceptions.NameTakenException;

import java.util.*;

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
    @Override
    public void addInstance(BankMachineUser user){
        users.put(user.getUsername(), user);
        this.nextID++;
    }
    public HashMap<String, BankMachineUser> getMap(){
        return users;
    }
}
