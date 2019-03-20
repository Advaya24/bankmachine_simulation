package bankmachine.gui;

import bankmachine.BankMachine;
import bankmachine.account.Account;
import bankmachine.exception.BankMachineException;
import bankmachine.exception.TransactionUndoException;
import bankmachine.transaction.Transaction;
import bankmachine.users.BankMachineUser;
import bankmachine.users.BankManager;
import bankmachine.users.Client;
import com.sun.istack.internal.Nullable;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BankManagerGUI implements Inputtable {
    private BankManager manager;

    public BankManagerGUI(BankManager b) {
        manager = b;
    }


    /**
     * Displays options for adding bills to the bankmachine
     *
     * @param m the input manager handling this
     */
    private void inputAddBills(InputManager m) {
        int[] denominations = {5, 10, 20, 50};
        for (int i : denominations) {
            int quantity = m.getInteger("How many " + i + "s? ");
            BankMachine.getBillManager().addBills(i, quantity);
        }
    }

    private void inputUndoTransaction(InputManager m) throws BankMachineException {
        Client client = inputGetClient(m);
        if (client == null) {
            return;
        }
        if (client.getClientsAccounts().size() == 0) {
            System.out.println("There are no accounts!");
            return;
        }
        System.out.println("Select an account");
        Account account = m.selectItem(client.getClientsAccounts());
        if (account.getTransactions().size() == 0) {
            System.out.println("There are no transactions!");
            return;
        }
        System.out.println("Select a transaction");
        Transaction transaction = m.selectItem(account.getTransactions());
        manager.undoRecentTransaction(transaction);
        System.out.println("Successful!");
    }

    private void inputSetTime(InputManager m) {
        BankMachine.getTimeInfo().setTime(m.getDate());
    }


    /**
     * Displays all clients and allows the manager to select a client
     *
     * @param m the input manager handling this
     * @return returns the selected client, null if there are no clients
     */
    @Nullable
    private Client inputGetClient(InputManager m) {
        List<Client> clients = new ArrayList<>();
        for (BankMachineUser b : BankMachine.USER_MANAGER.getInstances()) {
            if (b instanceof Client) {
                clients.add((Client) b);
            }
        }
        String prompt;
        JPanel panel = null;
        if (clients.size() == 0) {
            System.out.println("There are no clients!");
            prompt = "There are no clients!";

//            return null;
        } else {
            prompt = "Select a client";
            OptionsForm<Object> optionsForm = new OptionsForm<Object>(clients.toArray()) {
                @Override
                public void onSelection(Object object) {
                    createAccountFor(m, (Client) object);
                }
            };
            panel = optionsForm.getMainPanel();
        }
        System.out.println("Select a client");
        m.setPanel(new ClientSearchForm(prompt, panel));
        return null;
    }

    private void createAccountFor(InputManager m, Client client) {
        String[] accountTypes = {"Chequing account", "Credit card account", "Line of credit account", "Savings account", "Cancel"};
        m.setPanel(new OptionsForm<String>(accountTypes) {
            @Override
            public void onSelection(String s) {
                String alertMessage = "";
                if (manager.createAccount(client, s, LocalDateTime.now())) {
                    alertMessage = "Account created.";
                } else {
                    alertMessage = "There was some problem with the creation. Try again later.";
                }
                m.setPanel(new AlertMessageForm(alertMessage) {
                    @Override
                    public void onOK() {
                        handleInput(m);
                    }
                });
            }
        });
    }

    /**
     * Displays options for creating a new account
     *
     * @param m the input manager handling this
     */
    private void inputCreateAccount(InputManager m) {
        Client client = inputGetClient(m);
        if (client == null) {
            return;
        }
        List<String> accTypes = new ArrayList<>(Arrays.asList(
                "Chequing account", "Credit card account",
                "Line of credit account", "Savings account", "Cancel"
        ));

        String selection = m.selectItem(accTypes);
        if (selection.equals("Cancel")) {
            return;
        }
        if (manager.createAccount(client, selection, LocalDateTime.now())) {
            System.out.println("Account created successfully");
        }
    }

    /**
     * Displays outstanding account creation requests and allows manager to choose one to remove
     *
     * @param m the input manager handling this
     */
    public void removeCompletedRequests(InputManager m) {
        if (manager.getCreationRequests().size() == 0) {
            System.out.println("No pending creation requests");
        } else {
            manager.getCreationRequests().remove(m.selectItem(manager.getCreationRequests()));
        }
    }

    /**
     * Displays options for creating a new client
     *
     * @param m the input manager handling this
     */
    private void inputCreateClient(InputManager m) {
//        String name = m.getInput("Enter a name: ");
//        String username = m.getInput("Enter a username: ");
//        String phone = m.getPhone();
//        String email = m.getEmail();
//        String pwd = m.getInput("Enter a password: ");
//        Client client = BankMachine.USER_MANAGER.newClient(name, email, phone, username, pwd);
//        if(client == null){
//            System.out.println("A client with that username exists!");
//        } else {
//            System.out.println("Client created");
//        }
        String[] attributes = {"Name", "Email", "Phone", "Username", "Password", "Confirm Password"};
        m.setPanel(new ClientCreationForm("Create new client", attributes) {
            @Override
            public void onCancel() {
                handleInput(m);
            }

            @Override
            public void onOk(String[] strings) {
                Client client1 = BankMachine.USER_MANAGER.newClient(strings[0], strings[1], strings[2], strings[3], strings[4]);
                String alertMessage;
                if (client1 == null) {
                    System.out.println("A client with that username exists!");
                    alertMessage = "A client with that username exists!";

                } else {
                    System.out.println("Client created");
                    alertMessage = "Client created";
                }
                m.setPanel(new AlertMessageForm(alertMessage) {
                    @Override
                    public void onOK() {
                        handleInput(m);
                    }
                });
            }
        });
    }

    private void handleSelection(InputManager m, String s) throws BankMachineException {
        switch (s) {
            case "Shutdown":
                m.exit();
                return;
            case "Exit":
                m.mainLoop();
                return;
            case "Settings":
                new UserGUI(manager).handleInput(m);
                break;
            case "Create Account":
                inputCreateAccount(m);
                return;
            case "Undo a Transaction":
                inputUndoTransaction(m);
                break;
            case "Set Time":
                inputSetTime(m);
                break;
            case "Create Client":
                inputCreateClient(m);
                return;
            case "Add Bills":
                inputAddBills(m);
                break;
            case "View Account Creation Requests":
                manager.viewAccountCreationRequests();
                break;
            case "Remove Completed Creation Requests":
                removeCompletedRequests(m);
                break;
            default:
                break;
        }
        handleInput(m);
    }

    /**
     * Handles the input from the bank manager
     *
     * @param m the input manager handling this
     */
    @Override
    public void handleInput(InputManager m) {
        System.out.println("Welcome, " + manager.getName() + "!");
        String[] options = {
                "View Account Creation Requests", "Remove Completed Creation Requests",
                "Create Account", "Create Client", "Set Time", "Undo a Transaction", "Add Bills",
                "Settings", "Exit", "Shutdown"
        };
        m.setPanel(new OptionsForm<String>(options) {
            @Override
            public void onSelection(String s) {
                try {
                    handleSelection(m, s);
                } catch (BankMachineException e) {
                    System.out.println(e.toString());
                    //TODO: handle exception
                }
            }
        });

    }
}
