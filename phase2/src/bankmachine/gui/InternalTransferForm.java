package bankmachine.gui;

import bankmachine.account.Account;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

@SuppressWarnings("Duplicates")
public abstract class InternalTransferForm implements Form {
    /**
     * The main panel
     */
    private JPanel panel;
    /**
     * A panel that holds all input fields
     */
    private JPanel inputGrid;
    /**
     * A panel that holds all buttons shown to the user
     */
    private JPanel buttonGrid;
    /**
     * The labels that display the transfer operations
     */
    private JLabel[] labels;
    /**
     * All accounts a user can transfer from
     */
    private JComboBox<Account> fromAccountDropDown;
    /**
     * All accounts a user can transfer to
     */
    private JComboBox<Account> toAccountDropBox;
    /**
     * A text field that the user can input the amount into
     */
    private JTextField amountTextField;
    /**
     * A label that displays required prompts
     */
    private JLabel promptLabel;
    /**
     * An ok button
     */
    private JButton okButton;
    /**
     * A cancel button
     */
    private JButton cancelButton;
    /**
     * A list of all accounts that are displayed
     */
    private List<Account> accountList;

    public InternalTransferForm(List<Account> accountList) {
        this.accountList = accountList;
    }

    /**
     * @return the main JPanel
     */
    @Override
    public JPanel getMainPanel() {
        return panel;
    }

    /**
     * Creates all the UI Components required to display the GUI.
     */
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

    /**
     * Abstract method that is used to handle internal transfers.
     *
     * @param fromAccount  the account that the transfer is made from
     * @param toAccount    the account that the transfer is made to
     * @param amountString the amount of money being transferred.
     */
    public abstract void onOk(Account fromAccount, Account toAccount, String amountString);

    /**
     * Abstract method that determines what happens when the Cancel button is used.
     */
    public abstract void onCancel();
}
