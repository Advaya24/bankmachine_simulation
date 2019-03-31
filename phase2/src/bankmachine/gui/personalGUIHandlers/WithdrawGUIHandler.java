package bankmachine.gui.personalGUIHandlers;

import bankmachine.BankMachine;
import bankmachine.account.Account;
import bankmachine.exception.BankMachineException;
import bankmachine.gui.*;
import bankmachine.transaction.TransactionType;
import bankmachine.users.Client;

import java.time.LocalDateTime;

public class WithdrawGUIHandler {
    /**
     * The GUI of the client using the system
     */
    private PersonalGUI gui;
    /**
     * The client using the system
     */
    private Client client;

    public WithdrawGUIHandler(PersonalGUI gui, Client client) {
        this.gui = gui;
        this.client = client;
    }

    /**
     * Withdraws money from an account
     *
     * @param m the InputManager that displays the GUI and accepts input
     */
    public void handleWithdraw(InputManager m) {
        Account[] accounts = new Account[this.client.getClientsAccounts().size()];
        this.client.getClientsAccounts().toArray(accounts);
        m.setPanel(new SearchForm("Select account to withdraw from", new OptionsForm<Account>(accounts, "") {
            @Override
            public void onSelection(Account account) {
                handleWithdrawFor(account, m);
            }
        }.getMainPanel()) {
            @Override
            public void onCancel() {
                gui.handleInput(m);
            }
        });
    }

    /**
     * Withdraws money from an account
     *
     * @param account the account to be withdrawn from
     * @param m       the InputManager that displays the GUI and accepts input
     */
    private void handleWithdrawFor(Account account, InputManager m) {
        String[] attributes = {"Enter withdrawal amount"};
        m.setPanel(new TextInputForm("Withdraw Money", attributes) {
            @Override
            public void onCancel() {
                handleWithdraw(m);
            }

            @Override
            public void onOk(String[] strings) {
                double amount;
                try {
                    amount = Double.parseDouble(strings[0]);
                    account.withdraw(amount);
                    BankMachine.transFactory.newTransaction(amount, account, null, LocalDateTime.now(), TransactionType.WITHDRAW);
                    m.setPanel(new AlertMessageForm("Success! Please remember to collect your money") {
                        @Override
                        public void onOK() {
                            gui.handleInput(m);
                        }
                    });
                } catch (NumberFormatException e) {
                    m.setPanel(new AlertMessageForm("Invalid amount") {
                        @Override
                        public void onOK() {
                            handleWithdrawFor(account, m);
                        }
                    });
                } catch (BankMachineException e) {
                    m.setPanel(new AlertMessageForm(e.toString()) {
                        @Override
                        public void onOK() {
                            handleWithdrawFor(account, m);
                        }
                    });
                }
            }
        });
    }
}
