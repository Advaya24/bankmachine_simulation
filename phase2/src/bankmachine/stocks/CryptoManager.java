package bankmachine.stocks;

import java.io.IOException;
import java.util.LinkedList;

public class CryptoManager {

    String temp1;
    String temp2;




    public CryptoManager(String cryptocode) throws IOException {

        // Initializes Json Manager class, passing in stock code and data type (stock)
        JsonManager s1 = new JsonManager(cryptocode, "crypto");

        // Json Manager Class Returns linked list of data
        LinkedList data = s1.data();

        temp1 = (String) data.get(0);
        temp2 = (String) data.get(1);

        System.out.println(temp1);
        System.out.println(temp2);

        // Automatic Formatter - needed to format JSON hierarchy
        //formatter();

    }
}
