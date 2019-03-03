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

    /**
     * getter for a given account
     */
//    public static ArrayList<Account> getAccounts(String username) {
//        for (Client client : accounts) {
//            if (client.getUsername().equals(username)) {
//                return client.getClientsAccounts();
//            }
//        }
//        return new ArrayList<Account>();
//    }


}
