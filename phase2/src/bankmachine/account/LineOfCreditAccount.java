package bankmachine.account;

import bankmachine.users.Client;

import java.time.LocalDateTime;

public class LineOfCreditAccount extends DebtAccount {

    public LineOfCreditAccount(int id, int balance, Client client, LocalDateTime creationDate) {
        super(id, balance, client, creationDate);
    }

    /**
     * Transfer money out. Returns false if account doesn't have enough money
     *
     * @param amount the amount to transfer
     * @return true iff transfer was successful
     */
    @Override
    public boolean canTransferOut(double amount) {
        return true;
    }

    public String toString() {
        String output = "";
        output += "ID: " + getID() + " Type: Line of Credit Account Balance: $" + getBalance();
        return output;
    }
}
