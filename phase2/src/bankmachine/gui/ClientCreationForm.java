package bankmachine.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class ClientCreationForm implements Form {
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

    public ClientCreationForm(String prompt, String[] attributes) {
        this(prompt, attributes, 0);
    }
    public ClientCreationForm(String prompt, String[] attributes, int numPasswordFields) {
        this.prompt = prompt;
        this.attributes = attributes;
        this.numPasswordFields = numPasswordFields;
    }

    @Override
    public JPanel getMainPanel() {
        return panel;
    }

    private void createUIComponents() {
        panel = new JPanel();
        inputGrid = new JPanel(new GridLayout(2*attributes.length+2, 0));
        buttonGrid = new JPanel(new GridLayout(1,2));
        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");

        labels = new JLabel[attributes.length];
        textFields = new JTextField[attributes.length - numPasswordFields];
        passwordFields = new JPasswordField[numPasswordFields];
        promptLabel = new JLabel(prompt);

        inputGrid.add(promptLabel);

        for (int i = 0; i < attributes.length-numPasswordFields; i++) {
            labels[i] = new JLabel(attributes[i]);
            textFields[i] = new JTextField();
            inputGrid.add(labels[i]);
            inputGrid.add(textFields[i]);
        }
        for (int i = attributes.length-numPasswordFields, j = 0; i < attributes.length; i++, j++) {
            labels[i] = new JLabel(attributes[i]);
            passwordFields[j] = new JPasswordField();
            inputGrid.add(labels[i]);
            inputGrid.add(passwordFields[j]);
        }

//        panel.add(inputGrid, BorderLayout.CENTER);
        okButton.addActionListener(e -> {
            String[] inputStrings = new String[attributes.length];
            for (int i = 0; i < textFields.length; i++) {
                inputStrings[i] = textFields[i].getText();
            }
            for (int i = textFields.length, j = 0; i < attributes.length; i++, j++) {
                inputStrings[i] = String.valueOf(passwordFields[j].getPassword());
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
//        panel.add(buttonGrid, BorderLayout.SOUTH);
        inputGrid.add(buttonGrid);
        panel.add(inputGrid);
    }

    public abstract void onCancel();
    public abstract void onOk(String[] strings);
}
