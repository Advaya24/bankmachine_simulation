package bankmachine.account;

import bankmachine.BankMachine;
import bankmachine.Client;
import bankmachine.fileManager.WriteFile;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * A credit card account where it is not possible to transfer out
 * but it is possible to transfer in, withdraw an pay bills
 */
public class CreditCardAccount extends DebtAccount{
    public CreditCardAccount(int id, int balance, Client client, LocalDateTime creationDate) {
        super(id, balance, client, creationDate);
    }

    @Override
    public boolean canTransferOut(int amount) { return false; }

    @Override
    public boolean payBill(int amount) {
        if(amount < 0){ return false; }
        changeBalance(-amount);
        WriteFile out = new WriteFile("outgoing.txt");
        out.writeData(
                client.getName() + " paid a bill of $" + (amount/100),
                true
        );
        return true;
    }

    @Override
    public boolean withdraw(int amount) {
        boolean withdraw = BankMachine.getBillManager().withdrawBills(amount);
        return withdraw && transferOut(amount);
    }
    /**
     * Cannot transfer out
     * @return always false
     */
    public boolean transferOut(int amount){ return false; }
    public String toString(){
        String output = "";
        output += "ID: " + getID() +" Type: Credit Card Account Balance: " + balance;
        return output;
    }
}
