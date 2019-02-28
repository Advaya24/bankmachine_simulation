package bankmachine;

import bankmachine.account.Account;

import java.util.ArrayList;

/**A Bank Manager within the system**/
//Working on: Varun (if that's okay with y'all, seeing that I'm working on User and the two are kinda linked.
    //Should this extend BankMachineUser?
public class BankManager extends BankMachineUser {
    BankManager(String username, String password) {
        super(username, password);
    }

    //TODO: Can a user have more than one account of the same type?
//    public boolean newAccount(String username, String accountType) {
//        ArrayList<Account> accounts = AccountManager.getAccounts(username);
//        for (Account account1 : accounts) {
//            if
//        }
//
//    }

}
