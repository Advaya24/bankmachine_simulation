package bankmachine.finance;

/***
 * Custom Exception Created for Finance Package: Used in GUI to catch any form of issue regarding invalid stocks, or
 * exchanges.
 */

public class FinanceException extends Exception {

    public FinanceException(String message) {
        super(message);
    }
}
