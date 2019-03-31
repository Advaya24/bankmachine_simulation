package bankmachine.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class OptionsForm<T> implements Form {
    private T[] options;
    private JPanel panel;
    private JPanel buttonGrid;
    private JLabel promptLabel;
    private String prompt;

    public OptionsForm(T[] options, String prompt) {
        this.options = options;
        this.prompt = prompt;
    }

    /**
     * @return the main JPanel
     */
    public JPanel getMainPanel() {
        return panel;
    }

    /**
     * Abstract method that handles option selection
     * @param t a Generic Object
     */
    public abstract void onSelection(T t);

    /**
     * Creates all the UI Components required to display the GUI.
     */
    private void createUIComponents() {
        panel = new JPanel(new BorderLayout());
        buttonGrid = new JPanel(new GridLayout(0, 2));
        promptLabel = new JLabel(prompt);
        promptLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(promptLabel, BorderLayout.NORTH);
        for (int i = 0; i < options.length; i++) {
            JButton b = new JButton(options[i].toString());
            final int index = i;
            b.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    onSelection(options[index]);
                }
            });
            buttonGrid.add(b);
        }
        panel.add(buttonGrid, BorderLayout.CENTER);
    }
}
