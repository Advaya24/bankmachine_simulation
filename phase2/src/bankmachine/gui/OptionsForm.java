package bankmachine.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class OptionsForm<T> implements Form {
    private T options[];
    private JPanel panel;
    private JPanel buttonGrid;

    public OptionsForm(T[] options){
        this.options = options;
    }

    public JPanel getMainPanel(){
        return panel;
    }

    public abstract void onSelection(T t);

    private void createUIComponents() {
        buttonGrid = new JPanel(new GridLayout(0,2));
        for (int i=0; i<options.length; i++){
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
    }
}
