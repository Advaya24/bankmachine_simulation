package bankmachine.stocks;
import java.io.IOException;
import java.util.*;


public class StockManager {

    private String stockdate;
    private String stockinfo;
    private String open;
    private String high;
    private String low;
    private String close;
    private String volume;

    public StockManager(String stockcode) throws IOException {
        JsonManager s1 = new JsonManager(stockcode, "stock");
        LinkedList data = s1.data();

        stockdate = (String)data.get(0);
        stockinfo = (String)data.get(1);

        formatter();

    }

    public void formatter(){
        stockinfo = stockinfo.replace("{", "");
        stockinfo = stockinfo.replace("}", "");

        List<String> items = Arrays.asList(stockinfo.split("\\s*,\\s*"));
        open = ((items.get(0)).split(":")[1]).replace("\"", "");
        high = ((items.get(1)).split(":")[1]).replace("\"", "");
        low = ((items.get(2)).split(":")[1]).replace("\"", "");
        close = ((items.get(3)).split(":")[1]).replace("\"", "");
        volume = ((items.get(4)).split(":")[1]).replace("\"", "");

        System.out.println("Open: "+open+" High: "+high+" Low: "+low+" Close: "+close+" Volume: "+volume);


    }

    public double getOpen(){
        return Double.parseDouble(open);
    }




}
