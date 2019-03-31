package bankmachine.gui;

import bankmachine.BankMachine;
import bankmachine.gui.bankEmployeeGUIHandlers.AccountCreationGUIHandler;
import bankmachine.gui.bankEmployeeGUIHandlers.AddBillsGUIHandler;
import bankmachine.gui.bankEmployeeGUIHandlers.CreationRequestsGUIHandler;
import bankmachine.users.BankEmployee;
import bankmachine.users.BankMachineUser;
import bankmachine.users.Client;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class BankEmployeeGUI implements Inputtable {
    /**
     * the Bank Employee currently using the system
     */
    private BankEmployee employee;

    public BankEmployeeGUI(BankEmployee employee) {
        this.employee = employee;
    }

    /**
     * A list of responsibilities that a Bank Employee has
     */
    String[] responsibilities = {
            "View Account Creation Requests", "Remove Completed Creation Requests",
            "Create Account", "Add Bills", "Logout"
    };

    /**
     * Search client by username
     *
     * @param m        the input manager handling this
     * @param function a function which takes a Client and returns nothing
     * @return returns the selected client, null if there are no clients
     */
    public void handleSearchClient(InputManager m, Function<Client, Void> function) {
        List<Client> users = new ArrayList<>();
        for (BankMachineUser b : BankMachine.USER_MANAGER.getInstances()) {
            if (b instanceof Client) {
                users.add((Client) b);
            }
        }
        String prompt;
        JPanel panel = null;
        if (users.size() == 0) {
            prompt = "There are no users!";
        } else {
            prompt = "Select a user";
            OptionsForm<Object> optionsForm = new OptionsForm<Object>(users.toArray(), "") {
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
     * Determines what behaviour needs to be executed based on the user input.
     * @param m InputManager object that is used to accept input
     * @param s represents the User Input
     */
    void handleSelection(InputManager m, String s) {
        switch (s) {
            case "Logout":
                m.mainLoop();
                return;
            case "Add Bills":
                new AddBillsGUIHandler(this).handleAddBills(m);
                return;
            case "Create Account":
                new AccountCreationGUIHandler(this, this.employee).handleCreateAccount(m);
                return;
            case "View Account Creation Requests":
                new CreationRequestsGUIHandler(this, this.employee).showCreationRequests(m);
                return;
            case "Remove Completed Creation Requests":
                new CreationRequestsGUIHandler(this, this.employee).removeCompletedRequests(m);
                return;
            default:
                break;
        }
        handleInput(m);
    }

    /**
     * When called, accepts an input from the user and calls the method to handle that input.
     * @param m InputManager object that is used to accept input
     */
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
