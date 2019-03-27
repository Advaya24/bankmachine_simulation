package bankmachine.account;

import bankmachine.exception.BankMachineException;
import bankmachine.users.Client;
import java.time.LocalDateTime;

/** Only has one client.*/
//TODO: What happens if we try and access multiple clients of this account??
public class RetirementAccount extends AssetAccount {

    public RetirementAccount(int id, Client client, LocalDateTime creationDate){
        super(id,0, client, creationDate);
        clients = null;
    }

    /**
     * Every month, takes 5% of the money in the primary checking account and stores
     * it in the RetirementAccount
     */
    public void autoDeposit() {
        try {
            Account account = primaryClient.getPrimaryAccount();
            double balance = account.getBalance() / 5;
            account.transferOut(balance);
            transferIn(balance);
        }
        catch(BankMachineException e){//Should never happen
            System.err.println("Never happening");
        }
    }

    public String toString() {
        String output = "";
        return "ID: " + getID() + " Type: Retirement Account Balance: $" + getBalance();
    }

    /**
     * Indicates whether this account can transfer out the given amount
     *
     * @param amount the amount to be transferred out
     * @return true iff this account can transfer out this amount
     */
    @Override
    public boolean canTransferOut(double amount){
        return false;
    }
}
