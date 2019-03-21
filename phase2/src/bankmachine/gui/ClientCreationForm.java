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

    public ClientCreationForm(String prompt, String[] attributes) {
        this.attributes = attributes;
        this.prompt = prompt;
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

        textFields = new JTextField[attributes.length];
        labels = new JLabel[attributes.length];
        promptLabel = new JLabel(prompt);

        inputGrid.add(promptLabel);

        for (int i = 0; i < attributes.length; i++) {
            labels[i] = new JLabel(attributes[i]);
            textFields[i] = new JTextField();
            inputGrid.add(labels[i]);
            inputGrid.add(textFields[i]);
        }

//        panel.add(inputGrid, BorderLayout.CENTER);
        okButton.addActionListener(e -> {
            String[] inputStrings = new String[textFields.length];
            for (int i = 0; i < textFields.length; i++) {
                inputStrings[i] = textFields[i].getText();
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
