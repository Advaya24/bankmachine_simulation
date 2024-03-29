package bankmachine.gui.personalGUIHandlers;

import bankmachine.BankMachine;
import bankmachine.account.Account;
import bankmachine.exception.AccountAlreadyOwnedException;
import bankmachine.gui.*;
import bankmachine.users.Client;

public class AddUserGUIHandler {
    /**
     * The GUI of the client using the system
     */
    private PersonalGUI gui;
    /**
     * The client using the system
     */
    private Client client;

    public AddUserGUIHandler(PersonalGUI gui, Client client) {
        this.gui = gui;
        this.client = client;
    }

    /**
     * Displays accounts and takes selection for the account where the user is to be added
     *
     * @param m the InputManager that displays the GUI and accepts input
     */
    public void handleAddUser(InputManager m) {
        Account[] accounts = new Account[this.client.getClientsAccounts().size()];
        this.client.getClientsAccounts().toArray(accounts);

        m.setPanel(new SearchForm("Select account to add user to", new OptionsForm<Account>(accounts, "") {
            @Override
            public void onSelection(Account account) {
                addUserTo(account, m, client);
            }
        }.getMainPanel()) {
            @Override
            public void onCancel() {
                gui.handleInput(m);
            }
        });
    }

    /**
     * Adds user (taking username as keyboard input) to selected account
     *
     * @param account the account to which the user is added
     * @param m       the InputManager that displays the GUI and accepts input
     * @param user    the user who wants to add a new user
     */
    private void addUserTo(Account account, InputManager m, Client user) {
        String[] attributes = {"Username of user to add"};
        m.setPanel(new TextInputForm("Add user to " + account.toString(), attributes) {
            @Override
            public void onCancel() {
                new AddUserGUIHandler(gui, user).handleAddUser(m);
            }

            @Override
            public void onOk(String[] strings) {
                try {
                    client = (Client) BankMachine.USER_MANAGER.get(strings[0]);
                } catch (NullPointerException e) {
                    m.setPanel(new AlertMessageForm("Invalid input for username!") {
                        @Override
                        public void onOK() {
                            addUserTo(account, m, user);
                        }
                    });
                }

                if (client == null) {
                    m.setPanel(new AlertMessageForm("User not found") {
                        @Override
                        public void onOK() {
                            addUserTo(account, m, user);
                        }
                    });
                } else {
                    try {
                        account.addSecondaryClient(client);
                        m.setPanel(new AlertMessageForm("Successfully added " + client.toString()) {
                            @Override
                            public void onOK() {
                                gui.handleInput(m);
                            }
                        });
                    } catch (NullPointerException e) {
                        m.setPanel(new AlertMessageForm("Cannot add user to this type of account!") {
                            @Override
                            public void onOK() {
                                handleAddUser(m);
                            }
                        });
                    } catch (AccountAlreadyOwnedException e) {
                        m.setPanel(new AlertMessageForm(e.toString()) {
                            @Override
                            public void onOK() {
                                addUserTo(account, m, user);
                            }
                        });
                    }
                }
            }
        });
    }
}
