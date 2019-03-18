package bankmachine.account;

import bankmachine.users.Client;

import java.time.LocalDateTime;

/**
 * Abstract class for all asset accounts
 */
public abstract class AssetAccount extends Account {
    public AssetAccount(int id, int balance, Client client, LocalDateTime creationDate) {
        super(id, balance, client, creationDate);
    }
}
