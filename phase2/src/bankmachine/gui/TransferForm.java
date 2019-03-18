package bankmachine.gui;

import bankmachine.BankMachine;
import bankmachine.users.Client;
import bankmachine.account.Account;

import javax.swing.*;

public class TransferForm {
    private JPanel panel1;
    private JComboBox<Client> userSelect;
    private JComboBox<Account> accounts;

    private void createUIComponents() {
        userSelect = new JComboBox<>((Client[]) BankMachine.USER_MANAGER.getClients().toArray());
        // TODO: place custom component creation code here
    }
}
