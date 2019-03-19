package bankmachine.account;

import bankmachine.exception.BankMachineException;
import bankmachine.exception.NegativeQuantityException;
import bankmachine.exception.NotEnoughMoneyException;
import bankmachine.users.Client;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
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
    public void testTransfer() throws BankMachineException{
        Account otherAccount = mock(Account.class);
        account.transferIn(otherAccount, 10.0);
        assertEquals(10.0, account.getBalance());
        assertThrows(
                NegativeQuantityException.class,
                () -> account.transferOut(-200)
        );
        account.transferOut(100.0);
        assertThrows(
                NotEnoughMoneyException.class,
                () -> account.payBill(1)
        );
    }

    public static class SavingsAccountTest {
        private SavingsAccount account;
        public SavingsAccountTest(){
            Client client = mock(Client.class);
            LocalDateTime creationDate = LocalDateTime.now();
            this.account = new SavingsAccount(0, 0, client, creationDate);
        }
        @Test
        public void testTransfer() throws BankMachineException {
            assertThrows(
                NotEnoughMoneyException.class,
                () -> account.transferOut(1)
            );
            account.transferIn(100.0);
            account.applyInterest();
            assertEquals(100.10, account.getBalance());

        }
    }
}
