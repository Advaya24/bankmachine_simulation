package bankmachine;

import java.lang.Math;

public class MoneyUtil {
    public static double roundMoney(double amount){
        return Math.floor(amount * 100f) / 100f;
    }
}
