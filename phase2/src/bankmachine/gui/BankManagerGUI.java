package bankmachine.gui;

import bankmachine.BankMachine;
import bankmachine.account.Account;
import bankmachine.exception.BankMachineException;
import bankmachine.transaction.Transaction;
import bankmachine.users.BankMachineUser;
import bankmachine.users.BankManager;
import bankmachine.users.Client;

import javax.swing.*;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@SuppressWarnings("Duplicates")
public class BankManagerGUI implements Inputtable {
    private BankManager manager;

    public BankManagerGUI(BankManager b) {
        manager = b;
    }


    /**
     * Displays options for adding bills to the bankmachine
     *
     * @param m the input manager handling this
     */
    private void inputAddBills(InputManager m) {
        int[] denominations = {5, 10, 20, 50};
//        for (int i : denominations) {
//            int quantity = m.getInteger("How many " + i + "s? ");
//            BankMachine.getBillManager().addBills(i, quantity);
//        }
        String[] attributes = new String[denominations.length];
        for (int i = 0; i < denominations.length; i++) {
            attributes[i] =  denominations[i] + "s";
        }
        m.setPanel(new TextInputForm("How many of each?", attributes) {
            @Override
            public void onCancel() {
                handleInput(m);
            }

            @Override
            public void onOk(String[] strings) {
                int[] arrayNumBillsToAdd = new int[strings.length];
                for (int i = 0; i < strings.length; i++) {
                    try {
                        int numBills = Integer.parseInt(strings[i]);
                        if (numBills > 0) {
                            arrayNumBillsToAdd[i] = numBills;
                        } else {
                            throw new NumberFormatException();
                        }
                    } catch (NumberFormatException e) {
                        m.setPanel(new AlertMessageForm("Invalid input for at least one denomination!") {
                            @Override
                            public void onOK() {
                                inputAddBills(m);
                            }
                        });
                        return;
                    }
                }
                for (int i = 0; i < arrayNumBillsToAdd.length; i++) {
                    BankMachine.getBillManager().addBills(denominations[i], arrayNumBillsToAdd[i]);
                }
                m.setPanel(new AlertMessageForm("Success!") {
                    @Override
                    public void onOK() {
                        handleInput(m);
                    }
                });
            }
        });
    }

    private void inputUndoTransaction(InputManager m) {
        inputGetClient(m, (Client client) -> {
            undoTransactionsForClient(client, m);
            return null;
        });

    }

    private void undoTransactionsForClient(Client client, InputManager m) {
        if (client.getClientsAccounts().size() == 0) {
            System.out.println("There are no accounts!");
            m.setPanel(new AlertMessageForm("There are no accounts") {
                @Override
                public void onOK() {
                    handleInput(m);
                }
            });
        } else {
            System.out.println("Select an account");
//        Account account = m.selectItem(client.getClientsAccounts());
            m.setPanel(new SearchForm("Select an account", new OptionsForm<Object>(client.getClientsAccounts().toArray(), "") {
                @Override
                public void onSelection(Object object) {
                    inputGetTransactionFor((Account) object, m);
                }
            }.getMainPanel()) {
                @Override
                public void onCancel() {
                    handleInput(m);
                }
            });
        }

//        if (account.getTransactions().size() == 0) {
//            System.out.println("There are no transactions!");
//            return;
//        }
//        System.out.println("Select a transaction");
//        Transaction transaction = m.selectItem(account.getTransactions());
//        manager.undoRecentTransaction(transaction);
//        System.out.println("Successful!");
    }

    private void inputGetTime(InputManager m) {
        getDate(m);
    }

    private void getDate(InputManager m) {
        String[] attributes = {"Year (YYYY):", "Month (1-12)", "Day (1-31)"};
        m.setPanel(new TextInputForm("Time settings", attributes) {
            @Override
            public void onCancel() {
                handleInput(m);
            }

            @Override
            public void onOk(String[] strings) {
                try {
                    setTime(LocalDateTime.of(Integer.parseInt(strings[0]), Integer.parseInt(strings[1]), Integer.parseInt(strings[2]), 0, 0));
                    m.setPanel(new AlertMessageForm("Success!") {
                        @Override
                        public void onOK() {
                            handleInput(m);
                        }
                    });
                } catch (DateTimeException | NumberFormatException e) {
                    m.setPanel(new AlertMessageForm("Invalid Date") {
                        @Override
                        public void onOK() {
                            getDate(m);
                        }
                    });
                }
            }
        });
    }

    private void setTime(LocalDateTime localDateTime) {
        BankMachine.getTimeInfo().setTime(localDateTime);
    }

