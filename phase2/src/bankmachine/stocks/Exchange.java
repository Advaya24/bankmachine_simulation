package bankmachine.stocks;

public class Exchange {
    String from_currency;
    String to_currency;
    Double amount;

    String papa;


    public Exchange(String from_currency, String to_currency, Double amount){
        this.from_currency = from_currency;
        this.to_currency = to_currency;
        this.amount = amount;

    }

    public String makeExchange() {
        try {
            ExchangeManager em = new ExchangeManager(from_currency, to_currency);
            Double exchangerate = em.getExchange();
            papa = (exchangerate * amount) + " " + em.getCurrencyName();
        } catch (Exception e) {
            System.out.println("ERROR");
            papa = null;
        }
        return papa;
    }


}
