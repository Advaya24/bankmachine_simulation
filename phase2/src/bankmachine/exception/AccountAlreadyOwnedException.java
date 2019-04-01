package bankmachine.exception;

public class AccountAlreadyOwnedException extends BankMachineException {

    public AccountAlreadyOwnedException() {
        super("User already added to account!");
    }
}
