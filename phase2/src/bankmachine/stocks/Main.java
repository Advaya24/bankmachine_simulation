package bankmachine.stocks;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException { //Main method to test read / write of files
        StockManager sm = new StockManager("NVS");
        Double open = sm.getOpen();
        System.out.println(open);
    }


}
