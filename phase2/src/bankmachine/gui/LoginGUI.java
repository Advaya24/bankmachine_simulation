package bankmachine.gui;

import bankmachine.*;
import bankmachine.users.BankMachineUser;
import bankmachine.users.BankManager;
import bankmachine.users.Client;
import bankmachine.users.UserManager;

public class LoginGUI implements Inputtable {
    private UserManager userManager = BankMachine.USER_MANAGER;
    private void attemptLogin(InputManager m, LoginForm form, String uname, String pass){
        BankMachineUser user = userManager.authenticate(uname, pass);
        if (user == null) {
            form.displayInvalid();
        } else {
            if (user instanceof BankManager){
                new BankManagerGUI((BankManager) user).handleInput(m);
            } else if(user instanceof Client){
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
