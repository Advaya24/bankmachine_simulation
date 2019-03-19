package bankmachine.exception;

import bankmachine.account.Account;

public class NotEnoughMoneyException extends BankMachineException {
    public NotEnoughMoneyException(Account a){
        super(a.toString() + " does not have enough money.");
    }
}
