package bankmachine.gui;

import bankmachine.BankMachine;
import bankmachine.BankManager;
import bankmachine.Client;
import bankmachine.TransactionType;
import bankmachine.account.Account;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    @Override
    public void handleInput(InputManager m) {
        List<String> options = new ArrayList<>(Arrays.asList(
                "Transfer", "Withdraw", "Deposit", "Pay Bill", "See Creation Date", "Cancel"
        ));
        System.out.println("Select an option");
        String action = m.selectItem(options);
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
}
