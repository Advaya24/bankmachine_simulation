package bankmachine.stocks;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import com.google.gson.*;

import java.net.URLConnection;
import java.util.*;

public class JsonManager {

    String sURL;
    URL url;
    URLConnection request;
    JsonParser jp;
    JsonElement root;
    JsonObject rootobj;

    String input;
    String input2;
    String type;
    String header;

    JsonObject jsonheader;

    public JsonManager(String input, String input2, String type) {
        this.input = input;
        this.input2 = input2;
        this.type = type;


    }

    public LinkedList data() throws IOException {
        if (type.equals("stock")) {
            sURL = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=" + input + "&interval=5min&outputsize=compact&apikey=MSOJPA23LLK8HUOQ";
            header = "Time Series (5min)";
        }
        if (type.equals("exchange")) {
            sURL = "https://www.alphavantage.co/query?function=CURRENCY_EXCHANGE_RATE&from_currency=" + input + "&to_currency="+input2+"&apikey=MSOJPA23LLK8HUOQ";
            header = "Realtime Currency Exchange Rate";

        }

        url = new URL(sURL);
        request = url.openConnection();
        request.connect();

        jp = new JsonParser(); //from gson
        root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
        rootobj = root.getAsJsonObject(); //May be an array, may be an object.

        if(type.equals("stock")) {

            jsonheader = rootobj.getAsJsonObject(header);
        }

        if(type.equals("exchange")){
            jsonheader = rootobj;
        }

        LinkedList<String> linkedlist = new LinkedList<String>();

        for (Map.Entry<String, JsonElement> e : jsonheader.entrySet()) {
            linkedlist.add(e.getKey());
            linkedlist.add(e.getValue().toString());

        }

        return linkedlist;


    }
}
