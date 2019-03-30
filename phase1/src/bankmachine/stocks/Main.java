package bankmachine.finance;

import java.io.IOException;

public class Main {


    public static void main(String[] args) throws IOException { //Main method to test read / write of files

        stockManager sm = new stockManager("NVS");
        sm.stockInfo();


    }


}
