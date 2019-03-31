package bankmachine.gui;

import javax.swing.*;
import java.awt.*;

public abstract class SearchForm implements Form {
    private JPanel panel;
    private JLabel promptLabel;
    private JPanel clientSelectionGrid;
    private String prompt;
    private JButton cancelButton;

    public SearchForm(String prompt, JPanel clientSelectionGrid) {
        this.prompt = prompt;
        this.clientSelectionGrid = clientSelectionGrid;
    }

    /**
     * Creates all the UI Components required to display the GUI.
     */
    private void createUIComponents() {
        panel = new JPanel(new BorderLayout());
        promptLabel = new JLabel(prompt);
        promptLabel.setHorizontalAlignment(SwingConstants.CENTER);
        cancelButton = new JButton("Cancel");
        panel.add(promptLabel, BorderLayout.NORTH);
        if (clientSelectionGrid != null) {
            panel.add(clientSelectionGrid, BorderLayout.CENTER);
        }
        cancelButton.setContentAreaFilled(false);
        cancelButton.addActionListener(e -> onCancel());
        panel.add(cancelButton, BorderLayout.SOUTH);

    }

    /**
     * @return the main JPanel
     */
    @Override
    public JPanel getMainPanel() {
        return panel;
    }

    /**
     * Abstract method that determines what happens when the Cancel button is used.
     */
    public abstract void onCancel();
}
