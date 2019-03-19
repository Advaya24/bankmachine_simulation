package bankmachine.finance;

import java.lang.Math;

public class MortgageCalculator {

    Double principal;
    Double rate;
    Integer payment_cycles;

    public MortgageCalculator(Double principal, Double rate, Integer payment_cycles){
        this.principal = principal;
        this.rate = rate;
        this.payment_cycles = payment_cycles;

    }

    public Double getMortgage(){
        Double monthly_payment = (principal * ((rate * Math.pow((1+rate), payment_cycles)))) / ((Math.pow((1+rate), payment_cycles) -1));
        Double mortgage = (double)Math.round(monthly_payment * 100) / 100;
        return mortgage;
    }
}
