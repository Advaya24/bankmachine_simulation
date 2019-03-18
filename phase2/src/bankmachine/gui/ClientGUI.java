package bankmachine.gui;

import bankmachine.BankMachine;
import bankmachine.users.BankManager;
import bankmachine.users.Client;
import bankmachine.account.Account;

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
                m.setPanel(new OptionsForm<Account>((Account[])client.getClientsAccounts().toArray()) {
                    @Override
                    public void onSelection(Account account) {
                        new AccountGUI(account).handleInput(m);
                    }
                });
                break;
            default:
                break;
        }
        handleInput(m);
    }

    @Override
    public void handleInput(InputManager m){
        System.out.println("Welcome, "+client.getName()+"!");
        while (true){
            System.out.println("Select an action");
            String[] options = {
                "Accounts", "Request Creation Of A New Account", "Settings", "Exit"
            };
            m.setPanel(new OptionsForm<String>(options) {
                @Override
                public void onSelection(String s) {
                    handleSelection(m, s);
                }
            });
        }
    }
}
