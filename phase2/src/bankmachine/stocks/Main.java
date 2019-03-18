package bankmachine.stocks;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException { //Main method to test read / write of files
        StockManager sm = new StockManager("NVS");

        Double open = sm.getOpen();
        System.out.println(open);

        Integer volume = sm.getVolume();
        System.out.println(volume);

        String all = sm.getAll();
        System.out.println(all);

        String time = sm.getTime();
        System.out.println(time);


        ExchangeManager cm = new ExchangeManager("BTC", "USD");
        String all2 = cm.getAll();
        System.out.println(all2);

        ExchangeManager cm2 = new ExchangeManager("USD", "CNY");

    }


}
