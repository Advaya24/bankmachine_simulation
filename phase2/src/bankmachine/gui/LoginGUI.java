package bankmachine.gui;

import bankmachine.BankMachine;
import bankmachine.flappyFloof.FlappyFloof;
import bankmachine.users.BankEmployee;
import bankmachine.users.BankMachineUser;
import bankmachine.users.Client;
import bankmachine.users.UserManager;

public class LoginGUI implements Inputtable {
    /**
     * A UserManager object that is used to authenticate users
     */
    private UserManager userManager = BankMachine.USER_MANAGER;

    /**
     * Handles the log in functionality; checks if the username-password combination matches, and handles
     * the appropriate behaviour.
     *
     * @param m     the InputManager used to take in Input and Display the GUI of the application
     * @param form  the LoginForm used to display the required input fields
     * @param uname the username input by the user
     * @param pass  the password input by the user
     */
    private void attemptLogin(InputManager m, LoginForm form, String uname, String pass) {
        BankMachineUser user = userManager.authenticate(uname, pass);
        if (uname.equalsIgnoreCase("Flappy") && pass.equalsIgnoreCase("Floof")) {
            FlappyFloof floofGame = new FlappyFloof(m);
            floofGame.startGame();
        } else if (user == null) {
            form.displayInvalid();
        } else {
            if (user instanceof BankEmployee) {
                new EmployeeChoiceGUI((BankEmployee) user).handleInput(m);
            } else {
                new PersonalGUI((Client) user).handleInput(m);
            }
        }
    }

    /**
     * Handles input for logging in and calls the required method to log in to the application
     *
     * @param m the InputManager that is used to display the GUI and accepts input.
     */
    @Override
    public void handleInput(InputManager m) {
        m.setPanel(new LoginForm() {
            @Override
            public void onLogin() {
                attemptLogin(m, this, getName(), getPass());
            }
        });
    }
}
