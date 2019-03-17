package bankmachine.stocks;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import com.google.gson.*;
import com.google.gson.annotations.SerializedName;

import java.net.URLConnection;
import java.util.*;


public class StockManager {

    private String stockdate;
    private String stockinfo;

    public StockManager(String stockcode) throws IOException {
        JsonManager s1 = new JsonManager(stockcode, "stock");
        LinkedList data = s1.data();

        stockdate = (String)data.get(0);
        stockinfo = (String)data.get(1);

        System.out.println(stockdate);
        System.out.println(stockinfo);

        formatter();

    }

    public void formatter(){
        stockinfo = stockinfo.replace("{", "");
        stockinfo = stockinfo.replace("}", "");

        List<String> items = Arrays.asList(stockinfo.split("\\s*,\\s*"));
        System.out.println(items.get(0));

    }

}
