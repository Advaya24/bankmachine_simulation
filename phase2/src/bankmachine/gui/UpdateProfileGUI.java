package bankmachine.gui;

import bankmachine.users.BankMachineUser;

public class UpdateProfileGUI implements Inputtable {
    /**
     * The user using this system
     */
    private BankMachineUser user;
    /**
     * The PersonalGUi of the user
     */
    private PersonalGUI gui;

    public UpdateProfileGUI(BankMachineUser user, PersonalGUI gui) {
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
//        System.out.println("Select an option");
//        String action = m.selectItem(options);
//        if (action.equals("Cancel")) {
//            return;
//        }
//        String value;
//        switch (action) {
//            case "Phone Number":
//                value = m.getPhone();
//                user.setPhoneNumber(value);
//                break;
//            case "Email":
//                value = m.getEmail();
//                user.setEmail(value);
//                break;
//            case "Password":
//                value = m.getInput("Enter a new password");
//                user.setPassword(value);
//                break;
//            default:
//                return;
//        }
//        System.out.println("Set new " + action + " to " + value);
    }

    private void updatePhoneNumber(InputManager m) {
        String[] attributes = {"New Phone Number"};
        m.setPanel(new TextInputForm("Update Phone Number", attributes) {
            @Override
            public void onCancel() {
                handleInput(m);
            }

            @Override
            public void onOk(String[] strings) {
                if (strings[0] == null || strings[0].equals("")) {
                    m.setPanel(new AlertMessageForm("Phone Number cannot be empty!") {
                        @Override
                        public void onOK() {
                            updatePhoneNumber(m);
                        }
                    });
                } else {
                    user.setPhoneNumber(strings[0]);
                    m.setPanel(new AlertMessageForm("Updated Phone Number Successfully") {
                        @Override
                        public void onOK() {
                            gui.handleInput(m);
                        }
                    });
                }
            }
        });
    }

    private void updateEmail(InputManager m) {
        String[] attributes = {"New Email"};
        m.setPanel(new TextInputForm("Update Email", attributes) {
            @Override
            public void onCancel() {
                handleInput(m);
            }

            @Override
            public void onOk(String[] strings) {
                if (strings[0] == null || strings[0].equals("")) {
                    m.setPanel(new AlertMessageForm("Email cannot be empty!") {
                        @Override
                        public void onOK() {
                            updateEmail(m);
                        }
                    });
                } else {
                    user.setEmail(strings[0]);
                    m.setPanel(new AlertMessageForm("Updated Email Successfully") {
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
     * Handles updating a user's password
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
