package bankmachine.finance;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/***
 * Exchange Manager class - responsible for conversion between one currency and another (includes crypto currency)
 */

public class ExchangeManager {

    String cryptodata;
    String from_code;
    String from_name;
    String to_currcode;
    String to_currency;
    String exchange;
    String datetime;


    /***
     * Constructor for Exchange Class - takes in input currency and output (target) currency
     * @param inputcurrency
     * @param outputcurrency
     * @throws IOException
     */

    public ExchangeManager(String inputcurrency, String outputcurrency){
        // Initializes Json Manager class, passing in stock code and data type (stock)

        try {
            JsonManager s1 = new JsonManager(inputcurrency, outputcurrency, "exchange");

            // Json Manager Class Returns linked list of data
            LinkedList data = s1.data();

            cryptodata = (String) data.get(1);

            // Automatic Formatter - needed to format JSON hierarchy
            formatter();
        } catch (IOException e){
            System.out.println("IOException Error!");
        }

    }

    // Formatter Class for formatting JSON API output (stock specific)
    public void formatter() {

        // Replaces JSON { and } root and node symbols
        cryptodata = cryptodata.replace("{", "");
        cryptodata = cryptodata.replace("}", "");

        List<String> items = Arrays.asList(cryptodata.split("\\s*,\\s*"));
        from_code = ((items.get(0)).split(":")[1]).replace("\"", "");
        from_name = ((items.get(1)).split(":")[1]).replace("\"", "");
        to_currcode = ((items.get(2)).split(":")[1]).replace("\"", "");
        to_currency = ((items.get(3)).split(":")[1]).replace("\"", "");
        exchange = ((items.get(4)).split(":")[1]).replace("\"", "");
        datetime = ((items.get(5)).split(":")[1]).replace("\"", "");


    }

    public String getCryptoCode(){
        return from_code;
    }

    public String getCryptoName(){
        return from_name;
    }

    public String getCurrencyCode(){
        return to_currcode;
    }
    public String getCurrencyName(){
        return to_currency;
    }

    public Double getExchange(){
        return Double.parseDouble(exchange);
    }

    public String getTime(){
        return datetime;
    }

    public String getAll(){
        return(from_code + " " + from_name + " " +  to_currcode + " " + to_currency + " " + exchange + " " + datetime);
    }
}