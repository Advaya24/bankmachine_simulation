package bankmachine.gui.bankManagerGUIHandlers;

import bankmachine.account.Account;
import bankmachine.exception.BankMachineException;
import bankmachine.gui.*;
import bankmachine.transaction.Transaction;
import bankmachine.users.BankManager;
import bankmachine.users.Client;

public class TransactionGUIHandler {
    /**
     * The GUI of the Bank Manager using the system
     */
    private BankManagerGUI gui;

    public TransactionGUIHandler(BankManagerGUI gui) {
        this.gui = gui;
    }

    /**
     *  Handles undoing a transaction
     * @param m the InputManager that displays the GUI and accepts input
     */
    public void handleUndoTransaction(InputManager m) {
        gui.handleSearchClient(m, (Client client) -> {
            undoTransactionsForClient(client, m);
            return null;
        });

    }

    /**
     * Displays all accounts for client and allows them to choose account to undo transaction from
     * @param client the client that the transactions are being undone for
     * @param m the InputManager that displays the GUI and accepts input
     */
    private void undoTransactionsForClient(Client client, InputManager m) {
        if (client.getClientsAccounts().size() == 0) {
            m.setPanel(new AlertMessageForm("There are no accounts") {
                @Override
                public void onOK() {
                    gui.handleInput(m);
                }
            });
        } else {
            m.setPanel(new SearchForm("Select an account", new OptionsForm<Object>(client.getClientsAccounts().toArray(), "") {
                @Override
                public void onSelection(Object object) {
                    inputGetTransactionToUndoFor((Account) object, m);
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
     * Shows transactions from account and gets the transaction to be deleted
     * @param account the account we're getting the transactions on
     * @param m the InputManager that displays the GUI and accepts input
     */
    private void inputGetTransactionToUndoFor(Account account, InputManager m) {
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
                        ((Transaction) o).undo();
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
