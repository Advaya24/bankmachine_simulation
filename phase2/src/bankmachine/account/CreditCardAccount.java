package bankmachine.account;

import bankmachine.BankMachine;
import bankmachine.users.Client;
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
    public boolean canTransferOut(double amount) {
        return false;
    }

    /**
     * Pays the bill using this account, logs to outgoing.txt file
     *
     * @param amount the amount to be payed
     * @return true iff transaction was successful
     */
    @Override
    public boolean payBill(double amount) {
        if (amount < 0) {
            return false;
        }
        changeBalance(-amount);
        WriteFile out = new WriteFile("outgoing.txt");
        out.writeData(
                client.getName() + " paid a bill of $" + amount,
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
    public boolean withdraw(double amount) {
        if (amount < 0){ return false; }
        if (amount%1!=0){
            return false;
        }
        boolean withdraw = BankMachine.getBillManager().withdrawBills((int)amount);
        if(withdraw) {
            changeBalance(-amount);
        }
        return withdraw;
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
        return output;
    }
}
