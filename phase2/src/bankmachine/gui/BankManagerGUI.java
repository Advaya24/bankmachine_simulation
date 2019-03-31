package bankmachine.gui;

import bankmachine.BankMachine;
import bankmachine.gui.bankManagerGUIHandlers.DateTimeGUIHandler;
import bankmachine.gui.bankManagerGUIHandlers.TransactionGUIHandler;
import bankmachine.gui.bankManagerGUIHandlers.UserCreationGUIHandler;
import bankmachine.users.BankManager;

public class BankManagerGUI extends BankEmployeeGUI {
    private BankManager manager;
    @SuppressWarnings("FieldCanBeLocal")
    private final String[] specialResponsibilities = {
            "Run Monthly Functions", "Create User", "Set Time", "Undo a Transaction", "Shutdown"
    };

    public BankManagerGUI(BankManager b) {
        super(b);
        manager = b;
        String[] options = new String[responsibilities.length + specialResponsibilities.length];

        for (int i = 0; i < responsibilities.length; i++) {
            if (!responsibilities[i].equals("Logout"))
                options[i] = responsibilities[i];
        }
        for (int i = 0; i < specialResponsibilities.length; i++) {
            if (!specialResponsibilities[i].equals("Shutdown")) {
                options[i + responsibilities.length - 1] = specialResponsibilities[i];
            }
        }
        options[options.length - 2] = "Logout";
        options[options.length - 1] = "Shutdown";

        responsibilities = options;
    }

    /**
     * Determines what behaviour needs to be executed based on the user input.
     * @param m InputManager object that is used to accept input
     * @param s represents the User Input
     */
    void handleSelection(InputManager m, String s) {
        super.handleSelection(m, s);
        switch (s) {
            case "Shutdown":
                m.exit();
                return;
            case "Run Monthly Functions":
                BankMachine.executeEveryMonth();
                m.setPanel(new AlertMessageForm("Done!") {
                    @Override
                    public void onOK() {
                        handleInput(m);
                    }
                });
                return;
            case "Undo a Transaction":
                new TransactionGUIHandler(this, this.manager).handleUndoTransaction(m);
                return;
            case "Set Time":
                new DateTimeGUIHandler(this).handleGetDateTime(m);
                return;
            case "Create User":
                new UserCreationGUIHandler(this).handleUserCreation(m);
                return;
            default:
                break;
        }
    }
}
