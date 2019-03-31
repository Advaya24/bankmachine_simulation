package bankmachine.gui;

import bankmachine.BankMachine;
import bankmachine.account.Account;
import bankmachine.exception.BankMachineException;
import bankmachine.users.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TransferForm implements Form {
    private JPanel panel1;
    private JComboBox<Client> userSelect;
    private JComboBox<Account> accountsSelect;
    private JTextField amountInput;
    private JPanel accountPanel;
    private JButton transferButton;
    private JLabel errors;
    private Account account;

    public TransferForm(Account account) {
        this.account = account;
        userSelect.addActionListener(actionEvent -> checkHideAccountSelect());
        transferButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                tryTransfer();
            }
        });
    }

    /**
     * Tries to transfer between clients/accounts, and displays the appropriate message if the operation
     * cannot be performed
     */
    private void tryTransfer() {
        Client client = (Client) userSelect.getSelectedItem();
        if (client == null) {
            errors.setText("No client selected");
            return;
        }
        Account a;
        if (client.getID() == account.getClient().getID()) {
            a = (Account) accountsSelect.getSelectedItem();
        } else {
            a = client.getPrimaryAccount();
        }
        if (a == null) {
            errors.setText("No account selected");
            return;
        }
        double amount;
        try {
            amount = Double.parseDouble(amountInput.getText());
        } catch (NumberFormatException e) {
            errors.setText("Not a valid number");
            return;
        }
        amount = ((double) Math.round(amount * 100)) / 100.0;
        try {
            this.account.transferOut(a, amount);
        } catch (BankMachineException e) {
            errors.setText(e.getMessage());
        }
    }

    /**
     * //TODO: DOCUMENT
     */
    private void checkHideAccountSelect() {
        Client selected = (Client) userSelect.getSelectedItem();
        if (selected == null) {
            return;
        }
        if (selected.getID() == account.getClient().getID()) {
            accountPanel.setVisible(true);
        } else {
            accountPanel.setVisible(false);
        }
    }

    /**
     * Creates all the UI Components required to display the GUI.
     */
    private void createUIComponents() {
        List<Client> clients = BankMachine.USER_MANAGER.getClients();
        Client[] clientArr = new Client[clients.size()];
        clients.toArray(clientArr);
        userSelect = new JComboBox<>(clientArr);

        List<Account> accounts = account.getClient().getClientsAccounts();
        Account[] accountArr = new Account[accounts.size()];
        accounts.toArray(accountArr);
        accountsSelect = new JComboBox<>(accountArr);
    }

    /**
     * @return returns the main JPanel
     */
    @Override
    public JPanel getMainPanel() {
        return panel1;
    }
}
