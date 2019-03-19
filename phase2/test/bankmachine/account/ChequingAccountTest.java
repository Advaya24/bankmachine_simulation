package bankmachine.account;

import bankmachine.users.Client;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ChequingAccountTest {
    private ChequingAccount account;
    public ChequingAccountTest(){
        Client client = mock(Client.class);
        LocalDateTime creationDate = LocalDateTime.now();
        this.account = new ChequingAccount(0, 0, client, creationDate);
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
        assertEquals(10.0, account.getBalance());
        status = account.transferOut(-200.0);
        assertFalse(status);
        status = account.transferOut(100.0);
        assertTrue(status);
        status = account.payBill(1);
        assertFalse(status);
    }

    public static class SavingsAccountTest {
        private SavingsAccount account;
        public SavingsAccountTest(){
            Client client = mock(Client.class);
            LocalDateTime creationDate = LocalDateTime.now();
            this.account = new SavingsAccount(0, 0, client, creationDate);
        }
        @Test
        public void testTransfer() {
            assertFalse(account.transferOut(1));
            account.transferIn(100.0);
            account.applyInterest();
            assertEquals(100.10, account.getBalance());

        }
    }
}
