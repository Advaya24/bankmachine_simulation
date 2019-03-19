package bankmachine.account;

import bankmachine.BankMachine;
import bankmachine.exception.BankMachineException;
import bankmachine.exception.NegativeQuantityException;
import bankmachine.exception.TransferException;
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
     */
    @Override
    public void payBill(double amount) throws TransferException {
        if (amount < 0) {
            throw new NegativeQuantityException();
        }
        changeBalance(-amount);
        WriteFile out = new WriteFile("outgoing.txt");
        out.writeData(
                client.getName() + " paid a bill of $" + amount,
                true
        );
    }

    /**
     * Withdraw specified amount, if possible
     *
     * @param amount the amount to withdraw
     */
    @Override
    public void withdraw(double amount) throws BankMachineException {
        BankMachine.getBillManager().withdrawBills(amount);
        changeBalance(-amount);
    }

    /**
     * Cannot transfer out
     */
    public void transferOut(int amount) throws TransferException {
        throw new TransferException("Cannot transfer out of a credit card account.");
    }

    public String toString() {
        String output = "";
        return output;
    }
}
