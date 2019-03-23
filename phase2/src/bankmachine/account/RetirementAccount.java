package bankmachine.account;

import bankmachine.BankMachine;
import bankmachine.exception.BankMachineException;
import bankmachine.users.Client;

import java.time.LocalDateTime;

public class RetirementAccount extends AssetAccount {

    public RetirementAccount(int id, Client client, LocalDateTime creationDate){
        super(id,0, client, creationDate);
    }
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
        output += "ID: " + getID() + " Type: Retirement Account Balance: $" + getBalance();
        return output;
    }

    @Override
    public boolean canTransferOut(double d){
        return false;
    }
}
