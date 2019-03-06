package bankmachine;

import bankmachine.FileManager.ObjectFileReader;
import bankmachine.FileManager.ObjectFileWriter;
import bankmachine.account.Account;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DataLoader {
    private String rootPath;
    public DataLoader(String path){
        rootPath = path;
    }

    public void loadData(String file){
        List<BankMachineUser> clients = loadFile(file);
        for(BankMachineUser client : clients){
            if(client instanceof Client) {
                Account.accounts.addAll(((Client) client).getClientsAccounts());
            }
            BankMachineUser.users.put(client.getUsername(), client);
        }
        for(Account account : Account.accounts){
            for (Transaction t:account.getTransactions()){
                if (!Transaction.transactions.contains(t)){
                    Transaction.transactions.add(t);
                }
            }
        }
        Transaction.transactions.sort(new CompareByDate());
    }
    public void saveData(String file){
        saveFile(file, new ArrayList<>(BankMachineUser.users.values()));
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

class CompareByDate implements Comparator<Transaction>{

    @Override
    public int compare(Transaction transaction, Transaction t1) {
        return transaction.getDate().compareTo(t1.getDate());
    }
}
