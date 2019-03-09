package bankmachine;

import bankmachine.account.Account;
import bankmachine.account.AccountFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TransactionFactory extends TrackingFactory<Transaction>{
    List<Transaction> transactions = new ArrayList<>();

    public TransactionFactory(AccountFactory accounts){
        for(Account account : accounts.getInstances()){
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
     * @param transactions list of transactions to add
     */
    public void extend(List<Transaction> transactions){
        super.extend(transactions);
        this.transactions.sort(new CompareByDate());
    }
    public Transaction newTransaction(double amount, Account from, Account to, LocalDateTime datetime, TransactionType type){
        Transaction t = new Transaction(nextID, amount, from, to, datetime, type);
        addInstance(t);
        return t;
    }
}
