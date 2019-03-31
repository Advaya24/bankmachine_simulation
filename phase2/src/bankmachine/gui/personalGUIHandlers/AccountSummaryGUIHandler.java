package bankmachine.gui.personalGUIHandlers;

import bankmachine.gui.AccountSummaryForm;
import bankmachine.gui.InputManager;
import bankmachine.gui.PersonalGUI;
import bankmachine.users.Client;

public class AccountSummaryGUIHandler {
    /**
     * The GUI of the client using the system
     */
    private PersonalGUI gui;
    /**
     * The client using the system
     */
    private Client client;

    public AccountSummaryGUIHandler(PersonalGUI gui, Client client) {
        this.gui = gui;
        this.client = client;
    }

    /**
     * Displays the account summary of the current client
     *
     * @param m the InputManager that displays the GUI and accepts input
     */
    public void handleAccountSummary(InputManager m) {
        m.setPanel(new AccountSummaryForm(client.getAccountSummary(), null) {
            @Override
            public void onCancel() {
                gui.handleInput(m);
            }
        });
    }
}
