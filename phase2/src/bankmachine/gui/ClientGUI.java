package bankmachine.gui;

import bankmachine.BankMachine;
import bankmachine.BankManager;
import bankmachine.Client;
import bankmachine.account.Account;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientGUI implements Inputtable {
    private Client client;
    public ClientGUI(Client c){
        this.client = client;
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

    @Override
    public void handleInput(InputManager m){
        System.out.println("Welcome, "+client.getName()+"!");
        while (true){
            System.out.println("Select an action");
            List<String> options = new ArrayList<>(Arrays.asList(
                    "Accounts","Request Creation Of A New Account", "Settings", "Exit"
            ));
            String action = m.selectItem(options);
            switch (action){
                case "Exit": return;
                case "Request Creation Of A New Account":
                    newAccountCreationInput(m);
                    break;
                case "Settings": new UserGUI(client).handleInput(m); break;
                case "Accounts":
                    client.printAccountSummary();
                    System.out.println("Please select an account:");
                    Account account = m.selectItem(client.getClientsAccounts());
                    if(account==null){
                        break;
                    }
                    new AccountGUI(account).handleInput(m);
                    break;
                default:
                    break;
            }
        }
    }
}
