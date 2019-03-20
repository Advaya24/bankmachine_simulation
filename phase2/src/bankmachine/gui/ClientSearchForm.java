package bankmachine.gui;

import javax.swing.*;
import java.awt.*;

public class ClientSearchForm implements Form {
    private JPanel panel;
    private JLabel promptLabel;
    private JPanel clientSelectionGrid;
    private String prompt;

    public ClientSearchForm(String prompt, JPanel clientSelectionGrid) {
        this.prompt = prompt;
        this.clientSelectionGrid = clientSelectionGrid;
    }

    private void createUIComponents() {
        panel = new JPanel(new BorderLayout());
        promptLabel = new JLabel(prompt);
        panel.add(promptLabel, BorderLayout.NORTH);
        if (clientSelectionGrid != null) {
            panel.add(clientSelectionGrid, BorderLayout.CENTER);
        }
    }

    @Override
    public JPanel getMainPanel() {
        return panel;
    }
}
