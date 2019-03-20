package bankmachine.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class AlertMessageForm implements Form {
    private JPanel panel;
    private JButton okButton;
    private JLabel alertLabel;
    private String alertMessage;

    public AlertMessageForm(String alertMessage) {
        this.alertMessage = alertMessage;
    }

    private void createUIComponents() {
        panel = new JPanel(new BorderLayout());
        alertLabel = new JLabel(alertMessage);
        alertLabel.setHorizontalAlignment(SwingConstants.CENTER);
        okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
        okButton.setContentAreaFilled(false);
        panel.add(alertLabel, BorderLayout.CENTER);
        panel.add(okButton, BorderLayout.SOUTH);
    }


    @Override
    public JPanel getMainPanel() {
        return panel;
    }

    public abstract void onOK();
}
