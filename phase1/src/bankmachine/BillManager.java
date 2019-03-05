package bankmachine;

import bankmachine.FileManager.WriteFile;

import java.util.HashMap;

// Rename/move to BillManager?
public class BillManager {

    /*Dictionary containing the number of bills of each type the machine has. Order of denomination:
    * $5, $10, $20, $50*/
    private HashMap<Integer, Integer> bills = new HashMap<Integer, Integer>() {
        {
            put(5, 0);
            put(10, 0);
            put(20, 0);
            put(50, 0);
        }

    };

    public BillManager(){
    }

    public HashMap<Integer, Integer> getBills() {
        return bills;
    }

    /**
     * Increase the amount of bills of denomination billType by amount quantity stored in BillManager.
     * @param billType which denomination to add
     * @param quantity how many bills to add
     */
    public void addBills(int billType, int quantity) throws Exception {
        if (bills.containsKey(billType)) {
            bills.put(billType, bills.get(billType) + quantity);
        } else {
            System.out.println("Incompatible denomination.");
        }
        //Update the alerts.txt message.
        updateAlert();
    }

    /**
     * Withdraw amount from this BillManager.
     * @param amount amount to be withdrawn.
     * @return true if and only if the amount was withdrawn.
     */
    public boolean withdrawBills(int amount) throws Exception {
        int amountLeft = amount;
        //Any withdrawal should be a multiple of 5.
        if (amountLeft % 5 != 0){
            return false;
        }
        // Create a copy of the bills HashMap.
        HashMap<Integer, Integer> temporaryBills = new HashMap<>();
        for (int i : bills.keySet()) {
            temporaryBills.put(i, bills.get(i));
        }
        int[] denominations = {50, 20, 10, 5};
        //Cycle through denominations in decreasing order.
        for (int i : denominations) {
            while (amountLeft >= i && temporaryBills.get(i) > 0) {
                temporaryBills.put(i, temporaryBills.get(i) - 1);
                amountLeft -= i;
            }
        }
        //Only update bills if the machine has enough bills to process it.
        if (amountLeft == 0) {
            bills = temporaryBills;
            updateAlert();
            return true;
        }
        return false;
    }

    /**
     * Function that writes to the alerts.txt file regarding whether the bank machine needs to be re-stocked.
     * @throws Exception depending on WriteFile.
     */
    private void updateAlert() throws Exception {
        StringBuilder warningMessage = new StringBuilder("WARNING: This bank machine currently has less than 20 bills of at least one denomination: \n");
        boolean hasChanged = false;
        for (int denomination : bills.keySet()) {
            int quantity = bills.get(denomination);
            if (quantity < 20) {
                warningMessage.append("* There are ").append(quantity).append(" $").append(denomination).append(" " +
                        "bills.\n");
                hasChanged = true;
            }
        }
        WriteFile out = new WriteFile("alerts.txt");
        if (hasChanged) {
            out.writeData(warningMessage.toString(), false);
        } else {
            out.writeData("This bank machine has enough bills of each denomination.", false);
        }

    }
}
