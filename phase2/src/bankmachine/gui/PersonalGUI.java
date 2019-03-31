package bankmachine.gui;

import bankmachine.gui.personalGUIHandlers.*;
import bankmachine.users.Client;

public class PersonalGUI implements Inputtable {
    /**
     * The current client using the system
     */
    private Client client;

    public PersonalGUI(Client c) {
        this.client = c;
    }

    /**
     * Determines what behaviour needs to be executed based on the user input.
     *
     * @param m InputManager object that is used to accept input
     * @param s represents the User Input
     */
    private void handleSelection(InputManager m, String s) {
        switch (s) {
            case "Logout":
                m.mainLoop();
                return;
            case "Account Summary":
                new AccountSummaryGUIHandler(this, this.client).handleAccountSummary(m);
                return;
            case "Request Creation Of A New Account":
                new NewCreationRequestGUIHandler(this, this.client).handleNewAccountCreationInput(m);
                return;
            case "Transfer":
                new TransferGUIHandler(this, this.client).transferMenu(m);
                return;
            case "Withdraw":
                new WithdrawGUIHandler(this, this.client).handleWithdraw(m);
                return;
            case "Deposit":
                new DepositGUIHandler(this, this.client).handleDeposit(m);
                return;
            case "Pay Bill":
                new PayBillGUIHandler(this, this.client).handlePayBill(m);
                return;
            case "Finance":
                new FinanceGUIHandler(this).handleFinance(m);
                return;
            case "Add User To Account":
                new AddUserGUIHandler(this, this.client).handleAddUser(m);
                return;
            case "Update Profile":
                new UpdateProfileGUIHandler(client, this).handleInput(m);
                return;
            default:
                break;
        }
        handleInput(m);
    }

    /**
     * When called, accepts an input from the user and calls the method to handle that input.
     *
     * @param m InputManager object that is used to accept input
     */
    @Override
    public void handleInput(InputManager m) {
        String[] options = {
                "Account Summary", "Request Creation Of A New Account", "Transfer", "Withdraw", "Deposit", "Pay Bill", "Finance", "Add User To Account", "Update Profile", "Logout"
        };
        m.setPanel(new OptionsForm<String>(options, "What would you like to do?") {
            @Override
            public void onSelection(String s) {
                handleSelection(m, s);
            }
        });

    }
}
