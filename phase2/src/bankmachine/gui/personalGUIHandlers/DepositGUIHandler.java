package bankmachine.gui.personalGUIHandlers;

import bankmachine.account.Account;
import bankmachine.exception.NegativeQuantityException;
import bankmachine.exception.NoDepositException;
import bankmachine.gui.*;
import bankmachine.users.Client;

public class DepositGUIHandler {
    /**
     * The GUI of the client using the system
     */
    private PersonalGUI gui;
    /**
     * The client using the system
     */
    private Client client;

    public DepositGUIHandler(PersonalGUI gui, Client client) {
        this.gui = gui;
        this.client = client;
    }

    /**
     * Deposits money in the system
     * @param m the InputManager that displays the GUI and accepts input
     */
    public void handleDeposit(InputManager m) {
        Account[] accounts = new Account[this.client.getClientsAccounts().size()];
        this.client.getClientsAccounts().toArray(accounts);

        m.setPanel(new SearchForm("Select account to deposit money to", new OptionsForm<Account>(accounts, "") {
            @Override
            public void onSelection(Account account) {
                try {
                    account.deposit();
                    m.setPanel(new AlertMessageForm("Success!") {
                        @Override
                        public void onOK() {
                            gui.handleInput(m);
                        }
                    });
                } catch (NegativeQuantityException | NoDepositException e) {
                    m.setPanel(new AlertMessageForm(e.toString()) {
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
