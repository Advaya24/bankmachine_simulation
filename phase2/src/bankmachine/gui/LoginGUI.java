package bankmachine.gui;

import bankmachine.BankMachine;
import bankmachine.flappyFloof.FlappyFloof;
import bankmachine.users.BankEmployee;
import bankmachine.users.BankMachineUser;
import bankmachine.users.Client;
import bankmachine.users.UserManager;

public class LoginGUI implements Inputtable {
    private UserManager userManager = BankMachine.USER_MANAGER;

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
