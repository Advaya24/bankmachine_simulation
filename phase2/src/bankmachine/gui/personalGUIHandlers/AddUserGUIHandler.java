package bankmachine.gui.personalGUIHandlers;

import bankmachine.BankMachine;
import bankmachine.account.Account;
import bankmachine.gui.*;
import bankmachine.users.Client;

public class AddUserGUIHandler {
    private PersonalGUI gui;
    private Client client;

    public AddUserGUIHandler(PersonalGUI gui, Client client) {
        this.gui = gui;
        this.client = client;
    }

    public void handleAddUser(InputManager m) {
        Account[] accounts = new Account[this.client.getClientsAccounts().size()];
        this.client.getClientsAccounts().toArray(accounts);

        m.setPanel(new SearchForm("Select account to add user to", new OptionsForm<Account>(accounts, ""){
            @Override
            public void onSelection(Account account) {
                addUserTo(account, m);
            }
        }.getMainPanel()) {
            @Override
            public void onCancel() {
                gui.handleInput(m);
            }
        });
    }
    private void addUserTo(Account account, InputManager m) {
        String[] attributes = {"Username of user to add"};
        m.setPanel(new TextInputForm("Add user to " + account.toString(), attributes) {
            @Override
            public void onCancel() {
                handleAddUser(m);
            }

            @Override
            public void onOk(String[] strings) {
                try {
                    client = (Client) BankMachine.USER_MANAGER.get(strings[0]);
                } catch (NullPointerException e) {
                    m.setPanel(new AlertMessageForm("Invalid input for username!") {
                        @Override
                        public void onOK() {
                            addUserTo(account, m);
                        }
                    });
                }

                if (client == null) {
                    m.setPanel(new AlertMessageForm("User not found") {
                        @Override
                        public void onOK() {
                            addUserTo(account, m);
                        }
                    });
                } else {
                    account.addSecondaryClient(client);
                    m.setPanel(new AlertMessageForm("Successfully added " + client.toString()) {
                        @Override
                        public void onOK() {
                            gui.handleInput(m);
                        }
                    });
                }
            }
        });
    }
}
