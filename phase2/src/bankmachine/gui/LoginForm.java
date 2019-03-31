package bankmachine.gui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class LoginForm implements Form {
    private JPanel panel1;
    private JTextField usernameTextField;
    private JPasswordField passwordPasswordField;
    private JButton login;
    private JLabel invalidLabel;

    public LoginForm() {
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                onLogin();
            }
        });
        DocumentListener clearer = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {
                removeInvalid();
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {
                removeInvalid();
            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {
                removeInvalid();
            }
        };
        usernameTextField.getDocument().addDocumentListener(clearer);
        passwordPasswordField.getDocument().addDocumentListener(clearer);
    }

    /**
     * @return returns the Username input by the user
     */
    public String getName() {
        return usernameTextField.getText();
    }

    /**
     * @return returns the Password input by the user
     */
    public String getPass() {
        return passwordPasswordField.getText();
    }

    /**
     * Displays a Label when an input is invalid
     */
    public void displayInvalid() {
        invalidLabel.setVisible(true);
        //this.getMainPanel().repaint();
    }

    /**
     * Removes the invalid Label
     */
    public void removeInvalid() {
        invalidLabel.setVisible(false);
    }

    /**
     * @return the main JPanel
     */
    public JPanel getMainPanel() {
        return panel1;
    }

    /**
     * Abstract method that handles what happens once a user has successfully logged in
     */
    public abstract void onLogin();
}