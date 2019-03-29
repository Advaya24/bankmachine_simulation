package bankmachine.gui;

import bankmachine.account.Account;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

@SuppressWarnings("Duplicates")
public abstract class InternalTransferForm implements Form {
    private JPanel panel;
    private JPanel inputGrid;
    private JPanel buttonGrid;
    private JLabel[] labels;
    private JComboBox<Account> fromAccountDropDown;
    private JComboBox<Account> toAccountDropBox;
    private JTextField amountTextField;
    private JLabel promptLabel;
    private JButton okButton;
    private JButton cancelButton;
    private List<Account> accountList;

    public InternalTransferForm(List<Account> accountList) {
        this.accountList = accountList;
    }

    @Override
    public JPanel getMainPanel() {
        return panel;
    }

    private void createUIComponents() {
        panel = new JPanel(new BorderLayout());
        inputGrid = new JPanel(new GridLayout(6, 1));
        buttonGrid = new JPanel(new GridLayout(1, 2));
        promptLabel = new JLabel("Transfer between accounts");
        panel.add(promptLabel, BorderLayout.NORTH);

        fromAccountDropDown = new JComboBox<>();
        toAccountDropBox = new JComboBox<>();
        for (Account account : this.accountList) {
            fromAccountDropDown.addItem(account);
            toAccountDropBox.addItem(account);
        }
        amountTextField = new JTextField();
        labels = new JLabel[3];
        labels[0] = new JLabel("From");
        inputGrid.add(labels[0]);
        inputGrid.add(fromAccountDropDown);
        labels[1] = new JLabel("To");
        inputGrid.add(labels[1]);
        inputGrid.add(toAccountDropBox);
        labels[2] = new JLabel("Amount");
        inputGrid.add(labels[2]);
        inputGrid.add(amountTextField);
        panel.add(inputGrid, BorderLayout.CENTER);

        okButton = new JButton("OK");
        cancelButton = new JButton("Cancel");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    onOk((Account) fromAccountDropDown.getSelectedItem(), (Account) toAccountDropBox.getSelectedItem(), amountTextField.getText());
                } catch (ClassCastException | NullPointerException ex) {
                    onOk(null, null, null);
                }
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });
        buttonGrid.add(okButton);
        buttonGrid.add(cancelButton);
        panel.add(buttonGrid, BorderLayout.SOUTH);
    }

    public abstract void onOk(Account fromAccount, Account toAccount, String amountString);

    public abstract void onCancel();
}
