package bankmachine.finance;

import java.lang.Math;

/***
 * Class that calculates monthly mortgage rates for houses.
 */

public class MortgageCalculator {

    Double principal;
    Double rate;
    Integer payment_cycles;

    /***
     * Constructor for Mortgage Calculator
     * @param principal value of mortgage (loan) taken out
     * @param rate interest rate given
     * @param payment_cycles how many cycles mortgage is being paid out over
     */
    public MortgageCalculator(Double principal, Double rate, Integer payment_cycles){
        this.principal = principal;
        this.rate = rate;
        this.payment_cycles = payment_cycles;

    }

    /***
     * Main method to calculate monthly mortgage of property
     * @return Returns double of monthly quote
     */
    public Double getMortgage(){
        Double monthly_payment = (principal * ((rate * Math.pow((1+rate), payment_cycles)))) / ((Math.pow((1+rate), payment_cycles) -1));
        Double mortgage = (double)Math.round(monthly_payment * 100) / 100;
        return mortgage;
    }
}
