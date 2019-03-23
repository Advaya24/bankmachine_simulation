package bankmachine.gui;

import bankmachine.*;
import bankmachine.users.*;

import java.util.ArrayList;
import java.util.List;

public class LoginGUI implements Inputtable {
    private UserManager userManager = BankMachine.USER_MANAGER;
    private void attemptLogin(InputManager m, LoginForm form, String uname, String pass){
        BankMachineUser user = userManager.authenticate(uname, pass);
        if (user == null) {
            form.displayInvalid();
        } else {
            if(user instanceof BankEmployee) {
                new EmployeeChoiceGUI((BankEmployee)user).handleInput(m);
            } else {
                new ClientGUI((Client) user).handleInput(m);
            }
        }
    }
    @Override
    public void handleInput(InputManager m){
        m.setPanel(new LoginForm(){
            @Override
            public void onLogin() {
                attemptLogin(m, this, getName(), getPass());
            }
        });
    }
}
