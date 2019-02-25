package bankmachine;
import java.util.Date;
/** Represents a Transaction within the system.
 **/
public class Transaction {
    float amount;
    Account transaction_made_from;
    Account transaction_made_to;
    Date transaction_date;
    transaction_types transaction_type;
}
