package bankmachine.gui.personalGUIHandlers;

import bankmachine.BankMachine;
import bankmachine.account.Account;
import bankmachine.exception.TransferException;
import bankmachine.gui.*;
import bankmachine.transaction.TransactionType;
import bankmachine.users.Client;

import java.time.LocalDateTime;

public class PayBillGUIHandler {
    private PersonalGUI gui;
    private Client client;

    public PayBillGUIHandler(PersonalGUI gui, Client client) {
        this.gui = gui;
        this.client = client;
    }

    public void handlePayBill(InputManager m) {
        Account[] accounts = new TransferGUIHandler(this.gui, this.client).getTransferAccounts(client);
        if (accounts.length == 0) {
            m.setPanel(new AlertMessageForm("No accounts to pay bills from!") {
                @Override
                public void onOK() {
                    gui.handleInput(m);
                }
            });
        } else {
            m.setPanel(new SearchForm("Select account to pay bill from", new OptionsForm<Account>(accounts, "") {
                @Override
                public void onSelection(Account account) {
                    payBillUsing(account, m);
                }
            }.getMainPanel()) {
                @Override
                public void onCancel() {
                    gui.handleInput(m);
                }
            });
        }
    }

    private void payBillUsing(Account account, InputManager m) {
        String[] attributes = {"Enter amount"};
        m.setPanel(new TextInputForm("Pay bill", attributes) {
            @Override
            public void onCancel() {
                handlePayBill(m);
            }

            @Override
            public void onOk(String[] strings) {
                double amount;
                try {
                    amount = Double.parseDouble(strings[0]);
                    account.payBill(amount);
                    BankMachine.transFactory.newTransaction(amount, account, null, LocalDateTime.now(), TransactionType.BILL);
                    m.setPanel(new AlertMessageForm("Success!") {
                        @Override
                        public void onOK() {
                            gui.handleInput(m);
                        }
                    });
                } catch (NumberFormatException e) {
                    m.setPanel(new AlertMessageForm("Invalid amount!") {
                        @Override
                        public void onOK() {
                            payBillUsing(account, m);
                        }
                    });
                } catch (TransferException e) {
                    m.setPanel(new AlertMessageForm(e.toString()) {
                        @Override
                        public void onOK() {
                            payBillUsing(account, m);
                        }
                    });
                }
            }
        });
    }
}
