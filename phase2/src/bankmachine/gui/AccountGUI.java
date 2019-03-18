package bankmachine.gui;

import bankmachine.BankMachine;
import bankmachine.users.BankManager;
import bankmachine.users.Client;
import bankmachine.transaction.TransactionType;
import bankmachine.account.Account;

import java.time.LocalDateTime;

public class AccountGUI implements Inputtable {
    private Account account;
    public AccountGUI(Account a){
        account = a;
    }

    public Account inputTransfer(InputManager m, double amount){
        String username =  m.getInput("Please input the username of the client." +
                " If you would like to transfer between your accounts, enter your own username.");
        Client client;
        if(BankMachine.USER_MANAGER.get(username) instanceof BankManager ||
                BankMachine.USER_MANAGER.get(username) == null){
            System.out.println("This is not the username of one of our clients.");
            return null;
        }
        else {
            client = (Client)BankMachine.USER_MANAGER.get(username);
        }
        Account a;
        if(client.equals(this.account.getClient())){
            a = m.selectItem(client.getClientsAccounts());
        } else {
            a = client.getPrimaryAccount();
        }
        if (a == null){
            return null;
        }
        if(this.account.transferOut(a, amount)){
            return a;
        } else {
            return null;
        }
    }

    private void handleSelection(InputManager m, String action){
        if (action.equals("Cancel")) {
            return;
        }
        TransactionType type = null;
        boolean status = false;
        if (action.equals("See Creation Date")){
            System.out.println(account.getCreationDate());
        }
        if (action.equals("Deposit")){
            status = account.deposit();
        } else {
            double amount = m.getMoney();
            Account destination = null;
            switch(action){
                case "Withdraw":
                    type = TransactionType.WITHDRAW;
                    status = account.withdraw(amount);
                    break;
                case "Pay Bill":
                    type = TransactionType.BILL;
                    status = account.payBill(amount);
                    break;
                case "Transfer":
                    type = TransactionType.TRANSFER;
                    destination = this.inputTransfer(m, amount);
                    status = destination != null;
                    break;
            }
            if (status){
                BankMachine.transFactory.newTransaction(
                        amount, account, destination, LocalDateTime.now(), type
                );
            }
        }
        if(status){
            System.out.println(action+" successful");
        } else {
            System.out.println(action+" unsuccessful");
        }
    }

    @Override
    public void handleInput(InputManager m) {
        String[] options = {
            "Transfer", "Withdraw", "Deposit", "Pay Bill", "See Creation Date", "Cancel"
        };
        m.setPanel(new OptionsForm<String>(options) {
            @Override
            public void onSelection(String s) {
                handleSelection(m, s);
            }
        });

    }
}
