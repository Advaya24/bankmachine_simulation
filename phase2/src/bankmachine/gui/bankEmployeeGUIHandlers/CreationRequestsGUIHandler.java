package bankmachine.gui.bankEmployeeGUIHandlers;

import bankmachine.gui.*;
import bankmachine.users.BankEmployee;

import javax.swing.*;

public class CreationRequestsGUIHandler {
    /**
     * The GUI of the Bank Employee using the system
     */
    private BankEmployeeGUI gui;
    /**
     * The current Bank Employee using the system
     */
    private BankEmployee employee;

    public CreationRequestsGUIHandler(BankEmployeeGUI gui, BankEmployee employee) {
        this.gui = gui;
        this.employee = employee;
    }

    /**
     * Shows all creation requests
     *
     * @param m the InputManager that displays the GUI and accepts input
     */
    public void showCreationRequests(InputManager m) {

        if (employee.getCreationRequestArray().length == 0) {
            m.setPanel(new AlertMessageForm("No Pending Requests!") {
                @Override
                public void onOK() {
                    gui.handleInput(m);
                }
            });
        } else {
            m.setPanel(new AccountSummaryForm(employee.getCreationRequestArray(), new JPanel()) {
                @Override
                public void onCancel() {
                    gui.handleInput(m);
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
            m.setPanel(new AlertMessageForm("No pending creation requests!") {
                @Override
                public void onOK() {
                    gui.handleInput(m);
                }
            });
        } else {
            String[] options = new String[employee.getCreationRequests().size()];
            for (int i = 0; i < options.length; i++) {
                options[i] = "Request " + (i + 1);
            }

            m.setPanel(new AccountSummaryForm(employee.getCreationRequestArray(), new OptionsForm<String>(options, "Select request to delete:") {

                @Override
                public void onSelection(String s) {
                    String alertMessage = "Failure";
                    for (int i = 0; i < options.length; i++) {
                        if (options[i].equals(s)) {
                            employee.removeCreationRequest(i);
                            alertMessage = "Success";
                        }
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
}
