package bankmachine.finance;


import java.io.IOException;

/**
 * Class that handles Exchanges across currencies (including Crypto currencies)
 */

public class Exchange {
    String from_currency;
    String to_currency;
    Double amount;

    /***
     * Constructor for Exchange Class
     * @param from_currency String of initial currency (e.g. USD)
     * @param to_currency String of target currency (e.g. CNY)
     * @param amount Double amount of currency to exchange
     */

    public Exchange(String from_currency, String to_currency, Double amount){
        this.from_currency = from_currency;
        this.to_currency = to_currency;
        this.amount = amount;

    }

    /**
     * Method that returns String representation of exchange amount (with target currency name)
     * @return String of value and currency name
     */
    public String makeExchange() throws IOException {
        ExchangeManager em = new ExchangeManager(from_currency, to_currency);
        Double exchangerate = em.getExchange();
        return amount + " " + from_currency + " is equal to " + (exchangerate * amount) + " " + em.getCurrencyName();
    }


}
