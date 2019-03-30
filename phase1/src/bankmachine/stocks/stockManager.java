package bankmachine.finance;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import com.google.gson.*;
import com.google.gson.annotations.SerializedName;

import java.net.URLConnection;
import java.util.*;


public class stockManager {

    private String stockdate;
    private String stockinfo;
    private String open;
    private String high;
    private String low;
    private String close;
    private String volume;




    public stockManager(String code) throws IOException {
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

        //System.out.println(stockinfo);

        List<String> items = Arrays.asList(stockinfo.split("\\s*,\\s*"));
        List<String> data_values = new ArrayList<>();

        for (String s : items) {
            s.replaceAll("\"", "");
            String s1 = s.substring(s.indexOf(":")+1);

            data_values.add(s1);
        }
        open = data_values.get(0);
        high = data_values.get(1);
        low = data_values.get(2);
        close = data_values.get(3);
        volume = data_values.get(4);
        //System.out.println("Open: " + open);
    }

    public void stockInfo(){
        System.out.println("Open:");

    }

}
