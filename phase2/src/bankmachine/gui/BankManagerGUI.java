package bankmachine.gui;

import bankmachine.BankMachine;
import bankmachine.account.Account;
import bankmachine.exception.BankMachineException;
import bankmachine.transaction.Transaction;
import bankmachine.users.BankMachineUser;
import bankmachine.users.BankManager;
import bankmachine.users.Client;

import java.time.DateTimeException;
import java.time.LocalDateTime;

@SuppressWarnings("Duplicates")
public class BankManagerGUI extends BankEmployeeGUI {
    private BankManager manager;
    @SuppressWarnings("FieldCanBeLocal")
    private final String[] specialResponsibilities =  {
            "Create User", "Set Time", "Undo a Transaction", "Shutdown"
    };

    private String[] options;

    public BankManagerGUI(BankManager b) {
        super(b);
        manager = b;
        options = new String[responsibilities.length+specialResponsibilities.length];

        for(int i = 0; i < responsibilities.length; i++) {
            if (!responsibilities[i].equals("Exit"))
            options[i] = responsibilities[i];
        }
        for (int i = 0; i < specialResponsibilities.length; i++) {
            if (!specialResponsibilities[i].equals("Shutdown")) {
                options[i + responsibilities.length - 1] = specialResponsibilities[i];
            }
        }
        options[options.length-2] = "Exit";
        options[options.length-1] = "Shutdown";

        responsibilities = options;
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
     * Displays options for creating a new client
     *
     * @param m the input manager handling this
     */
    private void inputCreateUser(InputManager m) {
        String[] attributes = {"Name", "Email", "Phone", "Username", "Password", "Confirm Password"};
        String[] userTypes = {"Client", "Employee"};
        m.setPanel(new TextInputForm("Create new user", attributes, 2, userTypes) {
            @Override
            public void onCancel() {
                handleInput(m);
            }

            @Override
            public void onOk(String[] strings) {
                if (!strings[4].equals(strings[5])) {
                    m.setPanel(new AlertMessageForm("Passwords didn't match!") {
                        @Override
                        public void onOK() {
                            inputCreateUser(m);
                        }
                    });
                } else if (strings[0].equals("") || strings[1].equals("") || strings[2].equals("") || strings[3].equals("") || strings[4].equals("")){
                    m.setPanel(new AlertMessageForm("Can't leave any field empty!") {
                        @Override
                        public void onOK() {
                            inputCreateUser(m);
                        }
                    });
                } else if (strings[6] == null) {
                    m.setPanel(new AlertMessageForm("Must select type of User") {
                        @Override
                        public void onOK() {
                            inputCreateUser(m);
                        }
                    });
                } else {
                    BankMachineUser user;
                    if (strings[6].equals("Client")) {
                        user = BankMachine.USER_MANAGER.newClient(strings[0], strings[1], strings[2], strings[3], strings[4]);
                    } else {
                        user = BankMachine.USER_MANAGER.newEmployee(strings[0], strings[1], strings[2], strings[3], strings[4]);
                    }
//                    Client client1 = BankMachine.USER_MANAGER.newClient(strings[0], strings[1], strings[2], strings[3], strings[4]);

                    String alertMessage;
                    if (user == null) {
                        System.out.println("A user with that username exists!");
                        alertMessage = "A user with that username exists!";

                    } else {
                        System.out.println("User created");
                        alertMessage = "User created";
                    }
                    m.setPanel(new AlertMessageForm(alertMessage) {
                        @Override
                        public void onOK() {
                            handleInput(m);
                        }
                    });
                }
            }
        });
    }


    void handleSelection(InputManager m, String s) {
        super.handleSelection(m, s);
        switch (s) {
            case "Shutdown":
                m.exit();
                return;
            case "Undo a Transaction":
                inputUndoTransaction(m);
                return;
            case "Set Time":
                inputGetTime(m);
                return;
            case "Create User":
                inputCreateUser(m);
                return;
            default:
        }
    }
}