    private void inputGetTransactionFor(Account account, InputManager m) {
        if (account.getTransactions().size() == 0) {
            m.setPanel(new AlertMessageForm("There are no transactions!") {
                @Override
                public void onOK() {
                    handleInput(m);
                }
            });
        } else {
            m.setPanel(new SearchForm("Select a transaction:", new OptionsForm<Object>(account.getTransactions().toArray(), "") {
                @Override
                public void onSelection(Object o) {
                    try {
                        manager.undoRecentTransaction((Transaction) o);
                        m.setPanel(new AlertMessageForm("Success!") {
                            @Override
                            public void onOK() {
                                handleInput(m);
                            }
                        });
                    } catch (BankMachineException e) {
                        e.printStackTrace();
                        m.setPanel(new AlertMessageForm("Failure!") {
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
                    handleInput(m);
                }
            });
        }

    }

    /**
     * Displays all clients and allows the manager to select a client
     *
     * @param m the input manager handling this
     * @param function a function which takes a Client and returns nothing
     * @return returns the selected client, null if there are no clients
     */
    private void inputGetClient(InputManager m, Function<Client, Void> function) {
        List<Client> clients = new ArrayList<>();
        for (BankMachineUser b : BankMachine.USER_MANAGER.getInstances()) {
            if (b instanceof Client) {
                clients.add((Client) b);
            }
        }
        String prompt;
        JPanel panel = null;
        if (clients.size() == 0) {
            prompt = "There are no clients!";
        } else {
            prompt = "Select a client";
            OptionsForm<Object> optionsForm = new OptionsForm<Object>(clients.toArray(), "") {
                @Override
                public void onSelection(Object object) {
                    function.apply((Client) object);
                }
            };
            panel = optionsForm.getMainPanel();
        }
        m.setPanel(new SearchForm(prompt, panel) {
            @Override
            public void onCancel() {
                handleInput(m);
            }
        });
    }

    private void createAccountFor(Client client, InputManager m) {
        String[] accountTypes = {"Chequing account", "Credit card account", "Line of credit account", "Savings account"};
        m.setPanel(new SearchForm("Select the type of account:", new OptionsForm<String>(accountTypes, "") {
            @Override
            public void onSelection(String s) {
                String alertMessage;
                if (manager.createAccount(client, s, LocalDateTime.now())) {
                    alertMessage = "Account created.";
                } else {
                    alertMessage = "There was some problem with the creation. Try again later.";
                }
                m.setPanel(new AlertMessageForm(alertMessage) {
                    @Override
                    public void onOK() {
                        handleInput(m);
                    }
                });
            }
        }.getMainPanel()) {
            @Override
            public void onCancel() {
                handleInput(m);
            }
        });
    }

    /**
     * Displays options for creating a new account
     *
     * @param m the input manager handling this
     */
    private void inputCreateAccount(InputManager m) {
        inputGetClient(m, (Client client1) -> {
            createAccountFor(client1, m);
            return null;
        });
    }

    /**
     * Displays outstanding account creation requests and allows manager to choose one to remove
     *
     * @param m the input manager handling this
     */
    public void removeCompletedRequests(InputManager m) {
        if (manager.getCreationRequests().size() == 0) {
            System.out.println("No pending creation requests");
        } else {
            manager.getCreationRequests().remove(m.selectItem(manager.getCreationRequests()));
        }
    }

    /**
     * Displays options for creating a new client
     *
     * @param m the input manager handling this
     */
    private void inputCreateClient(InputManager m) {
//        String name = m.getInput("Enter a name: ");
//        String username = m.getInput("Enter a username: ");
//        String phone = m.getPhone();
//        String email = m.getEmail();
//        String pwd = m.getInput("Enter a password: ");
//        Client client = BankMachine.USER_MANAGER.newClient(name, email, phone, username, pwd);
//        if(client == null){
//            System.out.println("A client with that username exists!");
//        } else {
//            System.out.println("Client created");
//        }
        String[] attributes = {"Name", "Email", "Phone", "Username", "Password", "Confirm Password"};
        m.setPanel(new TextInputForm("Create new client", attributes, 2) {
            @Override
            public void onCancel() {
                handleInput(m);
            }

            @Override
            public void onOk(String[] strings) {
                Client client1 = BankMachine.USER_MANAGER.newClient(strings[0], strings[1], strings[2], strings[3], strings[4]);
                String alertMessage;
                if (client1 == null) {
                    System.out.println("A client with that username exists!");
                    alertMessage = "A client with that username exists!";

                } else {
                    System.out.println("Client created");
                    alertMessage = "Client created";
                }
                m.setPanel(new AlertMessageForm(alertMessage) {
                    @Override
                    public void onOK() {
                        handleInput(m);
                    }
                });
            }
        });
    }

    private void handleSelection(InputManager m, String s) throws BankMachineException {
        switch (s) {
            case "Shutdown":
                m.exit();
                return;
            case "Exit":
                m.mainLoop();
                return;
            case "Settings":
                new UserGUI(manager).handleInput(m);
                break;
            case "Create Account":
                inputCreateAccount(m);
                return;
            case "Undo a Transaction":
                inputUndoTransaction(m);
                return;
            case "Set Time":
                inputGetTime(m);
                return;
            case "Create Client":
                inputCreateClient(m);
                return;
            case "Add Bills":
                inputAddBills(m);
                return;
            case "View Account Creation Requests":
                manager.viewAccountCreationRequests();
                break;
            case "Remove Completed Creation Requests":
                removeCompletedRequests(m);
                break;
            default:
                break;
        }
        handleInput(m);
    }

    /**
     * Handles the input from the bank manager
     *
     * @param m the input manager handling this
     */
    @Override
    public void handleInput(InputManager m) {
        System.out.println("Welcome, " + manager.getName() + "!");
        String[] options = {
                "View Account Creation Requests", "Remove Completed Creation Requests",
                "Create Account", "Create Client", "Set Time", "Undo a Transaction", "Add Bills",
                "Settings", "Exit", "Shutdown"
        };
        m.setPanel(new OptionsForm<String>(options, "What would you like to do?") {
            @Override
            public void onSelection(String s) {
                try {
                    handleSelection(m, s);
                } catch (BankMachineException e) {
                    System.out.println(e.toString());
                    //TODO: handle exception
                }
            }
        });

    }
}
