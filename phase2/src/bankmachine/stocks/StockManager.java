package bankmachine.stocks;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.*;
import com.google.gson.annotations.SerializedName;

import java.net.URLConnection;
import java.util.*;


public class StockManager {

    private String stockdate;
    private String stockinfo;

    public StockManager(String code) throws IOException {
        String sURL = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol="+code+"&interval=5min&outputsize=compact&apikey=MSOJPA23LLK8HUOQ";
        URL url = new URL(sURL);
        URLConnection request = url.openConnection();
        request.connect();

        JsonParser jp = new JsonParser(); //from gson
        JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
        JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object.
        JsonObject paymentsObject = rootobj.getAsJsonObject("Time Series (5min)");

        LinkedList<String> linkedlist = new LinkedList<String>();

        for (Map.Entry<String, JsonElement> e : paymentsObject.entrySet()) {
            linkedlist.add(e.getKey());
            linkedlist.add(e.getValue().toString());
        }

        stockdate = linkedlist.get(0);
        stockinfo = linkedlist.get(1);

        formatter();

    }

    public void formatter(){
        stockinfo = stockinfo.replace("{", "");
        stockinfo = stockinfo.replace("}", "");

        List<String> items = Arrays.asList(stockinfo.split("\\s*,\\s*"));
        System.out.println(items.get(0));

    }

}
