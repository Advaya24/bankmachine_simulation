package bankmachine;

import bankmachine.FileManager.ObjectFileReader;
import bankmachine.FileManager.ObjectFileWriter;
import bankmachine.account.Account;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {
    private String rootPath;
    public DataLoader(String path){
        rootPath = path;
    }

    public void loadData(String file){
        List<BankMachineUser> clients = loadFile(file);
        BankMachine.userFactory.extend(clients);
        for(BankMachineUser client : clients){
            if(client instanceof Client) {
                BankMachine.accFactory.extend(((Client) client).getClientsAccounts());
            }
        }
        for(Account account : BankMachine.accFactory.getInstances()){
            BankMachine.transFactory.extend(account.getTransactions());
        }
    }
    public void saveData(String file){
        saveFile(file, new ArrayList<>(BankMachine.userFactory.getInstances()));
    }
    public <T extends Serializable> List<T> loadFile(String filename){
        filename = rootPath + filename;
        ObjectFileReader<T> reader = new ObjectFileReader<>(filename);
        ArrayList<T> objects;
        try {
            objects = reader.read();
        } catch (Exception e) {
            objects = new ArrayList<>();
        }
        return objects;
    }

    public <T extends Serializable> void saveFile(String filename, List<T> data){
        filename = rootPath + filename;
        ObjectFileWriter<T> writer = new ObjectFileWriter<>(filename);
        writer.clear();
        writer.writeAll(data);
    }

    public static void main(String[] args){
        new DataLoader(BankMachine.fileManagerPath).loadData("/userData.ser");
    }
}

