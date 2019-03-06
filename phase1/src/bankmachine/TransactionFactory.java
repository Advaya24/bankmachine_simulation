package bankmachine;

import bankmachine.account.Account;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TransactionFactory {
    List<Transaction> transactions = new ArrayList<>();

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
        for (Transaction t : transactions){
            if(!this.transactions.contains(t)){
                this.transactions.add(t);
            }
        }
        this.transactions.sort(new CompareByDate());
    }
    public Transaction newTransaction(double amount, Account from, Account to, LocalDateTime datetime, TransactionType type){
        Transaction t = new Transaction(amount, from, to, datetime, type);
        transactions.add(t);
        return t;
    }
}
