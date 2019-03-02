package account;

import bankmachine.Client;
import bankmachine.account.Account;
import bankmachine.account.ChequingAccount;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ChequingAccountTest {
    private ChequingAccount account;
    public ChequingAccountTest(){
        Client client = mock(Client.class);
        this.account = new ChequingAccount(client);
    }
    @Test
    public void testSanity() {
        assertEquals(1.0, 1);
    }
    @Test
    public void testTransfer() {
        Account otherAccount = mock(Account.class);
        when(otherAccount.transferOut(anyInt())).thenReturn(true);
        boolean status = account.transferIn(otherAccount, 10.0);
        assertTrue(status);
        assertEquals(10.0, account.getDoubleBalance());
    }
}
