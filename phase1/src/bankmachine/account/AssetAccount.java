package bankmachine.account;

import bankmachine.Client;

import java.time.LocalDateTime;
import java.util.Date;

public abstract class AssetAccount extends Account{
    public AssetAccount(int balance, Client client, LocalDateTime creationDate) {
        super(balance, client, creationDate);
    }
}
