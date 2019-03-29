package bankmachine.gui.personalGUIHandlers;

import bankmachine.BankMachine;
import bankmachine.account.Account;
import bankmachine.exception.BankMachineException;
import bankmachine.exception.NegativeQuantityException;
import bankmachine.exception.NotEnoughBillsException;
import bankmachine.exception.NotEnoughMoneyException;
import bankmachine.gui.*;
import bankmachine.transaction.TransactionType;
import bankmachine.users.Client;

import java.time.LocalDateTime;

public class WithdrawGUIHandler {
    private PersonalGUI gui;
    private Client client;

    public WithdrawGUIHandler(PersonalGUI gui, Client client) {
        this.gui = gui;
        this.client = client;
    }

    public void handleWithdraw(InputManager m) {
        Account[] accounts = new TransferGUIHandler(this.gui, this.client).getTransferAccounts(this.client);
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
                } catch (NotEnoughMoneyException | NotEnoughBillsException | NegativeQuantityException e) {
                    m.setPanel(new AlertMessageForm(e.toString()) {
                        @Override
                        public void onOK() {
                            handleWithdrawFor(account, m);
                        }
                    });
                } catch (BankMachineException e) {
                    m.setPanel(new AlertMessageForm("Failed... Something went wrong!") {
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
