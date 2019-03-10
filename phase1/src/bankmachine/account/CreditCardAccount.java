package bankmachine.account;

import bankmachine.BankMachine;
import bankmachine.Client;
import bankmachine.fileManager.WriteFile;

import java.time.LocalDateTime;

/**
 * A credit card account where it is not possible to transfer out
 * but it is possible to transfer in, withdraw and pay bills
 */
public class CreditCardAccount extends DebtAccount {
    public CreditCardAccount(int id, int balance, Client client, LocalDateTime creationDate) {
        super(id, balance, client, creationDate);
    }

    /**
     * Indicates whether this account can transfer out the given amount
     * @param amount the amount to be transferred out
     * @return false always
     */
    @Override
    public boolean canTransferOut(int amount) {
        return false;
    }

    /**
     * Pays the bill using this account, logs to outgoing.txt file
     *
     * @param amount the amount to be payed
     * @return true iff transaction was successful
     */
    @Override
    public boolean payBill(int amount) {
        if (amount < 0) {
            return false;
        }
        changeBalance(-amount);
        WriteFile out = new WriteFile("outgoing.txt");
        out.writeData(
                client.getName() + " paid a bill of $" + (amount / 100),
                true
        );
        return true;
    }

    /**
     * Withdraw specified amount, if possible
     *
     * @param amount the amount to withdraw
     * @return true iff withdraw was successful
     */
    @Override
    public boolean withdraw(int amount) {
        if (amount < 0){ return false; }
        boolean withdraw = BankMachine.getBillManager().withdrawBills(amount);
        if(withdraw){
            changeBalance(-amount);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Cannot transfer out
     *
     * @return always false
     */
    public boolean transferOut(int amount) {
        return false;
    }

    public String toString() {
        String output = "";
        output += "ID: " + getID() + " Type: Credit Card Account Balance: $" + getDoubleBalance();
        return output;
    }
}
