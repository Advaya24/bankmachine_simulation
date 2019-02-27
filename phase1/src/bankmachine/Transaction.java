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
    private Account transaction_made_from;
    /**The account this transaction was made to**/
    private Account transaction_made_to;
    /**The Date this transaction was made**/
    private Date transaction_date;
    /**The type of transaction made**/
    private transaction_types transaction_type;

     public Transaction(double amount, Account from, Account to, Date datetime, transaction_types type){
        this.amount=amount;
        transaction_made_from = from;
        transaction_made_to = to;
        transaction_date = datetime;
        transaction_type = type;
    }
    /** All the getters**/
    public double getAmount(){
        return amount;
    }
    public Account getFrom(){
        return transaction_made_from;
    }
    public Account getTo(){
        return transaction_made_to;
    }
    public Date getDate(){
        return transaction_date;
    }
    public transaction_types getType(){
        return transaction_type;
    }

    /** All the setters **/
    public void setAmount(double new_amount){
        amount = new_amount;
    }
    //TODO: Decide whether you want to change the from and to accounts
    public void setFrom(Account new_from){
        transaction_made_from = new_from;
    }
    public void setTo(Account new_to){
        transaction_made_to = new_to;
    }
    public void changeDate(Date new_date){
        transaction_date = new_date;
    }
    public void changeType(transaction_types new_type){
        transaction_type = new_type;
    }
}
