package bankmachine.gui;

import javax.swing.*;
import java.awt.*;

public abstract class AccountSummaryForm implements Form {
    private JPanel panel;
    private JPanel labelGrid;
    private JLabel[] labels;
    private String[] messages;
    private JPanel accountsPanel;
    private JButton cancelButton;

    public AccountSummaryForm(String[] messages, JPanel accountsPanel) {
        this.messages = messages;
        this.accountsPanel = accountsPanel;
    }

    /**
     * @return the main JPanel
     */
    @Override
    public JPanel getMainPanel() {
        return panel;
    }

    /**
     * Creates all the UI Components required to display the GUI.
     */
    private void createUIComponents() {
        panel = new JPanel(new BorderLayout());
        labelGrid = new JPanel(new GridLayout(messages.length, 1));
        cancelButton = new JButton("Cancel");
        cancelButton.setContentAreaFilled(false);
        cancelButton.setHorizontalAlignment(SwingConstants.CENTER);
        cancelButton.addActionListener(e -> onCancel());

        labels = new JLabel[messages.length];
        for (int i = 0; i < messages.length; i++) {
            labels[i] = new JLabel(messages[i]);
            labels[i].setHorizontalAlignment(SwingConstants.CENTER);
            labelGrid.add(labels[i]);
        }

        panel.add(labelGrid, BorderLayout.NORTH);
        if (accountsPanel != null) {
            panel.add(accountsPanel, BorderLayout.CENTER);
        }
        panel.add(cancelButton, BorderLayout.SOUTH);
    }

    /**
     * Abstract method that determines what happens when the Cancel button is used.
     */
    public abstract void onCancel();
}
