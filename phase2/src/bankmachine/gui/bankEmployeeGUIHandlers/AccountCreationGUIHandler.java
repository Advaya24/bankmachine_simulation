package bankmachine.gui.bankEmployeeGUIHandlers;

import bankmachine.gui.*;
import bankmachine.users.BankEmployee;
import bankmachine.users.Client;

import java.time.LocalDateTime;

public class AccountCreationGUIHandler {
    /**
     * The GUI of the Bank Employee
     */
    private BankEmployeeGUI gui;
    /**
     * The Bank Employee currently using the system
     */
    private BankEmployee employee;

    public AccountCreationGUIHandler(BankEmployeeGUI gui, BankEmployee employee) {
        this.gui = gui;
        this.employee = employee;
    }

    /**
     * Displays options for creating a new account
     *
     * @param m the input manager handling this
     */
    public void handleCreateAccount(InputManager m) {
        gui.handleSearchClient(m, (Client client1) -> {
            createAccountFor(client1, m);
            return null;
        });
    }

    /**
     * Creates an account for the client
     *
     * @param client the client who the account is being created for
     * @param m      the InputManager that displays the GUI and accepts input
     */
    private void createAccountFor(Client client, InputManager m) {
        String[] accountTypes = {"Chequing account", "Credit card account", "Line of credit account", "Savings account", "Retirement account"};
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
                        gui.handleInput(m);
                    }
                });
            }
        }.getMainPanel()) {
            @Override
            public void onCancel() {
                gui.handleInput(m);
            }
        });
    }

}
