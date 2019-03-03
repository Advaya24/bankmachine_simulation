package bankmachine;

import java.util.HashMap;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;

public class BillManagerTest {

    private HashMap<Integer, Integer> bills;

    @BeforeEach
    public void setUp() {
        //BillManager bankMachine = new BillManager();
        BillManager.addBills(50, 20);
        BillManager.addBills(20, 20);
        BillManager.addBills(10, 20);
        BillManager.addBills(5, 20);

    }

    @Test
    public void testAddBills() {
        //noinspection unchecked
        bills = BillManager.getBills();
        assertEquals(20, (int)bills.get(50));
        assertEquals(20, (int)bills.get(20));
    }

    @Test
    public void testWithdraw() {
        BillManager.withdraw(80);
        //noinspection unchecked
        bills = BillManager.getBills();
        System.out.println(bills);
        assertEquals(39, (int)bills.get(50));
        assertEquals(39, (int)bills.get(20));
        assertEquals(39, (int)bills.get(10));
        assertEquals(40, (int)bills.get(5));

        assertTrue(!BillManager.withdraw(1000000));
        assertTrue(!BillManager.withdraw(1));


    }

}
