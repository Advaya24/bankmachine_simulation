package bankmachine.gui;

import bankmachine.BankMachine;
import bankmachine.account.Account;
import bankmachine.users.BankManager;
import bankmachine.users.Client;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientGUI implements Inputtable {
    private Client client;
    public ClientGUI(Client c){
        this.client = c;
    }

    public void newAccountCreationInput(InputManager m){
        System.out.println("Choose a type of account:");
        List<String> accTypes = new ArrayList<>(Arrays.asList(
                "Chequing account", "Credit card account",
                "Line of credit account", "Savings account", "Cancel"
        ));
        String selection = m.selectItem(accTypes);
        if (selection.equals("Cancel")){
            return;
        }
        BankManager manager = BankMachine.USER_MANAGER.getBankManagers().get(0);
        manager.addCreationRequest(client.getUsername() + " requests a " + selection);
    }

    public void handleSelection(InputManager m, String s){
        switch (s){
            case "Exit": m.mainLoop(); return;
            case "Request Creation Of A New Account":
                newAccountCreationInput(m);
                break;
            case "Settings": new UserGUI(client).handleInput(m); break;
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
//        while (true){ // Commented loop out because it caused an infinite loop
            System.out.println("Select an action");
            String[] options = {
                "Accounts", "Request Creation Of A New Account", "Settings", "Exit"
            };
            m.setPanel(new OptionsForm<String>(options, "What would you like to do?") {
                @Override
                public void onSelection(String s) {
                    handleSelection(m, s);
                }
            });
//        }
    }
}
