package bankmachine.users;

import bankmachine.transaction.Transaction;
import bankmachine.account.*;
import com.sun.istack.internal.Nullable;

import java.util.ArrayList;
import java.util.List;

/**A Client within this system.**/
// Person working on this: Varun
public class Client extends BankMachineUser {
    /**All the accounts this Client has**/
    private List<Account> clientsAccounts = new ArrayList<>();
    public Client(int id, String name, String email, String phoneNumber, String username, String default_password) {
        super(id,name,email, phoneNumber, username, default_password);
    }

    /**All the getters for this class' private variables:**/

    public List<Account> getClientsAccounts(){
        return clientsAccounts;
    }

    /**
     * Adds a newly created account for this client.
     * @param newAccount the Account just created for this client
     */
    public void addAccount(Account newAccount){
        clientsAccounts.add(newAccount);
        if (!(newAccount instanceof ChequingAccount)){
            return;
        }
        for (Account a : clientsAccounts){
            if (a instanceof ChequingAccount && ((ChequingAccount) a).isPrimary()){
                return;
            }
        }
        for (Account a : clientsAccounts){
            if (a instanceof ChequingAccount){
                ((ChequingAccount) a).setPrimary(true);
                return;
            }
        }
    }


    /**
     * Returns the most recent transaction across all accounts .
     * @return the most recent transaction of this client
     */
    @Nullable
    public Transaction mostRecentTransaction(Account a1){
        if (!getClientsAccounts().contains(a1))
        {
            return null;
        }
        else {
            return a1.getTransactions().get(a1.getTransactions().size() - 1);
        }
    }

    /**
     * Returns the net total of the client
     * @return the net total of the client
     */
    public double getNetTotal(){
        double debt=0;
        double asset =0;
        for (Account a: clientsAccounts){
            if(a instanceof DebtAccount){
                debt+=a.getDoubleBalance();
            }
            else if(a instanceof AssetAccount){
                asset+=a.getDoubleBalance();
            }
        }
        return asset-debt;
    }

    /**
     * Prints the summary of all Accounts this Client has.
     */
    public void printAccountSummary(){
        System.out.println("Account summary for username: " + getUsername());
        for(Account a: clientsAccounts){
            System.out.println(a);
        }
        System.out.println("Net total: "+getNetTotal());
    }
    @Override
    public String toString(){
        return "Client "+getName();
    }


    public Account getPrimaryAccount(){
        for (Account a:this.clientsAccounts){
            if (a instanceof ChequingAccount && ((ChequingAccount) a).isPrimary()){
                return a;
            }
        }
        if(this.clientsAccounts.size() == 0){
            return null;
        }
        return this.clientsAccounts.get(0);
    }


}
