/**
 * Could probably moved to bankmachine.account subpackage
 * Not sure what we're going to use this for yet
 */

package bankmachine;

import bankmachine.account.Account;

import java.util.ArrayList;

//TODO: make this static?
public class AccountManager {
    private static ArrayList<Client> accounts = new ArrayList<>();
    //TODO: decide what this stores (accounts of each user (i.e. each user has their instance) or accounts of all users.)

    /**
     * getter for a given account
     */
    public static ArrayList<Account> getAccounts(String username) {
        for (Client client : accounts) {
            if (client.getUsername().equals(username)) {
                return client.getClientsAccounts();
            }
        }
        return new ArrayList<Account>();
    }
}
