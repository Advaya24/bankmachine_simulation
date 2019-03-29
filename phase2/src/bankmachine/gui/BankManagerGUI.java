package bankmachine.gui;

import bankmachine.gui.bankManagerGUIHandlers.DateTimeGUIHandler;
import bankmachine.gui.bankManagerGUIHandlers.TransactionGUIHandler;
import bankmachine.gui.bankManagerGUIHandlers.UserCreationGUIHandler;
import bankmachine.users.BankManager;

public class BankManagerGUI extends BankEmployeeGUI {
    private BankManager manager;
    @SuppressWarnings("FieldCanBeLocal")
    private final String[] specialResponsibilities = {
            "Create User", "Set Time", "Undo a Transaction", "Shutdown"
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


    void handleSelection(InputManager m, String s) {
        super.handleSelection(m, s);
        switch (s) {
            case "Shutdown":
                m.exit();
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
        }
    }
}
