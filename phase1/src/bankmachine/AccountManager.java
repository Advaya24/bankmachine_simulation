/**
 * Could probably moved to bankmachine.account subpackage
 * Not sure what we're going to use this for yet
 */

package bankmachine;

import bankmachine.account.Account;

import java.util.ArrayList;

//TODO: make this static?
public class AccountManager {

    /* List of all accounts*/
    private static ArrayList<Account> accounts;

    public AccountManager(){}

    /**
     * Adds account to accounts.
     * @param account to be added.
     */
    public static void addAccount(Account account) {
        if (accounts.contains(account)) {
            System.out.print("This account is already in the system.");
        } else { accounts.add(account); }
    }

    //TODO: decide whether we need a getter (and if so, what is its input parameter)


}
