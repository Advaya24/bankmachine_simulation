package bankmachine.finance;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import com.google.gson.*;
import java.net.URLConnection;
import java.util.*;

/** Class that is used to access external APIs and gets JSON information. Used for ExchangeManager and StockManager
 * Main method data() returns linked list of necessary JSON data.
 */

public class JsonManager {

    private String sURL;
    private String input;
    private String input2;
    private String type;
    private String header;
    private JsonObject jsonheader;

    /***
     * Constructor used to assign inputs and type.
     * @param input For stocks: Stock code e.g. AAPL or MSFT. For currency conversion: origin currency e.g. USD
     * @param input2 For stocks: set to null in StockManager by default. For CC: destination currency e.g. CNY
     * @param type type of data (either "stock" or "exchange")
     */
    public JsonManager(String input, String input2, String type) {
        this.input = input;
        this.input2 = input2;
        this.type = type;
    }

    /*** Main method used to retrieve JSON data and return LinkedList of that data.
     *
     * @return LinkedList of necessary data only
     * @throws IOException
     */
    public LinkedList data() throws FinanceException, NullPointerException{

        if (type.equals("stock")) {
            sURL = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=" + input + "&interval=5min&outputsize=compact&apikey=MSOJPA23LLK8HUOQ";
            header = "Time Series (5min)";
        }
        if (type.equals("exchange")) {
            sURL = "https://www.alphavantage.co/query?function=CURRENCY_EXCHANGE_RATE&from_currency=" + input + "&to_currency="+input2+"&apikey=MSOJPA23LLK8HUOQ";
            header = "Realtime Currency Exchange Rate";

        }

        try {
            URL url = new URL(sURL);
            URLConnection request = url.openConnection();
            request.connect();

            JsonParser jp = new JsonParser(); //from gson
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
            JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object.

            if (type.equals("stock")) {

                jsonheader = rootobj.getAsJsonObject(header);
            }

            if (type.equals("exchange")) {
                jsonheader = rootobj;
            }
        } catch(IOException e){
            throw new FinanceException("FinanceException");
        }

        LinkedList<String> linkedlist = new LinkedList<String>();

        for (Map.Entry<String, JsonElement> e : jsonheader.entrySet()) {
            linkedlist.add(e.getKey());
            linkedlist.add(e.getValue().toString());

        }

        return linkedlist;


    }
}
