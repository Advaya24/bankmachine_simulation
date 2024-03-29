package bankmachine;

import java.util.HashMap;

import bankmachine.exception.NotEnoughBillsException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;

public class BillManagerTest {

    private HashMap<Integer, Integer> bills;
    private BillManager billManager;

    @BeforeEach
    public void setUp(){
        this.billManager = new BillManager("");
        //BillManager bankMachine = new BillManager();
        billManager.addBills(50, 20);
        billManager.addBills(20, 20);
        billManager.addBills(10, 20);
        billManager.addBills(5, 20);

    }

    @Test
    public void testAddBills() {
        bills = billManager.getBills();
        assertEquals(20, (int)bills.get(50));
        assertEquals(20, (int)bills.get(20));
    }

    @Test
    public void testWithdrawBills() throws NotEnoughBillsException{
        billManager.withdrawBills(80);
        bills = billManager.getBills();
        assertEquals(19, (int)bills.get(50));
        assertEquals(19, (int)bills.get(20));
        assertEquals(19, (int)bills.get(10));
        assertEquals(20, (int)bills.get(5));

        assertThrows(
            NotEnoughBillsException.class,
            () -> billManager.withdrawBills(1000000)
        );
        assertThrows(
            NotEnoughBillsException.class,
            () -> billManager.withdrawBills(1)
        );


    }

}
