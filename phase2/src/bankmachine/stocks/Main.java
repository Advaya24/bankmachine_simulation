package bankmachine.stocks;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException { //Main method to test read / write of files

        // Creates new StockManager with code NVS
        StockManager sm = new StockManager("NVS");

        // Gets stock open price
        Double open = sm.getOpen();
        System.out.println(open);

        // Gets stock volume (amount traded)
        Integer volume = sm.getVolume();
        System.out.println(volume);

        // Gets concatenated string of all stock outputs
        String all = sm.getAll();
        System.out.println(all);

        // Gets time when stock info was last updated
        String time = sm.getTime();
        System.out.println(time);

        // Creates new Exchange Manager (crypto)
        ExchangeManager cm = new ExchangeManager("BTC", "USD");

        // Gets concatenated string of all exchange outputs (crypto)
        String all2 = cm.getAll();
        System.out.println(all2);

        // Creates new Exchange Manager (currencies)
        ExchangeManager cm2 = new ExchangeManager("USD", "CNY");

        // Gets concatenated string of all exchange outputs (currencies)
        String all4 = cm2.getAll();
        System.out.println(all4);

    }


}
