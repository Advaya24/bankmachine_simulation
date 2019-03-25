package bankmachine.gui;

import bankmachine.BankMachine;
import bankmachine.account.Account;
import bankmachine.finance.Exchange;
import bankmachine.finance.ExchangeManager;
import bankmachine.finance.MortgageCalculator;
import bankmachine.finance.StockManager;
import bankmachine.users.BankManager;
import bankmachine.users.Client;

import javax.swing.*;
import java.io.IOException;

public class PersonalGUI implements Inputtable {
    private Client client;
    public PersonalGUI(Client c){
        this.client = c;
    }

    private void newAccountCreationInput(InputManager m){
        String[] accountTypes ={"Chequing account", "Credit card account",
                "Line of credit account", "Savings account","Retirement account"};
        m.setPanel(new SearchForm("Select type of account", new OptionsForm<String>(accountTypes, ""){
            @Override
            public void onSelection(String s) {
                addCreationRequest(s, m);
            }
        }.getMainPanel()) {
            @Override
            public void onCancel() {
                handleInput(m);
            }
        });
    }
    private void addCreationRequest(String request, InputManager m) {
        BankManager manager = BankMachine.USER_MANAGER.getBankManagers().get(0);
        manager.addCreationRequest(client.getUsername() + " requests a " + request);
        m.setPanel(new AlertMessageForm("Account Creation Request Sent") {
            @Override
            public void onOK() {
                handleInput(m);
            }
        });
    }

    private void handleFinance(InputManager m) {
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
                handleInput(m);
            }
        });
    }

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
                } catch (IOException e) {
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
                    Exchange exchange = new Exchange(strings[0], strings[1], Double.parseDouble(strings[2]));
                    outputString = exchange.makeExchange();
                } catch (IOException | NumberFormatException | NullPointerException e) {
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

    private void handleMortgage(InputManager m) {
        String[] attributes = {"Principal", "Interest rate %", "Number of payment cycles"};

        m.setPanel(new TextInputForm("Mortgage Calculator", attributes) {
            @Override
            public void onCancel() {
                handleFinance(m);
            }

            @Override
            public void onOk(String[] strings) {
                String outputString;
                try {
                    MortgageCalculator mortgageCalculator = new MortgageCalculator(Double.parseDouble(strings[0]), Double.parseDouble(strings[1])/100, Integer.parseInt(strings[2]));
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

    private void inputGetAccounts(InputManager m) {
        JPanel panel = null;
        String[] accountSummary = client.getAccountSummary().clone();
        if (client.getClientsAccounts().size() > 0) {
            panel = new OptionsForm<Object>(client.getClientsAccounts().toArray(), ""){
                @Override
                public void onSelection(Object obj) {
                    handleAccount(m, (Account)obj);
                }
            }.getMainPanel();
        } else {
            String[] tempAccountSummary = new String[accountSummary.length + 1];
            for (int i = 0; i < accountSummary.length; i++) {
                tempAccountSummary[i] = accountSummary[i];
            }
            tempAccountSummary[accountSummary.length] = "No accounts to show";
            accountSummary = tempAccountSummary;
        }
        m.setPanel(new AccountSummaryForm(accountSummary, panel) {
            @Override
            public void onCancel() {
                handleInput(m);
            }
        });
    }

    private void handleSelection(InputManager m, String s){
        switch (s){
            case "Exit": m.mainLoop(); return;
            case "Request Creation Of A New Account":
                newAccountCreationInput(m);
                return;
            case "Finance":
                handleFinance(m);
                return;
            case "Update Profile":
                new UpdateProfileGUI(client, this).handleInput(m);
                return;
            case "Accounts":
                inputGetAccounts(m);
                return;
            default:
                break;
        }
        handleInput(m);
    }

    private void handleAccount(InputManager m, Account account) {
        if(account==null){
            return;
        }
        new AccountGUI(account).handleInput(m);
    }


    @Override
    public void handleInput(InputManager m){
        System.out.println("Welcome, "+client.getName()+"!");
            System.out.println("Select an action");
            String[] options = {
                "Accounts", "Request Creation Of A New Account", "Finance", "Update Profile", "Exit"
            };
            m.setPanel(new OptionsForm<String>(options, "What would you like to do?") {
                @Override
                public void onSelection(String s) {
                    handleSelection(m, s);
                }
            });

    }
}
