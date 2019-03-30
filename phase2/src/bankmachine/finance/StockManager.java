package bankmachine.finance;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/*** Class that handles stock information from external API - Uses JsonManager Class to Parse Data
 * API Information: https://www.alphavantage.co/documentation/
 */

public class StockManager {

    /**
     * Global Variables used to handle Stock data
     */

    private String stockdate;
    private String stockinfo;
    private String open;
    private String high;
    private String low;
    private String close;
    private String volume;

    /**
     * Constructor for StockManager - takes in stock code (e.g. AAPL or MSFT) as parameter
     *
     * @param stockcode NASDAQ Stock Code abbreviation (e.g. AAPL = Apple)
     * @throws IOException Throws Exception for JSON Manager
     */

    public StockManager(String stockcode) throws FinanceException {

        // Initializes Json Manager class, passing in stock code and data type (stock)
        // Second parameter is null as this parameter is only needed for conversion of two currencies
        JsonManager s1 = new JsonManager(stockcode, null, "stock");

        // Json Manager Class Returns linked list of data
        LinkedList data = s1.data();

        stockdate = (String) data.get(0);
        stockinfo = (String) data.get(1);

        // Automatic Formatter - needed to format JSON hierarchy
        formatter();

    }

    // Formatter Class for formatting JSON API output (stock specific)
    public void formatter() {

        // Replaces JSON { and } root and node symbols
        stockinfo = stockinfo.replace("{", "");
        stockinfo = stockinfo.replace("}", "");

        // Sets global variables of interest
        List<String> items = Arrays.asList(stockinfo.split("\\s*,\\s*"));
        open = ((items.get(0)).split(":")[1]).replace("\"", "");
        high = ((items.get(1)).split(":")[1]).replace("\"", "");
        low = ((items.get(2)).split(":")[1]).replace("\"", "");
        close = ((items.get(3)).split(":")[1]).replace("\"", "");
        volume = ((items.get(4)).split(":")[1]).replace("\"", "");

    }

    public String getTime() {
        return stockdate;
    }

    public double getOpen() {
        return ((double) Math.round(100 * (Double.parseDouble(open))) / 100.0);
    }

    public double getHigh() {
        return ((double) Math.round(100 * (Double.parseDouble(high))) / 100.0);
    }

    public double getLow() {
        return ((double) Math.round(100 * (Double.parseDouble(low))) / 100.0);
    }

    public double getClose() {
        return ((double) Math.round(100 * (Double.parseDouble(close))) / 100.0);
    }

    public Integer getVolume() {
        return Integer.parseInt(volume);
    }

    public String getAll() {
        return ("Open: " + getOpen() + " High: " + getHigh() + " Low: " + getLow() + " Close: " + getClose() + " Volume: " + volume);
    }


}
