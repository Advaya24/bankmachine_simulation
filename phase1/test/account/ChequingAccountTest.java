package account;

import bankmachine.account.ChequingAccount;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ChequingAccountTest {
    private ChequingAccount account;
    public ChequingAccountTest(){
        //this.account = new ChequingAccount();
    }
    @Test
    public void testSanity() {
        assertEquals(1.0, 1);
    }
    @Test
    public void testTransfer() {

    }

}
