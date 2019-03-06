package bankmachine;
import bankmachine.Exceptions.NameTakenException;
import bankmachine.account.Account;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ClientTest {
    private Client testClient;
    @BeforeEach
    public void setUp(){
        try {
            testClient = new Client("Test Client", "testclient@trolldomain.com", "+16472074357", "testclient", "foobar");
        } catch (NameTakenException e){}
    }
    @Test
    public void testRecentTransaction(){
        Account a1 = mock(Account.class);
        //Account a2 = mock(Account.class);
        Transaction t1 = mock(Transaction.class);
        Transaction t2 = mock(Transaction.class);
        //Transaction t3 = mock(Transaction.class);
       LocalDateTime d1= LocalDateTime.now();
       LocalDateTime d2= LocalDateTime.now().plusNanos(500000000);
//       LocalDateTime d3= newLocalDateTime(System.currentTimeMillis()+1000);
        when(t1.getDate()).thenReturn(d1);
        when(t2.getDate()).thenReturn(d2);
        //when(t3.getDate()).thenReturn(d3);
        ArrayList<Transaction> a1_transactions = new ArrayList<>();
        a1_transactions.add(t1);
        a1_transactions.add(t2);
//        ArrayList<Transaction> a2_transactions = new ArrayList<>();
//        a2_transactions.add(t2);
      //  a2_transactions.add(t3);
        when(a1.getTransactions()).thenReturn(a1_transactions);
        testClient.getClientsAccounts().add(a1);
        //when(a2.getTransactions()).thenReturn(a2_transactions);
        assertEquals(t2, testClient.mostRecentTransaction(a1));
    }
}
