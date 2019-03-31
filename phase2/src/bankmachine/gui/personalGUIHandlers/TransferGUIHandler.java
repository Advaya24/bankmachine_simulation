package bankmachine.gui.personalGUIHandlers;

import bankmachine.BankMachine;
import bankmachine.account.Account;
import bankmachine.exception.BankMachineException;
import bankmachine.gui.*;
import bankmachine.transaction.TransactionType;
import bankmachine.users.Client;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransferGUIHandler {
    /**
     * The GUI of the client using the system
     */
    private PersonalGUI gui;
    /**
     * The client using the system
     */
    private Client client;

    public TransferGUIHandler(PersonalGUI gui, Client client) {
        this.gui = gui;
        this.client = client;
    }

    /**
     * Selects the account to transfer from
     * @param m the InputManager that displays the GUI and accepts input
     */
    private void selectAccountForTransfer(InputManager m) {
        Account[] accounts = getTransferAccounts(this.client);

        if (accounts.length == 0) {
            m.setPanel(new AlertMessageForm("No accounts to transfer from!") {
                @Override
                public void onOK() {
                    gui.handleInput(m);
                }
            });
        } else {
            m.setPanel(new SearchForm("Select account to transfer from:", new OptionsForm<Account>(accounts, "") {
                @Override
                public void onSelection(Account account) {
                    handleTransfer(m, account);
                }
            }.getMainPanel()) {
                @Override
                public void onCancel() {
                    gui.handleInput(m);
                }
            });
        }
    }

    /**
     * Gets accounts for the client that they can transfer money from
     * @param client the client the accounts are gotten from
     * @return an array of all eligible accounts for this client
     */
    public Account[] getTransferAccounts(Client client) {
        List<Account> listOfTransferAccounts = new ArrayList<>();
        for (Account account : client.getClientsAccounts()) {
            if (account.canTransferOut(0)) {
                listOfTransferAccounts.add(account);
            }
        }
        Account[] arrayOfTransferAccounts = new Account[listOfTransferAccounts.size()];
        listOfTransferAccounts.toArray(arrayOfTransferAccounts);
        return arrayOfTransferAccounts;
    }

    /**
     * Transfers money out of this account
     * @param m the InputManager that displays the GUI and accepts input
     * @param account the account to transfer from
     */
    private void handleTransfer(InputManager m, Account account) {
        String[] attributes = {"Username of recipient", "Amount to transfer"};
        m.setPanel(new TextInputForm("Transfer money", attributes) {
            @Override
            public void onCancel() {
                gui.handleInput(m);
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
                        amount = ((double) Math.round(amount * 100)) / 100.0;
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
                            gui.handleInput(m);
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

    /**
     * Transfers money between two accounts of this client
     * @param m the InputManager that displays the GUI and accepts input
     */
    private void handleInternalTransfer(InputManager m) {
        List<Account> accountList = client.getClientsAccounts();
        if (accountList.size() < 2) {
            m.setPanel(new AlertMessageForm("No account(s) to transfer between!") {
                @Override
                public void onOK() {
                    gui.handleInput(m);
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
                                gui.handleInput(m);
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
                    gui.handleInput(m);
                }
            });
        }
    }

    /**
     * Displays the menu for the transfers that are possible
     * @param m the InputManager that displays the GUI and accepts input
     */
    public void transferMenu(InputManager m) {
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
                gui.handleInput(m);
            }
        });
    }

}
