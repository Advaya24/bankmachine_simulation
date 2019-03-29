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
                new WithdrawGUIHandler(this, this.client).handleWithdraw(m);
                return;
            case "Deposit":
                handleDeposit(m);
                return;
            case "Finance":
                new FinanceGUIHandler(this).handleFinance(m);
                return;
            case "Add User To Account":
                new AddUserGUIHandler(this, this.client).handleAddUser(m);
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
