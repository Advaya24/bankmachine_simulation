package bankmachine.gui;

import bankmachine.BankMachine;
import bankmachine.InputManager;
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
                    destination = account.inputTransfer(m, amount);
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
