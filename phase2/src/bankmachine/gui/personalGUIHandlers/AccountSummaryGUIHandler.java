package bankmachine.gui.personalGUIHandlers;

import bankmachine.gui.AccountSummaryForm;
import bankmachine.gui.InputManager;
import bankmachine.gui.PersonalGUI;
import bankmachine.users.Client;

public class AccountSummaryGUIHandler {
    private PersonalGUI gui;
    private Client client;

    public AccountSummaryGUIHandler(PersonalGUI gui, Client client) {
        this.gui = gui;
        this.client = client;
    }

    public void handleAccountSummary(InputManager m) {
        m.setPanel(new AccountSummaryForm(client.getAccountSummary(), null) {
            @Override
            public void onCancel() {
                gui.handleInput(m);
            }
        });
    }
}
