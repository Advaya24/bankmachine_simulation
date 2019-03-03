package account;

import bankmachine.Client;
import bankmachine.account.Account;
import bankmachine.account.ChequingAccount;
import bankmachine.account.SavingsAccount;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class SavingsAccountTest {
    private SavingsAccount account;
    public SavingsAccountTest(){
        Client client = mock(Client.class);
        Date creationDate = mock(Date.class);
        this.account = new SavingsAccount(0, client, creationDate);
    }
    @Test
    public void testTransfer() {
        assertFalse(account.transferOut(1));
        account.transferIn(100.0);
        account.applyInterest();
        assertEquals(10010, account.getBalance());

    }
}
