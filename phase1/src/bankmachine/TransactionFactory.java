package bankmachine;

import bankmachine.account.Account;
import bankmachine.account.AccountFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TransactionFactory extends TrackingFactory<Transaction> {
    List<Transaction> transactions = new ArrayList<>();

    public TransactionFactory(AccountFactory accounts) {
        for (Account account : accounts.getInstances()) {
            this.extend(account.getTransactions());
        }
    }

    static class CompareByDate implements Comparator<Transaction> {
        @Override
        public int compare(Transaction transaction, Transaction t1) {
            return transaction.getDate().compareTo(t1.getDate());
        }
    }

    /**
     * Add transactions to the class
     *
     * @param transactions list of transactions to add
     */
    public void extend(List<Transaction> transactions) {
        super.extend(transactions);
        this.transactions.sort(new CompareByDate());
    }

    /**
     * Creates a new transaction
     *
     * @param amount   the amount transferred
     * @param from     the account from which the money is transferred out
     * @param to       the account to which the money is transferred in
     * @param datetime the timestamp for the transaction
     * @param type     the type for this transaction
     * @return the new transaction if the transaction is successful, otherwise null
     */
    public Transaction newTransaction(double amount, Account from, Account to, LocalDateTime datetime, TransactionType type) {
        Transaction t = new Transaction(getNextID(), amount, from, to, datetime, type);
        boolean status = t.performTransaction();
        if (!status){ return null; }
        addInstance(t);
        return t;
    }
}
