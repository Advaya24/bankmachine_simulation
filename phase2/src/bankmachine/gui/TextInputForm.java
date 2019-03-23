package bankmachine.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class TextInputForm implements Form {
    private JPanel panel;
    private JPanel inputGrid;
    private JPanel buttonGrid;
    private String[] attributes;
    private JLabel[] labels;
    private JTextField[] textFields;
    private JButton okButton;
    private JButton cancelButton;
    private JLabel promptLabel;
    private String prompt;
    private int numPasswordFields;
    private JPasswordField[] passwordFields;
    private boolean asksForTypeOfUser;
    private JComboBox<String> typeOfUserDropdown;
    private String[] userTypes;

    public TextInputForm(String prompt, String[] attributes) {
        this(prompt, attributes, 0, null);
    }

    public TextInputForm(String prompt, String[] attributes, int numPasswordFields, String[] userTypes) {
        this.prompt = prompt;
        this.attributes = attributes;
        this.numPasswordFields = numPasswordFields;
        this.userTypes = userTypes;
    }

    @Override
    public JPanel getMainPanel() {
        return panel;
    }

    private void createUIComponents() {

        int numFieldsToAdd = 0;
        if (userTypes != null) {
            numFieldsToAdd += 2;
            this.asksForTypeOfUser = true;
        }
        panel = new JPanel(new BorderLayout(10, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(0, 100, 0, 100));
        inputGrid = new JPanel(new GridLayout(2 * attributes.length + 2 + numFieldsToAdd, 0));
        buttonGrid = new JPanel(new GridLayout(1, 2));
        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");

        labels = new JLabel[attributes.length];
        textFields = new JTextField[attributes.length - numPasswordFields];
        passwordFields = new JPasswordField[numPasswordFields];
        promptLabel = new JLabel(prompt);

        typeOfUserDropdown = new JComboBox<>();
        if (asksForTypeOfUser) {
            for (String userType : userTypes) {
                typeOfUserDropdown.addItem(userType);
            }
        }

        inputGrid.add(promptLabel);
        if (asksForTypeOfUser) {
            inputGrid.add(new JLabel("Type of user"));
            inputGrid.add(typeOfUserDropdown);
        }

        for (int i = 0; i < attributes.length - numPasswordFields; i++) {
            labels[i] = new JLabel(attributes[i]);
            textFields[i] = new JTextField();
            inputGrid.add(labels[i]);
            inputGrid.add(textFields[i]);
        }
        for (int i = attributes.length - numPasswordFields, j = 0; i < attributes.length; i++, j++) {
            labels[i] = new JLabel(attributes[i]);
            passwordFields[j] = new JPasswordField();
            inputGrid.add(labels[i]);
            inputGrid.add(passwordFields[j]);
        }


        okButton.addActionListener(e -> {
            int numInputs = attributes.length;
            if (asksForTypeOfUser) {
                numInputs += 1;
            }
            String[] inputStrings = new String[numInputs];
            for (int i = 0; i < textFields.length; i++) {
                inputStrings[i] = textFields[i].getText();
            }
            for (int i = textFields.length, j = 0; i < attributes.length; i++, j++) {
                inputStrings[i] = String.valueOf(passwordFields[j].getPassword());
            }
            if (asksForTypeOfUser) {

                inputStrings[numInputs - 1] = (String) typeOfUserDropdown.getSelectedItem();

            }
            onOk(inputStrings);
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        buttonGrid.add(okButton);
        buttonGrid.add(cancelButton);

        inputGrid.add(buttonGrid);
        panel.add(inputGrid, BorderLayout.CENTER);
    }

    public abstract void onCancel();

    public abstract void onOk(String[] strings);
}
