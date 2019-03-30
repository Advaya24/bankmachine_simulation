package bankmachine.exception;

public class DebtLimitException extends TransferException {
    public DebtLimitException() {
        super("Debt limit exceeded.");
    }

}
