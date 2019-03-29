package bankmachine.gui.personalGUIHandlers;

import bankmachine.BankMachine;
import bankmachine.gui.*;
import bankmachine.users.BankManager;
import bankmachine.users.Client;

public class NewCreationRequestGUIHandler {
    private PersonalGUI gui;
    private Client client;

    public NewCreationRequestGUIHandler(PersonalGUI gui, Client client) {
        this.gui = gui;
        this.client = client;
    }

    public void handleNewAccountCreationInput(InputManager m){
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
                gui.handleInput(m);
            }
        });
    }
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
