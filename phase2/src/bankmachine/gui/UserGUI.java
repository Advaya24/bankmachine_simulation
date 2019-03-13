package bankmachine.gui;

import bankmachine.BankMachineUser;
import bankmachine.InputManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserGUI implements Inputtable{
    private BankMachineUser user;
    public UserGUI(BankMachineUser user){
        this.user = user;
    }

    /**
     * Presents setting options for the user
     *
     * @param m the input manager that handles this
     */
    @Override
    public void handleInput(InputManager m) {
        List<String> options = new ArrayList<>(Arrays.asList(
                "Phone Number", "Email", "Password", "Cancel"
        ));
        System.out.println("Select an option");
        String action = m.selectItem(options);
        if (action.equals("Cancel")) {
            return;
        }
        String value;
        switch (action) {
            case "Phone Number":
                value = m.getPhone();
                user.setPhoneNumber(value);
                break;
            case "Email":
                value = m.getEmail();
                user.setEmail(value);
                break;
            case "Password":
                value = m.getInput("Enter a new password");
                user.setPassword(value);
                break;
            default:
                return;
        }
        System.out.println("Set new " + action + " to " + value);
    }
}
