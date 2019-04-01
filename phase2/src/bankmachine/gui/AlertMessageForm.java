package bankmachine.gui;

import javax.swing.*;
import java.awt.*;

public abstract class AlertMessageForm implements Form {
    /**
     * The main panel
     */
    private JPanel panel;
    /**
     * An ok button
     */
    private JButton okButton;
    /**
     * The label that displays alerts
     */
    private JLabel alertLabel;
    /**
     * An alert message that is displayed
     */
    private String alertMessage;

    public AlertMessageForm(String alertMessage) {
        this.alertMessage = alertMessage;
    }

    /**
     * Creates all the UI Components required to display the GUI.
     */
    private void createUIComponents() {
        panel = new JPanel(new BorderLayout());
        alertLabel = new JLabel(alertMessage);
        alertLabel.setHorizontalAlignment(SwingConstants.CENTER);
        okButton = new JButton("OK");
        okButton.setContentAreaFilled(false);
        okButton.addActionListener(e -> onOK());
        panel.add(alertLabel, BorderLayout.CENTER);
        panel.add(okButton, BorderLayout.SOUTH);
    }

    /**
     * @return the main JPanel
     */
    @Override
    public JPanel getMainPanel() {
        return panel;
    }

    public abstract void onOK();
}
