package bankmachine;
import bankmachine.account.Account;
import java.util.Date;
/** Represents a Transaction within the system.
 * This class does not actually have any methods; it just is a container for information.**/
// Person working on this: Varun
//TODO: Add Getters and Setters
public class Transaction {
    /**The amount of money involve in this transaction**/
    private double amount;
    /**The account this transaction was made from**/
    private Account transactionMadeFrom;
    /**The account this transaction was made to**/
    private Account transactionMadeTo;
    /**The Date this transaction was made**/
    private Date transaction_date;
    /**The type of transaction made**/
    private transaction_types transactionType;

     public Transaction(double amount, Account from, Account to, Date datetime, transaction_types type){
        this.amount=amount;
        transactionMadeFrom = from;
        transactionMadeTo = to;
        transaction_date = datetime;
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
    public Date getDate(){
        return transaction_date;
    }
    public transaction_types getType(){
        return transactionType;
    }

    /** All the setters **/
    public void setAmount(double new_amount){
        amount = new_amount;
    }
    //TODO: Decide whether you want to change the from and to accounts
    public void setFrom(Account new_from){
        transactionMadeFrom = new_from;
    }
    public void setTo(Account new_to){
        transactionMadeTo = new_to;
    }
    public void changeDate(Date new_date){
        transaction_date = new_date;
    }
    public void changeType(transaction_types new_type){
        transactionType = new_type;
    }
}
