package bankmachine.gui;

import javax.swing.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<String> sampleChoices = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            sampleChoices.add("Sample Choice " + i);
        }
        JFrame frame = new JFrame("ChoiceForm");
        frame.setContentPane(new ChoiceForm(sampleChoices).panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
