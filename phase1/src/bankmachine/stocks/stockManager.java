package bankmachine.stocks;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import com.google.gson.*;
import com.google.gson.annotations.SerializedName;

import java.net.URLConnection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class stockManager {

    public stockManager(String a) throws IOException {
        String sURL = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=NVS&interval=5min&outputsize=compact&apikey=MSOJPA23LLK8HUOQ";
        URL url = new URL(sURL);
        URLConnection request = url.openConnection();
        request.connect();


        // Convert to a JSON object to print data
        List<JsonObject> overlays = new LinkedList<>();


        JsonParser jp = new JsonParser(); //from gson
        JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
        JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object.
        JsonObject paymentsObject = rootobj.getAsJsonObject("Time Series (5min)");
        System.out.println(rootobj);
        System.out.println(paymentsObject);
        System.out.println(a);




    }








}
