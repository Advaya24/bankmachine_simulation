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

    public String getName() {
        return usernameTextField.getText();
    }

    public String getPass() {
        return passwordPasswordField.getText();
    }

    public void displayInvalid() {
        invalidLabel.setVisible(true);
        //this.getMainPanel().repaint();
    }

    public void removeInvalid() {
        invalidLabel.setVisible(false);
    }

    public JPanel getMainPanel() {
        return panel1;
    }

    public abstract void onLogin();
}