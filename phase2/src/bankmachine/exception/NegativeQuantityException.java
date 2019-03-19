package bankmachine.exception;

public class NegativeQuantityException extends TransferException {
    public NegativeQuantityException(){
        super("Amount must be positive");
    }
}
