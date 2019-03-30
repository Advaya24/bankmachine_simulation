package bankmachine.exception;

public class InvalidEmailException extends BankMachineException {

    public InvalidEmailException() {
        super("Invalid email address!");
    }
}
