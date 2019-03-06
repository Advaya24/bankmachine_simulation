package bankmachine;
import bankmachine.account.Account;

import java.io.Serializable;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.List;

/** Represents a Transaction within the system.
 * This class does not actually have any methods; it just is a container for information.**/
// Person working on this: Varun
public class Transaction implements Serializable {
    /**The amount of money involve in this transaction**/
    private double amount;
    /**The account this transaction was made from**/
    private Account transactionMadeFrom;
    /**The account this transaction was made to**/
    private Account transactionMadeTo;
    /**The LocalDateTime this transaction was made**/
    private LocalDateTime transactionDate;
    /**The type of transaction made**/
    private TransactionType transactionType;

    public Transaction(double amount, Account from, Account to, LocalDateTime datetime, TransactionType type){
        this.amount=amount;
        transactionMadeFrom = from;
        transactionMadeTo = to;
        transactionDate = datetime;
        transactionType = type;
    }
    /** All the getters**/
    public double getAmount(){
        return amount;
    }
    public Account getFrom(){
        return transactionMadeFrom;
    }
    public Account getTo(){
        return transactionMadeTo;
    }
    public LocalDateTime getDate(){
        return transactionDate;
    }
    public TransactionType getType(){
        return transactionType;
    }

    /** All the setters **/
    public void setAmount(double new_amount){
        amount = new_amount;
    }
    public void setFrom(Account new_from){
        transactionMadeFrom = new_from;
    }
    public void setTo(Account new_to){
        transactionMadeTo = new_to;
    }
    public void setLocalDateTime(LocalDateTime new_date){
        transactionDate = new_date;
    }
    public void setType(TransactionType new_type){
        transactionType = new_type;
    }
}
