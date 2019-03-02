package bankmachine.account;

import bankmachine.User;

public class LineOfCreditAccount extends DebtAccount{

    public LineOfCreditAccount(int balance, User user) {
        super(balance, user);
    }
    public LineOfCreditAccount(User user) {
        super(user);
    }
}
