package bankmachine.gui.personalGUIHandlers;

import bankmachine.BankMachine;
import bankmachine.gui.*;
import bankmachine.users.BankManager;
import bankmachine.users.Client;

public class NewCreationRequestGUIHandler {
    /**
     * The GUI of the client using the system
     */
    private PersonalGUI gui;
    /**
     * The client using the system
     */
    private Client client;

    public NewCreationRequestGUIHandler(PersonalGUI gui, Client client) {
        this.gui = gui;
        this.client = client;
    }

    /**
     * Creates new accounts for the client
     * @param m the InputManager that displays the GUI and accepts input
     */
    public void handleNewAccountCreationInput(InputManager m) {
        String[] accountTypes = {"Chequing account", "Credit card account",
                "Line of credit account", "Savings account", "Retirement account"};
        m.setPanel(new SearchForm("Select type of account", new OptionsForm<String>(accountTypes, "") {
            @Override
            public void onSelection(String s) {
                addCreationRequest(s, m);
            }
        }.getMainPanel()) {
            @Override
            public void onCancel() {
                gui.handleInput(m);
            }
        });
    }

    /**
     * Adds a Creation Request for an account
     * @param request the request for an account being processed
     * @param m the InputManager that displays the GUI and accepts input
     */
    private void addCreationRequest(String request, InputManager m) {
        BankManager manager = BankMachine.USER_MANAGER.getBankManagers().get(0);
        manager.addCreationRequest(client.getUsername() + " requests a " + request);
        m.setPanel(new AlertMessageForm("Account Creation Request Sent") {
            @Override
            public void onOK() {
                gui.handleInput(m);
            }
        });
    }
}
