package bankmachine;

import java.util.HashMap;

public class BankMachine {

    /*Dictionary containing the number of bills of each type the machine has. Order of denomination:
    * $5, $10, $20, $50*/
    static private HashMap<Integer, Integer> bills = new HashMap<>();
    /*Total amount of money in cash currently stored in this machine.*/
    static int total;

    public BankMachine(){
        bills.put(5, 0);
        bills.put(10, 0);
        bills.put(20, 0);
        bills.put(50, 0);
    }

    /**
     * Increase the amount of bills of denomination billType by amount quantity stored in BankMachine.
     * @param billType which denomination to add
     * @param quantity how many bills to add
     */
    public static void addBills(int billType, int quantity){
        if (bills.containsKey(billType)) {
            bills.put(billType, bills.get(billType) + quantity);
            total += billType * quantity;
        } else {
            System.out.println("Incompatible denomination.");
        }
    }

    /**
     * Withdraw amount from this BankMachine.
     * @param amount
     * @return true if and only if the amount was withdrawn.
     */
    public static boolean withdraw(int amount) {
        int amountLeft = amount;
        if (amountLeft % 5 != 0){
            return false;
        }
        HashMap<Integer, Integer> temporaryBills = new HashMap<>();
        for (int i : bills.keySet()) {
            temporaryBills.put(i, bills.get(i));
        }
        int[] denominations = {50, 20, 10, 5};
        for (int i : denominations) {
            while (amountLeft >= i && temporaryBills.get(i) > 0) {
                temporaryBills.put(i, temporaryBills.get(i) - 1);
                amountLeft -= i;
            }
        }
        System.out.print(temporaryBills);
        if (amountLeft == 0) {
            bills = temporaryBills;
            return true;
        }
        return false;
    }

    //TODO:update method that sends alerts to alerst.txt warning about bill shortage.

}
