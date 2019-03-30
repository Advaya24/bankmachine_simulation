package bankmachine.gui.bankEmployeeGUIHandlers;

import bankmachine.BankMachine;
import bankmachine.gui.AlertMessageForm;
import bankmachine.gui.BankEmployeeGUI;
import bankmachine.gui.InputManager;
import bankmachine.gui.TextInputForm;

public class AddBillsGUIHandler {
    private BankEmployeeGUI gui;

    public AddBillsGUIHandler(BankEmployeeGUI gui) {
        this.gui = gui;
    }

    /**
     * Displays options for adding bills to the bankmachine
     *
     * @param m the input manager handling this
     */
    public void handleAddBills(InputManager m) {
        int[] denominations = {5, 10, 20, 50};
        String[] attributes = new String[denominations.length];
        for (int i = 0; i < denominations.length; i++) {
            attributes[i] = denominations[i] + "s";
        }
        m.setPanel(new TextInputForm("How many of each?", attributes) {
            @Override
            public void onCancel() {
                gui.handleInput(m);
            }

            @Override
            public void onOk(String[] strings) {
                int[] arrayNumBillsToAdd = new int[strings.length];
                for (int i = 0; i < strings.length; i++) {
                    try {
                        int numBills = Integer.parseInt(strings[i]);
                        if (numBills > 0) {
                            arrayNumBillsToAdd[i] = numBills;
                        } else {
                            throw new NumberFormatException();
                        }
                    } catch (NumberFormatException e) {
                        m.setPanel(new AlertMessageForm("Invalid input for at least one denomination!") {
                            @Override
                            public void onOK() {
                                handleAddBills(m);
                            }
                        });
                        return;
                    }
                }
                for (int i = 0; i < arrayNumBillsToAdd.length; i++) {
                    BankMachine.getBillManager().addBills(denominations[i], arrayNumBillsToAdd[i]);
                }
                m.setPanel(new AlertMessageForm("Success!") {
                    @Override
                    public void onOK() {
                        gui.handleInput(m);
                    }
                });
            }
        });
    }
}
