package bankmachine.gui.bankManagerGUIHandlers;

import bankmachine.account.Account;
import bankmachine.exception.BankMachineException;
import bankmachine.gui.*;
import bankmachine.transaction.Transaction;
import bankmachine.users.BankManager;
import bankmachine.users.Client;

public class TransactionGUIHandler {
    private BankManagerGUI gui;
    private BankManager manager;

    public TransactionGUIHandler(BankManagerGUI gui, BankManager manager) {
        this.gui = gui;
        this.manager = manager;
    }

    public void handleUndoTransaction(InputManager m) {
        gui.handleSearchClient(m, (Client client) -> {
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
                    gui.handleInput(m);
                }
            });
        } else {
            System.out.println("Select an account");
            m.setPanel(new SearchForm("Select an account", new OptionsForm<Object>(client.getClientsAccounts().toArray(), "") {
                @Override
                public void onSelection(Object object) {
                    inputGetTransactionFor((Account) object, m);
                }
            }.getMainPanel()) {
                @Override
                public void onCancel() {
                    gui.handleInput(m);
                }
            });
        }
    }

    private void inputGetTransactionFor(Account account, InputManager m) {
        //TODO: Change this if statement. The way it currently works, we're not
        if (account.getTransactions().size() == 0) {
            m.setPanel(new AlertMessageForm("There are no transactions!") {
                @Override
                public void onOK() {
                    gui.handleInput(m);
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
                                gui.handleInput(m);
                            }
                        });
                    } catch (BankMachineException e) {
                        e.printStackTrace();
                        m.setPanel(new AlertMessageForm("Failure!") {
                            @Override
                            public void onOK() {
                                gui.handleInput(m);
                            }
                        });
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
}
