package bankmachine.gui.personalGUIHandlers;


import bankmachine.finance.Exchange;
import bankmachine.finance.FinanceException;
import bankmachine.finance.MortgageCalculator;
import bankmachine.finance.StockManager;
import bankmachine.gui.*;

public class FinanceGUIHandler {
    /**
     * the GUI of the user using this system
     */
    private PersonalGUI gui;

    public FinanceGUIHandler(PersonalGUI gui) {
        this.gui = gui;
    }

    /**
     * Handles all finance operations available to the user
     *
     * @param m the InputManager that displays the GUI and accepts input
     */
    public void handleFinance(InputManager m) {
        String[] financeOptions = {"Stocks", "Exchange", "Mortgage"};

        m.setPanel(new SearchForm("Select a finance service", new OptionsForm<String>(financeOptions, "") {
            @Override
            public void onSelection(String s) {
                switch (s) {
                    case "Stocks":
                        handleStocks(m);
                        return;
                    case "Exchange":
                        handleExchange(m);
                        return;
                    case "Mortgage":
                        handleMortgage(m);
                }
            }
        }.getMainPanel()) {
            @Override
            public void onCancel() {
                gui.handleInput(m);
            }
        });
    }

    /**
     * Handles all the stock functionality of the application
     *
     * @param m the InputManager that displays the GUI and accepts input
     */
    private void handleStocks(InputManager m) {
        String[] attributes = {"Stock code"};
        m.setPanel(new TextInputForm("Real-time Stock Viewer", attributes) {
            @Override
            public void onCancel() {
                handleFinance(m);
            }

            @Override
            public void onOk(String[] strings) {
                StringBuilder outputString = new StringBuilder();
                try {
                    StockManager stockManager = new StockManager(strings[0]);
                    outputString.append("Information for ").append(strings[0]).append(":");
                    outputString.append(stockManager.getAll());
                } catch (FinanceException | NullPointerException e) {
                    outputString.append("Invalid stock code!");
                }
                m.setPanel(new AlertMessageForm(outputString.toString()) {
                    @Override
                    public void onOK() {
                        handleStocks(m);
                    }
                });
            }
        });
    }

    /**
     * Handles exchange functionality
     *
     * @param m the InputManager that displays the GUI and accepts input
     */
    private void handleExchange(InputManager m) {
        String[] attributes = {"From", "To", "Amount"};
        m.setPanel(new TextInputForm("Real-time Exchange Rates", attributes) {
            @Override
            public void onCancel() {
                handleFinance(m);
            }

            @Override
            public void onOk(String[] strings) {
                String outputString;
                try {
                    double amount = Double.parseDouble(strings[2]);
                    if (amount <= 0) {
                        throw new NumberFormatException();
                    }
                    Exchange exchange = new Exchange(strings[0], strings[1], amount);
                    outputString = exchange.makeExchange();
                } catch (NumberFormatException | NullPointerException | FinanceException e) {
                    outputString = "At least one of the currency codes or amounts were invalid!";
                }

                m.setPanel(new AlertMessageForm(outputString) {
                    @Override
                    public void onOK() {
                        handleExchange(m);
                    }
                });
            }
        });
    }

    /**
     * Handles mortgage calculator functionality
     *
     * @param m the InputManager that displays the GUI and accepts input
     */
    private void handleMortgage(InputManager m) {
        String[] attributes = {"Principal", "Annual interest rate %", "Number of payment cycles"};

        m.setPanel(new TextInputForm("Mortgage Calculator", attributes) {
            @Override
            public void onCancel() {
                handleFinance(m);
            }

            @Override
            public void onOk(String[] strings) {
                String outputString;
                try {
                    MortgageCalculator mortgageCalculator = new MortgageCalculator(Double.parseDouble(strings[0]), Double.parseDouble(strings[1]) / 1200, Integer.parseInt(strings[2]));
                    outputString = "Monthly quote: " + mortgageCalculator.getMortgage();
                } catch (NumberFormatException | NullPointerException e) {
                    outputString = "At least one of the fields was given invalid input!";
                }
                m.setPanel(new AlertMessageForm(outputString) {
                    @Override
                    public void onOK() {
                        handleMortgage(m);
                    }
                });
            }
        });
    }
}
