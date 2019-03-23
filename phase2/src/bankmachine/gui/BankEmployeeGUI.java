package bankmachine.gui;

import bankmachine.BankMachine;
import bankmachine.users.BankEmployee;
import bankmachine.users.BankMachineUser;
import bankmachine.users.Client;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class BankEmployeeGUI implements Inputtable {
    private BankEmployee employee;

    public BankEmployeeGUI(BankEmployee employee) {
        this.employee = employee;
    }


    String[] responsibilities = {
            "View Account Creation Requests", "Remove Completed Creation Requests",
            "Create Account", "Add Bills",
            "Settings", "Exit"
    };

    /**
     * Displays options for adding bills to the bankmachine
     *
     * @param m the input manager handling this
     */
    private void inputAddBills(InputManager m) {
        int[] denominations = {5, 10, 20, 50};
        String[] attributes = new String[denominations.length];
        for (int i = 0; i < denominations.length; i++) {
            attributes[i] =  denominations[i] + "s";
        }
        m.setPanel(new TextInputForm("How many of each?", attributes) {
            @Override
            public void onCancel() {
                handleInput(m);
            }

            @Override
            public void onOk(String[] strings) {
                int[] arrayNumBillsToAdd = new int[strings.length];
                for (int i = 0; i < strings.length; i++) {
                    try {
                        int numBills = Integer.parseInt(strings[i]);
                        if (numBills > 0) {
                            arrayNumBillsToAdd[i] = numBills;
                        } else {
                            throw new NumberFormatException();
                        }
                    } catch (NumberFormatException e) {
                        m.setPanel(new AlertMessageForm("Invalid input for at least one denomination!") {
                            @Override
                            public void onOK() {
                                inputAddBills(m);
                            }
                        });
                        return;
                    }
                }
                for (int i = 0; i < arrayNumBillsToAdd.length; i++) {
                    BankMachine.getBillManager().addBills(denominations[i], arrayNumBillsToAdd[i]);
                }
                m.setPanel(new AlertMessageForm("Success!") {
                    @Override
                    public void onOK() {
                        handleInput(m);
                    }
                });
            }
        });
    }

    /**
     * Displays all clients and allows the employee to select a client
     *
     * @param m the input manager handling this
     * @param function a function which takes a Client and returns nothing
     * @return returns the selected client, null if there are no clients
     */
    void inputGetClient(InputManager m, Function<Client, Void> function) {
        List<Client> clients = new ArrayList<>();
        for (BankMachineUser b : BankMachine.USER_MANAGER.getInstances()) {
            if (b instanceof Client) {
                clients.add((Client) b);
            }
        }
        String prompt;
        JPanel panel = null;
        if (clients.size() == 0) {
            prompt = "There are no clients!";
        } else {
            prompt = "Select a client";
            OptionsForm<Object> optionsForm = new OptionsForm<Object>(clients.toArray(), "") {
                @Override
                public void onSelection(Object object) {
                    function.apply((Client) object);
                }
            };
            panel = optionsForm.getMainPanel();
        }
        m.setPanel(new SearchForm(prompt, panel) {
            @Override
            public void onCancel() {
                handleInput(m);
            }
        });
    }

    /**
     * Displays options for creating a new account
     *
     * @param m the input manager handling this
     */
    private void inputCreateAccount(InputManager m) {
        inputGetClient(m, (Client client1) -> {
            createAccountFor(client1, m);
            return null;
        });
    }

    private void createAccountFor(Client client, InputManager m) {
        String[] accountTypes = {"Chequing account", "Credit card account", "Line of credit account", "Savings account"};
        m.setPanel(new SearchForm("Select the type of account:", new OptionsForm<String>(accountTypes, "") {
            @Override
            public void onSelection(String s) {
                String alertMessage;
                if (employee.createAccount(client, s, LocalDateTime.now())) {
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
        }.getMainPanel()) {
            @Override
            public void onCancel() {
                handleInput(m);
            }
        });
    }

    private void showCreationRequests(InputManager m) {

        if (employee.getCreationRequestArray().length == 0) {
            m.setPanel(new AlertMessageForm("No Pending Requests!") {
                @Override
                public void onOK() {
                    handleInput(m);
                }
            });
        } else {
            m.setPanel(new AccountSummaryForm(employee.getCreationRequestArray(), new JPanel()) {
                @Override
                public void onCancel() {
                    handleInput(m);
                }
            });
        }

    }

    /**
     * Displays outstanding account creation requests and allows employee to choose one to remove
     *
     * @param m the input manager handling this
     */
    public void removeCompletedRequests(InputManager m) {
        if (employee.getCreationRequests().size() == 0) {
            System.out.println("No pending creation requests");
        } else {
            employee.getCreationRequests().remove(m.selectItem(employee.getCreationRequests()));
        }
    }

    void handleSelection(InputManager m, String s) {
        switch (s) {
            case "Exit":
                m.mainLoop();
                return;
            case "Settings":
                new UserGUI(employee).handleInput(m);
                return;
            case "Add Bills":
                inputAddBills(m);
                return;
            case "Create Account":
                inputCreateAccount(m);
                return;
            case "View Account Creation Requests":
                employee.viewAccountCreationRequests();
                showCreationRequests(m);
                return;
            case "Remove Completed Creation Requests":
                removeCompletedRequests(m);
                break;
            default:
                break;
        }
        handleInput(m);
    }

    @Override
    public void handleInput(InputManager m) {
        m.setPanel(new OptionsForm<String>(responsibilities, "What would you like to do?") {
            @Override
            public void onSelection(String s) {
                handleSelection(m, s);
            }
        });
    }
}
