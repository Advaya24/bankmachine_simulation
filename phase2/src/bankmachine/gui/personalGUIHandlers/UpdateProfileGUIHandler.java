package bankmachine.gui.personalGUIHandlers;

import bankmachine.exception.InvalidEmailException;
import bankmachine.exception.InvalidPhoneNumberException;
import bankmachine.gui.*;
import bankmachine.users.BankMachineUser;

public class UpdateProfileGUIHandler implements Inputtable {
    /**
     * The user using this system
     */
    private BankMachineUser user;
    /**
     * The PersonalGUi of the user
     */
    private PersonalGUI gui;

    public UpdateProfileGUIHandler(BankMachineUser user, PersonalGUI gui) {
        this.user = user;
        this.gui = gui;
    }

    /**
     * Presents setting options for the user
     *
     * @param m the input manager that handles this
     */
    @Override
    public void handleInput(InputManager m) {
        String[] options = {"Phone Number", "Email", "Password"};
        m.setPanel(new SearchForm("What would you like to update?", new OptionsForm<String>(options, "") {
            @Override
            public void onSelection(String s) {
                handleSelection(m, s);
            }
        }.getMainPanel()) {
            @Override
            public void onCancel() {
                gui.handleInput(m);
            }
        });
    }

    /**
     * Takes input for new phone number and updates it
     *
     * @param m the input manager that handles this
     */
    private void updatePhoneNumber(InputManager m) {
        String[] attributes = {"New Phone Number"};
        m.setPanel(new TextInputForm("Update Phone Number", attributes) {
            @Override
            public void onCancel() {
                handleInput(m);
            }

            @Override
            public void onOk(String[] strings) {
                try {
                    if (strings[0] == null || strings[0].equals("")) {
                        m.setPanel(new AlertMessageForm("Phone Number cannot be empty!") {
                            @Override
                            public void onOK() {
                                updatePhoneNumber(m);
                            }
                        });
                    } else {
                        m.checkPhone(strings[0]);
                        user.setPhoneNumber(strings[0]);
                        m.setPanel(new AlertMessageForm("Updated Phone Number Successfully") {
                            @Override
                            public void onOK() {
                                gui.handleInput(m);
                            }
                        });
                    }
                } catch (InvalidPhoneNumberException e) {
                    m.setPanel(new AlertMessageForm(e.toString()) {
                        @Override
                        public void onOK() {
                            updatePhoneNumber(m);
                        }
                    });
                }
            }
        });
    }

    /**
     * Takes input for new email and updates it
     *
     * @param m
     */
    private void updateEmail(InputManager m) {
        String[] attributes = {"New Email"};
        m.setPanel(new TextInputForm("Update Email", attributes) {
            @Override
            public void onCancel() {
                handleInput(m);
            }

            @Override
            public void onOk(String[] strings) {
                try {
                    if (strings[0] == null || strings[0].equals("")) {
                        m.setPanel(new AlertMessageForm("Email cannot be empty!") {
                            @Override
                            public void onOK() {
                                updateEmail(m);
                            }
                        });
                    } else {
                        m.checkEmail(strings[0]);
                        user.setEmail(strings[0]);
                        m.setPanel(new AlertMessageForm("Updated Email Successfully") {
                            @Override
                            public void onOK() {
                                gui.handleInput(m);
                            }
                        });
                    }
                } catch (InvalidEmailException e) {
                    m.setPanel(new AlertMessageForm(e.toString()) {
                        @Override
                        public void onOK() {
                            updateEmail(m);
                        }
                    });
                }
            }
        });
    }

    /**
     * Handles updating a user's password
     *
     * @param m the InputManager responsible for displaying the GUI and accepting input from the user
     */
    private void updatePassword(InputManager m) {
        String[] attributes = {"New Password", "Confirm New Password"};
        m.setPanel(new TextInputForm("Update password", attributes, 2) {
            @Override
            public void onCancel() {
                handleInput(m);
            }

            @Override
            public void onOk(String[] strings) {
                if (strings[0] == null || strings[0].equals("")) {
                    m.setPanel(new AlertMessageForm("Password cannot be empty") {
                        @Override
                        public void onOK() {
                            updatePassword(m);
                        }
                    });
                } else if (strings[1] == null || !strings[0].equals(strings[1])) {
                    m.setPanel(new AlertMessageForm("Passwords didn't match") {
                        @Override
                        public void onOK() {
                            updatePassword(m);
                        }
                    });
                } else {
                    user.setPassword(strings[0]);
                    m.setPanel(new AlertMessageForm("Updated Password Successfully") {
                        @Override
                        public void onOK() {
                            gui.handleInput(m);
                        }
                    });
                }
            }
        });
    }

    /**
     * Determines what behaviour needs to be executed based on the user input.
     *
     * @param m InputManager object that is used to accept input
     * @param s represents the User Input
     */
    private void handleSelection(InputManager m, String s) {
        switch (s) {
            case "Phone Number":
                updatePhoneNumber(m);
                return;
            case "Email":
                updateEmail(m);
                return;
            case "Password":
                updatePassword(m);
        }
    }
}
