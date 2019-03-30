package bankmachine.exception;

public class InvalidPhoneNumberException extends BankMachineException {

    public InvalidPhoneNumberException() {
        super("Invalid phone!");
    }
}
