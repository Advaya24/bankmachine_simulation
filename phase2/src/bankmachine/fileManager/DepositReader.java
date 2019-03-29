package bankmachine.fileManager;

import bankmachine.BillManager;
import bankmachine.exception.NoDepositException;

import java.io.IOException;

public class DepositReader {
    /** An object that allows us to read text from out files, to detect deposits made. */
    private ReadFile reader;
    /** A boolean representing whether the current Deposit is a cheque or not. */
    private boolean isCheque;
    /** A double representing how much money is involved in this Deposit. */
    private double quantity;
    /** An Array representing how many 5,10,20, and 50 dollar bills are in the system.*/
    private int[] billCounts = {0, 0, 0, 0};

    public DepositReader(String path) throws NoDepositException {
        reader = new ReadFile(path);
        this.readFile();
    }

    /**
     * Returns whether this Deposit is in the form of a cheque or not
     * @return whether this Deposit is in the form of a cheque or not
     */
    public boolean isCheque(){
        return isCheque;
    }

    /**
     * Returns the value of this Deposit
     * @return the value of this Deposit
     */
    public double getQuantity(){
        return quantity;
    }

    /**
     * //TODO: HELP
     */
    private void readFile() throws NoDepositException {
        String contents;
        try {
            contents = reader.getData();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        String[] lines = contents.split("\\r?\\n");
        if(lines.length == 0 || lines[0].equals("")){ throw new NoDepositException(); }
        quantity = 0.0;
        if (lines.length < 4){
            isCheque = true;
            try{
                quantity = Double.parseDouble(lines[0]);
            } catch (NumberFormatException e){
                return;
            }
        } else {
            isCheque = false;
            int[] denominations = BillManager.DENOMINATIONS;
            for(int i=0; i<4; i++){
                int billCount;
                try {
                    billCount = Integer.parseInt(lines[i]);
                } catch (NumberFormatException e){
                    e.printStackTrace();
                    return;
                }
                billCounts[i] = billCount;
                quantity += quantity*denominations[i];
            }
        }
        WriteFile writer = new WriteFile("/deposits.txt");
        writer.clearData();
    }

    /**
     * Returns an array representing how many of each bill is within the system.
     * @return an array representing how many of each bill is within the system.
     */
    public int[] getBillCounts(){
        return billCounts;
    }

}
