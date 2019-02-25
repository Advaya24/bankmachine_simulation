package bankmachine;
import java.util.Date;
/** Represents a Transaction within the system.
 **/
// Person working on this: Varun
public class Transaction {
    /**The amount of money involve in this transaction**/
    float amount;
    /**The account this transaction was made from**/
    Account transaction_made_from;
    /**The account this transaction was made to**/
    Account transaction_made_to;
    /**The Date this transaction was made**/
    Date transaction_date;
    /**The type of transaction made**/
    transaction_types transaction_type;
}
