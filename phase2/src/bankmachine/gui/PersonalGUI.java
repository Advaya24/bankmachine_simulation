package bankmachine.gui;

import bankmachine.BankMachine;
import bankmachine.account.Account;
import bankmachine.exception.*;
import bankmachine.finance.*;
import bankmachine.transaction.TransactionType;
import bankmachine.users.BankMachineUser;
import bankmachine.users.BankManager;
import bankmachine.users.Client;

import javax.swing.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PersonalGUI implements Inputtable {
    private Client client;
    public PersonalGUI(Client c){
        this.client = c;
    }

    private void newAccountCreationInput(InputManager m){
        String[] accountTypes ={"Chequing account", "Credit card account",
                "Line of credit account", "Savings account","Retirement account"};
        m.setPanel(new SearchForm("Select type of account", new OptionsForm<String>(accountTypes, ""){
            @Override
            public void onSelection(String s) {
                addCreationRequest(s, m);
            }
        }.getMainPanel()) {
            @Override
            public void onCancel() {
                handleInput(m);
            }
        });
    }
    private void addCreationRequest(String request, InputManager m) {
        BankManager manager = BankMachine.USER_MANAGER.getBankManagers().get(0);
        manager.addCreationRequest(client.getUsername() + " requests a " + request);
        m.setPanel(new AlertMessageForm("Account Creation Request Sent") {
            @Override
            public void onOK() {
                handleInput(m);
            }
        });
    }



    private void handleWithdraw(InputManager m) {
        Account[] accounts = new TransferGUIHandler(this, this.client).getTransferAccounts(this.client);
        m.setPanel(new SearchForm("Select account to withdraw from", new OptionsForm<Account>(accounts, "") {
            @Override
            public void onSelection(Account account) {
                handleWithdrawFor(account, m);
            }
        }.getMainPanel()) {
            @Override
            public void onCancel() {
                handleInput(m);
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
                            handleInput(m);
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

    private void handleDeposit(InputManager m) {
        Account[] accounts = new Account[this.client.getClientsAccounts().size()];
        this.client.getClientsAccounts().toArray(accounts);

        m.setPanel(new SearchForm("Select account to deposit money to", new OptionsForm<Account>(accounts, ""){
            @Override
            public void onSelection(Account account) {
                try {
                    account.deposit();
                    m.setPanel(new AlertMessageForm("Success!") {
                        @Override
                        public void onOK() {
                            handleInput(m);
                        }
                    });
                } catch (NegativeQuantityException | NoDepositException e) {
                    m.setPanel(new AlertMessageForm(e.toString()) {
                        @Override
                        public void onOK() {
                            handleInput(m);
                        }
                    });
                }
            }
        }.getMainPanel()) {
            @Override
            public void onCancel() {
                handleInput(m);
            }
        });
    }

    private void handleAddUser(InputManager m) {
        Account[] accounts = new Account[this.client.getClientsAccounts().size()];
        this.client.getClientsAccounts().toArray(accounts);

        m.setPanel(new SearchForm("Select account to add user to", new OptionsForm<Account>(accounts, ""){
            @Override
            public void onSelection(Account account) {
                addUserTo(account, m);
            }
        }.getMainPanel()) {
            @Override
            public void onCancel() {
                handleInput(m);
            }
        });
    }
    private void addUserTo(Account account, InputManager m) {
        String[] attributes = {"Username of user to add"};
        m.setPanel(new TextInputForm("Add user to " + account.toString(), attributes) {
            @Override
            public void onCancel() {
                handleAddUser(m);
            }

            @Override
            public void onOk(String[] strings) {
                try {
                    client = (Client) BankMachine.USER_MANAGER.get(strings[0]);
                } catch (NullPointerException e) {
                    m.setPanel(new AlertMessageForm("Invalid input for username!") {
                        @Override
                        public void onOK() {
                            addUserTo(account, m);
                        }
                    });
                }

                if (client == null) {
                    m.setPanel(new AlertMessageForm("User not found") {
                        @Override
                        public void onOK() {
                            addUserTo(account, m);
                        }
                    });
                } else {
                    account.addSecondaryClient(client);
                    m.setPanel(new AlertMessageForm("Successfully added " + client.toString()) {
                        @Override
                        public void onOK() {
                            handleInput(m);
                        }
                    });
                }
            }
        });
    }

    private void handleSelection(InputManager m, String s){
        switch (s){
            case "Logout": m.mainLoop(); return;
            case "Request Creation Of A New Account":
                newAccountCreationInput(m);
                return;
            case "Transfer":
                new TransferGUIHandler(this, this.client).transferMenu(m);
                return;
            case "Withdraw":
                handleWithdraw(m);
                return;
            case "Deposit":
                handleDeposit(m);
                return;
            case "Finance":
                new FinanceGUIHandler(this).handleFinance(m);
                return;
            case "Add User To Account":
                handleAddUser(m);
                return;
            case "Update Profile":
                new UpdateProfileGUI(client, this).handleInput(m);
                return;
            default:
                break;
        }
        handleInput(m);
    }

    @Override
    public void handleInput(InputManager m){
        System.out.println("Welcome, "+client.getName()+"!");
            System.out.println("Select an action");
            String[] options = {
                "Accounts", "Request Creation Of A New Account", "Transfer", "Withdraw", "Deposit", "Finance", "Add User To Account", "Update Profile", "Logout"
            };
            m.setPanel(new OptionsForm<String>(options, "What would you like to do?") {
                @Override
                public void onSelection(String s) {
                    handleSelection(m, s);
                }
            });

    }
}
