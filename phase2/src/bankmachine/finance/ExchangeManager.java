package bankmachine.finance;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/***
 * Exchange Manager class - responsible for conversion between one currency and another (includes crypto currency)
 */

public class ExchangeManager {

    private String cryptodata;
    private String from_code;
    private String from_name;
    private String to_currcode;
    private String to_currency;
    private String exchange;
    private String datetime;


    /***
     * Constructor for Exchange Class - takes in input currency and output (target) currency
     * @param inputcurrency
     * @param outputcurrency
     * @throws FinanceException
     * @throws ArrayIndexOutOfBoundsException
     */

    public ExchangeManager(String inputcurrency, String outputcurrency) throws FinanceException, ArrayIndexOutOfBoundsException {

        // Initializes Json Manager class, passing in stock code and data type (stock)
        JsonManager s1 = new JsonManager(inputcurrency, outputcurrency, "exchange");

        // Json Manager Class Returns linked list of data
        LinkedList data = s1.data();

        cryptodata = (String) data.get(1);

        // Automatic Formatter - needed to format JSON hierarchy
        formatter();
    }

    /**
     * Main method used to format JSON Manager output to get only relevant data needed
     * @throws ArrayIndexOutOfBoundsException thrown if linkedlist from JSON output is empty
     */

    // Formatter Class for formatting JSON API output (stock specific)
    public void formatter() throws ArrayIndexOutOfBoundsException {
        try {
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

            // Passes conflicting string to evoke later exception
        } catch (ArrayIndexOutOfBoundsException a) {
            exchange = "$&$&$&$&$&$&$&";
        }
    }

    /**
     * Getter for Cryptocurrency Code (eg. BTC)
     * @return String of this code
     */
    public String getCryptoCode() {
        return from_code;
    }

    /***
     * Getter for CryptoCurrency Name (eg. Bitcoin)
     * @return String of this name
     */
    public String getCryptoName() {
        return from_name;
    }

    /**
     * Getter for Currency Code (eg. USD)
     * @return String of this code
     */
    public String getCurrencyCode() {
        return to_currcode;
    }

    /**
     * Getter for Currency Name (e.g. US Dollar)
     * @return String of this name
     */
    public String getCurrencyName() {
        return to_currency;
    }

    /**
     * Getter for the exchange rate of the two inputted currencies
     * @return Double of exchange rate
     * @throws NumberFormatException
     */
    public Double getExchange() throws NumberFormatException {
        return Double.parseDouble(exchange);
    }

    /**
     * Get time of when exchange rate was last updated
     * @return String of date / time
     */
    public String getTime() {
        return datetime;
    }

    /***
     * Simple formatted output of all critical data used in GUI
     * @return String of all critical data
     */
    public String getAll() {
        return (from_code + " " + from_name + " " + to_currcode + " " + to_currency + " " + exchange + " " + datetime);
    }
}
