package bankmachine.account;

import bankmachine.Client;

import java.util.Date;

public abstract class AssetAccount extends Account{
    public AssetAccount(int balance, Client client, Date creationDate) {
        super(balance, client, creationDate);
    }
}
