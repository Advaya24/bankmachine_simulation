package bankmachine.gui;

import bankmachine.BankMachine;
import bankmachine.account.Account;
import bankmachine.exception.*;
import bankmachine.finance.*;
import bankmachine.transaction.TransactionType;
import bankmachine.users.BankMachineUser;
import bankmachine.users.BankManager;
import bankmachine.users.Client;

import javax.swing.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
                    MortgageCalculator mortgageCalculator = new MortgageCalculator(Double.parseDouble(strings[0]), Double.parseDouble(strings[1])/1200, Integer.parseInt(strings[2]));
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

    private void selectAccountForTransfer(InputManager m) {
        Account[] accounts = getTransferAccounts(this.client);

        if (accounts.length == 0) {
            m.setPanel(new AlertMessageForm("No accounts to transfer from!") {
                @Override
                public void onOK() {
                    handleInput(m);
                }
            });
        } else {
            m.setPanel(new SearchForm("Select account to transfer from:", new OptionsForm<Account>(accounts, ""){
                @Override
                public void onSelection(Account account) {
                    handleTransfer(m , account);
                }
            }.getMainPanel()) {
                @Override
                public void onCancel() {
                    handleInput(m);
                }
            });
        }
    }

    private Account[] getTransferAccounts(Client client) {
        List<Account> listOfTransferAccounts = new ArrayList<>();
        for (Account account: client.getClientsAccounts()) {
            if (account.canTransferOut(0)) {
                listOfTransferAccounts.add(account);
            }
        }
        Account[] arrayOfTransferAccounts = new Account[listOfTransferAccounts.size()];
        listOfTransferAccounts.toArray(arrayOfTransferAccounts);
        return arrayOfTransferAccounts;
    }

    private void handleTransfer(InputManager m, Account account) {
        String[] attributes = {"Username of recipient", "Amount to transfer"};
        m.setPanel(new TextInputForm("Transfer money", attributes) {
            @Override
            public void onCancel() {
                handleInput(m);
            }

            @Override
            public void onOk(String[] strings) {
                String outputString = "";
                boolean transferComplete = false;
                Client client = null;
                double amount = 0;

                try {
                    client = (Client) BankMachine.USER_MANAGER.get(strings[0]);
                } catch (NullPointerException e) {
                    outputString = "Invalid input for username!";
                }

                if (client == null) {
                    if (outputString.equals("")) {
                        outputString = "User with that username not found";
                    }
                } else if (client.getPrimaryAccount() == null) {
                    outputString = "The selected client does not have an account you can transfer to!";
                } else {
                    try {
                        amount = Double.parseDouble(strings[1]);
                        amount = ((double)Math.round(amount*100))/100.0;
                        try {
                            account.transferOut(client.getPrimaryAccount(), amount);
                            transferComplete = true;
                            outputString = "Transferred successfully!";
                        } catch (BankMachineException e) {
                            outputString = e.toString();
                        }
                    } catch (NumberFormatException | NullPointerException e) {
                        outputString = "Invalid amount!";
                    }
                }

                if (transferComplete) {
                    BankMachine.transFactory.newTransaction(amount, account, client.getPrimaryAccount(), LocalDateTime.now(), TransactionType.TRANSFER);
                    m.setPanel(new AlertMessageForm(outputString) {
                        @Override
                        public void onOK() {
                            handleInput(m);
                        }
                    });
                } else {
                    m.setPanel(new AlertMessageForm(outputString) {
                        @Override
                        public void onOK() {
                            handleTransfer(m, account);
                        }
                    });
                }
            }
        });
    }

    private void handleInternalTransfer(InputManager m) {
        List<Account> accountList = client.getClientsAccounts();
        if (accountList.size() < 2) {
            m.setPanel(new AlertMessageForm("No account(s) to transfer between!") {
                @Override
                public void onOK() {
                    handleInput(m);
                }
            });
        } else {
            m.setPanel(new InternalTransferForm(accountList) {
                @Override
                public void onOk(Account fromAccount, Account toAccount, String amountString) {
                    String outputString;
                    double amount = 0;
                    boolean transferComplete = false;
                    if (fromAccount.getID() == toAccount.getID()) {
                        outputString = "Cannot transfer to same account!";
                    } else {
                        try {
                            amount = Double.parseDouble(amountString);
                            fromAccount.transferOut(toAccount, amount);
                            outputString = "Transferred Successfully!";
                            transferComplete = true;
                        } catch (NumberFormatException e) {
                            outputString = "Invalid amount!";
                        } catch (BankMachineException e2) {
                            outputString = e2.toString();
                        }
                    }
                    if (transferComplete) {
                        BankMachine.transFactory.newTransaction(amount, fromAccount, toAccount, LocalDateTime.now(), TransactionType.TRANSFER);
                        m.setPanel(new AlertMessageForm(outputString) {
                            @Override
                            public void onOK() {
                                handleInput(m);
                            }
                        });
                    } else {
                        m.setPanel(new AlertMessageForm(outputString) {
                            @Override
                            public void onOK() {
                                handleInternalTransfer(m);
                            }
                        });
                    }
                }

                @Override
                public void onCancel() {
                    handleInput(m);
                }
            });
        }
    }

    private void transferMenu(InputManager m) {
        String[] options = {"Internal Transfer", "Transfer to other user"};
        m.setPanel(new SearchForm("What kind of transfer?", new OptionsForm<String>(options, "") {
            @Override
            public void onSelection(String s) {
                switch (s) {
                    case "Internal Transfer":
                        handleInternalTransfer(m);
                        return;
                    case "Transfer to other user":
                        selectAccountForTransfer(m);
                }
            }
        }.getMainPanel()) {
            @Override
            public void onCancel() {
                handleInput(m);
            }
        });
    }

    private void handleWithdraw(InputManager m) {
        Account[] accounts = getTransferAccounts(this.client);
        m.setPanel(new SearchForm("Select account to withdraw from", new OptionsForm<Account>(accounts, "") {
            @Override
            public void onSelection(Account account) {
                handleWithdrawFor(account, m);
            }
        }.getMainPanel()) {
            @Override
            public void onCancel() {
                handleInput(m);
            }
        });
    }

    private void handleWithdrawFor(Account account, InputManager m) {
        String[] attributes = {"Enter withdrawal amount"};
        m.setPanel(new TextInputForm("Withdraw Money", attributes) {
            @Override
            public void onCancel() {
                handleWithdraw(m);
            }

            @Override
            public void onOk(String[] strings) {
                double amount;
                try {
                    amount = Double.parseDouble(strings[0]);
                    account.withdraw(amount);
                    BankMachine.transFactory.newTransaction(amount, account, null, LocalDateTime.now(), TransactionType.WITHDRAW);
                    m.setPanel(new AlertMessageForm("Success! Please remember to collect your money") {
                        @Override
                        public void onOK() {
                            handleInput(m);
                        }
                    });
                } catch (NumberFormatException e) {
                    m.setPanel(new AlertMessageForm("Invalid amount") {
                        @Override
                        public void onOK() {
                            handleWithdrawFor(account, m);
                        }
                    });
                } catch (NotEnoughMoneyException | NotEnoughBillsException | NegativeQuantityException e) {
                    m.setPanel(new AlertMessageForm(e.toString()) {
                        @Override
                        public void onOK() {
                            handleWithdrawFor(account, m);
                        }
                    });
                } catch (BankMachineException e) {
                    m.setPanel(new AlertMessageForm("Failed... Something went wrong!") {
                        @Override
                        public void onOK() {
                            handleWithdrawFor(account, m);
                        }
                    });
                }
            }
        });
    }

    private void handleDeposit(InputManager m) {
        Account[] accounts = new Account[this.client.getClientsAccounts().size()];
        this.client.getClientsAccounts().toArray(accounts);

        m.setPanel(new SearchForm("Select account to deposit money to", new OptionsForm<Account>(accounts, ""){
            @Override
            public void onSelection(Account account) {
                try {
                    account.deposit();
                    m.setPanel(new AlertMessageForm("Success!") {
                        @Override
                        public void onOK() {
                            handleInput(m);
                        }
                    });
                } catch (NegativeQuantityException | NoDepositException e) {
                    m.setPanel(new AlertMessageForm(e.toString()) {
                        @Override
                        public void onOK() {
                            handleInput(m);
                        }
                    });
                }
            }
        }.getMainPanel()) {
            @Override
            public void onCancel() {

            }
        });
    }

    private void handleAddUser(InputManager m) {
        Account[] accounts = new Account[this.client.getClientsAccounts().size()];
        this.client.getClientsAccounts().toArray(accounts);

        m.setPanel(new SearchForm("Select account to add user to", new OptionsForm<Account>(accounts, ""){
            @Override
            public void onSelection(Account account) {
                addUserTo(account, m);
            }
        }.getMainPanel()) {
            @Override
            public void onCancel() {
                handleInput(m);
            }
        });
    }
    private void addUserTo(Account account, InputManager m) {
        String[] attributes = {"Username of user to add"};
        m.setPanel(new TextInputForm("Add user to " + account.toString(), attributes) {
            @Override
            public void onCancel() {
                handleAddUser(m);
            }

            @Override
            public void onOk(String[] strings) {
                try {
                    client = (Client) BankMachine.USER_MANAGER.get(strings[0]);
                } catch (NullPointerException e) {
                    m.setPanel(new AlertMessageForm("Invalid input for username!") {
                        @Override
                        public void onOK() {
                            addUserTo(account, m);
                        }
                    });
                }

                if (client == null) {
                    m.setPanel(new AlertMessageForm("User not found") {
                        @Override
                        public void onOK() {
                            addUserTo(account, m);
                        }
                    });
                } else {
                    account.addSecondaryClient(client);
                    m.setPanel(new AlertMessageForm("Successfully added " + client.toString()) {
                        @Override
                        public void onOK() {
                            handleInput(m);
                        }
                    });
                }
            }
        });
    }

    private void handleSelection(InputManager m, String s){
        switch (s){
            case "Logout": m.mainLoop(); return;
            case "Request Creation Of A New Account":
                newAccountCreationInput(m);
                return;
            case "Transfer":
                transferMenu(m);
                return;
            case "Withdraw":
                handleWithdraw(m);
                return;
            case "Deposit":
                handleDeposit(m);
                return;
            case "Finance":
                handleFinance(m);
                return;
            case "Add User To Account":
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
                "Accounts", "Request Creation Of A New Account", "Transfer", "Withdraw", "Deposit", "Finance", "Add User To Account", "Update Profile", "Logout"
            };
            m.setPanel(new OptionsForm<String>(options, "What would you like to do?") {
                @Override
                public void onSelection(String s) {
                    handleSelection(m, s);
                }
            });

    }
}
