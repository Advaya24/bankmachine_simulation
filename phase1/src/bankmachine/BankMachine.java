package bankmachine;

public class BankMachine {

    /*Array containing the number of bills of each type the machine has. Order of denomination:
    * $5, $10, $20, $50*/
    static private int[] bills = {0, 0, 0, 0};

    /**
     * Increase the amount of bills of denomination billType by amount quantity stored in BankMachine.
     * @param billType which denomination to add
     * @param quantity how many bills to add
     */
    public static void addBills(int billType, int quantity){
       int i = -1;
       if (billType == 5) {
           i = 0;
       } else if (billType == 10) {
           i = 1;
       } else if (billType == 20) {
           i = 2;
       } else if (billType == 50) {
           i = 3;
       }
       if (i != -1){
           bills[i] += quantity;
       }
    }

    //TODO:update method that sends alerts to alerst.txt warning about bill shortage.

}
