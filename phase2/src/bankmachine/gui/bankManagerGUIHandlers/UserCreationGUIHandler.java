package bankmachine.gui.bankManagerGUIHandlers;

import bankmachine.BankMachine;
import bankmachine.exception.BankMachineException;
import bankmachine.gui.AlertMessageForm;
import bankmachine.gui.BankManagerGUI;
import bankmachine.gui.InputManager;
import bankmachine.gui.TextInputForm;
import bankmachine.users.BankMachineUser;

public class UserCreationGUIHandler {
    private BankManagerGUI gui;

    public UserCreationGUIHandler(BankManagerGUI gui) {
        this.gui = gui;
    }

    /**
     * Displays options for creating a new client
     *
     * @param m the input manager handling this
     */
    public void handleUserCreation(InputManager m) {
        String[] attributes = {"Name", "Email", "Phone (xxx-xxx-xxxx)", "Username", "Password", "Confirm Password"};
        String[] userTypes = {"Client", "Employee"};
        m.setPanel(new TextInputForm("Create new user", attributes, 2, userTypes) {
            @Override
            public void onCancel() {
                gui.handleInput(m);
            }

            @Override
            public void onOk(String[] strings) {
                try {
                    m.checkEmail(strings[1]);
                    m.checkPhone(strings[2]);
                    if (!strings[4].equals(strings[5])) {
                        m.setPanel(new AlertMessageForm("Passwords didn't match!") {
                            @Override
                            public void onOK() {
                                handleUserCreation(m);
                            }
                        });
                    } else if (strings[0].equals("") || strings[1].equals("") || strings[2].equals("") || strings[3].equals("") || strings[4].equals("")) {
                        m.setPanel(new AlertMessageForm("Can't leave any field empty!") {
                            @Override
                            public void onOK() {
                                handleUserCreation(m);
                            }
                        });
                    } else if (strings[6] == null) {
                        m.setPanel(new AlertMessageForm("Must select type of User") {
                            @Override
                            public void onOK() {
                                handleUserCreation(m);
                            }
                        });
                    } else {
                        BankMachineUser user;
                        if (strings[6].equals("Client")) {
                            user = BankMachine.USER_MANAGER.newClient(strings[0], strings[1], strings[2], strings[3], strings[4]);
                        } else {
                            user = BankMachine.USER_MANAGER.newIntern(strings[0], strings[1], strings[2], strings[3], strings[4]);
                        }

                        String alertMessage;
                        if (user == null) {
                            System.out.println("A user with that username exists!");
                            alertMessage = "A user with that username exists!";

                        } else {
                            System.out.println("User created");
                            alertMessage = "User created";
                        }
                        m.setPanel(new AlertMessageForm(alertMessage) {
                            @Override
                            public void onOK() {
                                gui.handleInput(m);
                            }
                        });
                    }
                } catch (BankMachineException e) {
                    m.setPanel(new AlertMessageForm(e.toString()) {
                        @Override
                        public void onOK() {
                            handleUserCreation(m);
                        }
                    });
                }
            }
        });
    }

}
