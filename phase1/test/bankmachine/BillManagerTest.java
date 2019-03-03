package bankmachine;

import java.util.HashMap;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;

public class BillManagerTest {

    private HashMap<Integer, Integer> bills;
    private BillManager billManager;

    @BeforeEach
    public void setUp() {
        this.billManager = new BillManager();
        //BillManager bankMachine = new BillManager();
        billManager.addBills(50, 20);
        billManager.addBills(20, 20);
        billManager.addBills(10, 20);
        billManager.addBills(5, 20);

    }

    @Test
    public void testAddBills() {
        //noinspection unchecked
        bills = billManager.getBills();
        assertEquals(20, (int)bills.get(50));
        assertEquals(20, (int)bills.get(20));
    }

    @Test
    public void testWithdraw() {
        billManager.withdraw(80);
        //noinspection unchecked
        bills = billManager.getBills();
        System.out.println(bills);
        assertEquals(39, (int)bills.get(50));
        assertEquals(39, (int)bills.get(20));
        assertEquals(39, (int)bills.get(10));
        assertEquals(40, (int)bills.get(5));

        assertTrue(!billManager.withdraw(1000000));
        assertTrue(!billManager.withdraw(1));


    }

}