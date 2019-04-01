package bankmachine.account;

import bankmachine.exception.BankMachineException;
import bankmachine.exception.NegativeQuantityException;
import bankmachine.users.Client;

import java.time.LocalDateTime;

/**
 * Only has one client.
 */
public class RetirementAccount extends AssetAccount {

    public RetirementAccount(int id, Client client, LocalDateTime creationDate) {
        super(id, 0, client, creationDate);
        clients = null;
    }

    /**
     * Every month, takes 5% of the money in the primary checking account and stores
     * it in the RetirementAccount
     */
    public void autoDeposit() {
        try {
            Account account = primaryClient.getPrimaryAccount();
            double balance = account.getBalance()*0.05;
            account.transferOut(balance);
            transferIn(balance);
        }catch (NegativeQuantityException e) {
            System.err.println("Checking Account has negative balance!");
        }
        catch (BankMachineException e){//Should never happen
            System.err.println("Never happening");
        }
    }

    public String toString() {
        return "ID: " + getID() + " Type: Retirement Account Balance: $" + getBalance();
    }

    /**
     * Indicates whether this account can transfer out the given amount
     *
     * @param amount the amount to be transferred out
     * @return true iff this account can transfer out this amount
     */
    @Override
    public boolean canTransferOut(double amount) {
        return false;
    }
}
