package bankmachine.exception;

public class NoDepositException extends BankMachineException {
    public NoDepositException() {
        super("There was no deposit made with the teller");
    }
}
