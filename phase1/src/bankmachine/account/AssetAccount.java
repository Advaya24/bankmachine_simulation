package bankmachine.account;

import bankmachine.Client;

import java.time.LocalDateTime;
import java.util.Date;

/**
 *  Abstract class for all asset accounts
 */
public abstract class AssetAccount extends Account{
    public AssetAccount(int id, int balance, Client client, LocalDateTime creationDate) {
        super(id, balance, client, creationDate);
    }
}
