package bankmachine.gui;

import javax.swing.*;
import java.util.ArrayList;
import java.util.function.Function;

public class Main {
    public static ArrayList<String> sampleChoices = new ArrayList<>();
    public static GUISupportable listener = new GUISupportableDemo(aVoid -> {
        action();
        return null;
    });
    public static JFrame frame = new JFrame("ChoiceForm");

    public static void main(String[] args) {

        for (int i = 1; i < 6; i++) {
            sampleChoices.add("Sample Choice " + i);
        }

        frame.setContentPane(new ChoiceForm(sampleChoices, listener).panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void action() {
        System.out.println(sampleChoices.indexOf(((GUISupportableDemo) listener).getSelectedString()));
        frame.dispose(); // Removes frame
    }

//    public void buttonClicked(){
//        System.out.println("Clicked");
//    }
}
