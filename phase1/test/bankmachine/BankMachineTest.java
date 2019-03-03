package bankmachine;

import java.util.HashMap;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;

public class BankMachineTest {

    HashMap<Integer, Integer> bills;

    @BeforeEach
    public void setUp() {
        BankMachine bankMachine = new BankMachine();
        BankMachine.addBills(50, 20);
        BankMachine.addBills(20, 20);
        BankMachine.addBills(10, 20);
        BankMachine.addBills(5, 20);

    }

    @Test
    public void testAddBills() {
        //noinspection unchecked
        bills = BankMachine.getBills();
        assertEquals(20, bills.get(50));
        assertEquals(20, bills.get(20));
    }

    @Test
    public void testWithdraw() {
        BankMachine.withdraw(80);
        //noinspection unchecked
        bills = BankMachine.getBills();

        assertEquals(19, bills.get(50));
        assertEquals(19, bills.get(20));
        assertEquals(19, bills.get(10));
        assertEquals(20, bills.get(5));

        assertTrue(!BankMachine.withdraw(1000000));
        assertTrue(!BankMachine.withdraw(1));


    }

}
