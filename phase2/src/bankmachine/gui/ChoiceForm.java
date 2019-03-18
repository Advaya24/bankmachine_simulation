package bankmachine.gui;

import javafx.scene.control.ComboBox;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ChoiceForm {
    JPanel panel1;
    private JComboBox comboBox1;
    private JButton OKButton;
//    private Container contentPane;

    public ChoiceForm(ArrayList<String> choices) {
        for (String choice: choices) {
            comboBox1.addItem(choice);
        }
//        contentPane = new Container();
    }

    public static void main(String[] args) {
//        JFrame frame = new JFrame("ChoiceForm");
//        frame.setContentPane(new ChoiceForm(new ArrayList<>()).panel1);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
//        frame.setVisible(true);
    }
}
