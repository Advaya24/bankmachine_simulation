package bankmachine.gui.bankManagerGUIHandlers;

import bankmachine.BankMachine;
import bankmachine.gui.AlertMessageForm;
import bankmachine.gui.BankManagerGUI;
import bankmachine.gui.InputManager;
import bankmachine.gui.TextInputForm;

import java.time.DateTimeException;
import java.time.LocalDateTime;

public class DateTimeGUIHandler {
    private BankManagerGUI gui;

    public DateTimeGUIHandler(BankManagerGUI gui) {
        this.gui = gui;
    }

    public void handleGetDateTime(InputManager m) {
        String[] attributes = {"Year (YYYY):", "Month (1-12)", "Day (1-31)"};
        m.setPanel(new TextInputForm("Date-Time settings", attributes) {
            @Override
            public void onCancel() {
                gui.handleInput(m);
            }

            @Override
            public void onOk(String[] strings) {
                try {
                    setTime(LocalDateTime.of(Integer.parseInt(strings[0]), Integer.parseInt(strings[1]), Integer.parseInt(strings[2]), 0, 0));
                    m.setPanel(new AlertMessageForm("Success!") {
                        @Override
                        public void onOK() {
                            gui.handleInput(m);
                        }
                    });
                } catch (DateTimeException | NumberFormatException e) {
                    m.setPanel(new AlertMessageForm("Invalid Date") {
                        @Override
                        public void onOK() {
                            handleGetDateTime(m);
                        }
                    });
                }
            }
        });
    }

    private void setTime(LocalDateTime localDateTime) {
        BankMachine.getTimeInfo().setTime(localDateTime);
    }
}
