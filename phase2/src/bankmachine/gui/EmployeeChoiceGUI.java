package bankmachine.gui;

import bankmachine.users.BankEmployee;
import bankmachine.users.BankManager;

public class EmployeeChoiceGUI implements Inputtable {
    private BankEmployee employee;

    public EmployeeChoiceGUI(BankEmployee employee) {
        this.employee = employee;
    }

    @Override
    public void handleInput(InputManager m) {
        String[] options = {"Personal", "Bank"};
        m.setPanel(new OptionsForm<String>(options, "Which services do you wish to access?") {
            @Override
            public void onSelection(String s) {
                if (s.equals("Personal")) {
                    new ClientGUI(employee).handleInput(m);
                } else if (employee instanceof BankManager) {
                    new BankManagerGUI((BankManager) employee).handleInput(m);
                } else {
                    new BankEmployeeGUI(employee).handleInput(m);
                }
            }
        });
    }
}
