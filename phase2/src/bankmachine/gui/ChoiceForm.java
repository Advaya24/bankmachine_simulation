package bankmachine.gui;

import javafx.scene.control.ComboBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ChoiceForm {
    JPanel panel1;
    private JComboBox comboBox1;
    private JButton OKButton;
    private GUISupportable listener;
//    private Container contentPane;

    public ChoiceForm(ArrayList<String> choices, GUISupportable listener) {
        for (String choice: choices) {
            comboBox1.addItem(choice);
        }
//        contentPane = new Container();
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedProcess();
            }
        });
        this.listener = listener;
    }

    public static void main(String[] args) {
//        JFrame frame = new JFrame("ChoiceForm");
//        frame.setContentPane(new ChoiceForm(new ArrayList<>()).panel1);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
//        frame.setVisible(true);
    }

    public void selectedProcess() {
        listener.buttonClicked((String) comboBox1.getSelectedItem());
    }
}
