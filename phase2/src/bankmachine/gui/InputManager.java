package bankmachine.gui;


import bankmachine.BankMachine;
import bankmachine.exception.InvalidEmailException;
import bankmachine.exception.InvalidPhoneNumberException;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Pattern;

public class InputManager extends JFrame {

    /**
     * Pattern for emails
     */
    private Pattern emailRe = Pattern.compile(
            "^[a-zA-Z0-9.\\-_]+@[a-zA-Z0-9.\\-_]+\\.[a-zA-Z]{2,3}$"
    );
    /**
     * Pattern for phone numbers
     */
    private Pattern phoneRe = Pattern.compile(
            "^\\(?\\d{3}\\)?-\\d{3}-\\d{4}$"
    );
    /**
     * Pattern for double
     */
    private Pattern doubleRe = Pattern.compile(
            "^\\d+(.\\d\\d?)?$"
    );

    /**
     * Matches email to regex and throws exception if it doesn't match
     *
     * @param email the string to match
     * @throws InvalidEmailException the email was in invalid format
     */
    public void checkEmail(String email) throws InvalidEmailException {
        if (!emailRe.matcher(email).matches()) {
            throw new InvalidEmailException();
        }
    }

    /**
     * Matches phone to regex and throws exception if it doesn't match
     *
     * @param phone the string to match
     * @throws InvalidPhoneNumberException the phone was in invalid format
     */
    public void checkPhone(String phone) throws InvalidPhoneNumberException {
        if (!phoneRe.matcher(phone).matches()) {
            throw new InvalidPhoneNumberException();
        }
    }


    public static void main(String[] args) {
        new InputManager().mainLoop();
    }

    public InputManager() {
        this.setSize(new Dimension(800, 800));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    /**
     * Sets panel for JFrame
     *
     * @param form the form whose main panel is to be the current panel
     */
    public void setPanel(Form form) {
        this.getContentPane().removeAll();
        this.repaint();
        this.getContentPane().add(form.getMainPanel(), BorderLayout.CENTER);
        this.setVisible(true);
    }

    /**
     * Entry point for log in and all other actions
     */
    public void mainLoop() {
        new LoginGUI().handleInput(this);
    }

    public void exit() {
        BankMachine.USER_MANAGER.saveData();
        this.dispose();
        System.exit(0);
    }
}
