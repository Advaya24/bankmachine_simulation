package bankmachine.gui;

import bankmachine.BankMachine;
import bankmachine.account.Account;
import bankmachine.users.BankManager;
import bankmachine.users.Client;

import javax.swing.*;

public class PersonalGUI implements Inputtable {
    private Client client;
    public PersonalGUI(Client c){
        this.client = c;
    }

    public void newAccountCreationInput(InputManager m){
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
                handleInput(m);
            }
        });
    }
    private void addCreationRequest(String request, InputManager m) {
        BankManager manager = BankMachine.USER_MANAGER.getBankManagers().get(0);
        manager.addCreationRequest(client.getUsername() + " requests a " + request);
        m.setPanel(new AlertMessageForm("Account Creation Request Sent") {
            @Override
            public void onOK() {
                handleInput(m);
            }
        });
    }

    public void handleSelection(InputManager m, String s){
        switch (s){
            case "Exit": m.mainLoop(); return;
            case "Request Creation Of A New Account":
                newAccountCreationInput(m);
                return;
            case "Update Profile": new UpdateProfileGUI(client, this).handleInput(m); return;
            case "Accounts":
                client.printAccountSummary();
                JPanel panel = null;
                String[] accountSummary = client.getAccountSummary().clone();
                if (client.getClientsAccounts().size() > 0) {
                    panel = new OptionsForm<Object>(client.getClientsAccounts().toArray(), ""){
                        @Override
                        public void onSelection(Object obj) {
                            handleAccount(m, (Account)obj);
                        }
                    }.getMainPanel();
                } else {
                    String[] tempAccountSummary = new String[accountSummary.length + 1];
                    for (int i = 0; i < accountSummary.length; i++) {
                        tempAccountSummary[i] = accountSummary[i];
                    }
                    tempAccountSummary[accountSummary.length] = "No accounts to show";
                    accountSummary = tempAccountSummary;
                }
                m.setPanel(new AccountSummaryForm(accountSummary, panel) {
                    @Override
                    public void onCancel() {
                        handleInput(m);
                    }
                });
//                System.out.println("Please select an account:");
//                Account account = m.selectItem(client.getClientsAccounts());

                return;
            default:
                break;
        }
        handleInput(m);
    }

    private void handleAccount(InputManager m, Account account) {
        if(account==null){
            return;
        }
        new AccountGUI(account).handleInput(m);
    }


    @Override
    public void handleInput(InputManager m){
        System.out.println("Welcome, "+client.getName()+"!");
            System.out.println("Select an action");
            String[] options = {
                "Accounts", "Request Creation Of A New Account", "Update Profile", "Exit"
            };
            m.setPanel(new OptionsForm<String>(options, "What would you like to do?") {
                @Override
                public void onSelection(String s) {
                    handleSelection(m, s);
                }
            });

    }
}
