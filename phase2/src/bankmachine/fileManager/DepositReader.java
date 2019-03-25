package bankmachine.fileManager;

import bankmachine.BillManager;

import java.io.IOException;

public class DepositReader {
    private ReadFile reader;
    private boolean isCheque;
    private double quantity;
    private int[] billCounts = {0, 0, 0, 0};

    public DepositReader(String path){
        reader = new ReadFile(path);
        this.readFile();
    }

    public boolean isCheque(){
        return isCheque;
    }
    public double getQuantity(){
        return quantity;
    }

    private void readFile() {
        String contents;
        try {
            contents = reader.getData();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        String[] lines = contents.split("\\r?\\n");
        if(lines.length == 0){ return; }
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

    public int[] getBillCounts(){
        return billCounts;
    }

}
