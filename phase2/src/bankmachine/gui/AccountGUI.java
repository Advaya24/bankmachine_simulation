package bankmachine.gui;

import bankmachine.BankMachine;
import bankmachine.account.Account;
import bankmachine.exception.BankMachineException;
import bankmachine.transaction.TransactionType;

import java.time.LocalDateTime;

// TODO: Get rid of this class
public class AccountGUI implements Inputtable {
    private Account account;

    public AccountGUI(Account a) {
        account = a;
    }

    public Account inputTransfer(InputManager m, double amount) throws BankMachineException {
        m.setPanel(new TransferForm(this.account));
        return this.account;
    }

    private void handleSelection(InputManager m, String action) throws BankMachineException {
        if (action.equals("Cancel")) {
            return;
        }
        TransactionType type = null;
        boolean status = false;
        if (action.equals("See Creation Date")) {
            System.out.println(account.getCreationDate());
        }
        if (action.equals("Deposit")) {
            account.deposit();
        } else {
            double amount = m.getMoney();
            Account destination = null;
            switch (action) {
                case "Withdraw":
                    type = TransactionType.WITHDRAW;
                    account.withdraw(amount);
                    break;
                case "Pay Bill":
                    type = TransactionType.BILL;
                    account.payBill(amount);
                    break;
                case "Transfer":
                    type = TransactionType.TRANSFER;
                    destination = this.inputTransfer(m, amount);
                    status = destination != null;
                    break;
            }
            if (status) {
                BankMachine.transFactory.newTransaction(
                        amount, account, destination, LocalDateTime.now(), type
                );
            }
        }
    }


    @Override
    public void handleInput(InputManager m) {
        String[] options = {
                "Transfer", "Withdraw", "Deposit", "Pay Bill", "See Creation Date", "Cancel"
        };
        m.setPanel(new OptionsForm<String>(options, "What would you like to do?") {
            @Override
            public void onSelection(String s) {
                try {
                    handleSelection(m, s);
                } catch (BankMachineException e) {
                    System.out.println(e.toString());
                    // TODO: exception handling
                }
            }
        });

    }
}
