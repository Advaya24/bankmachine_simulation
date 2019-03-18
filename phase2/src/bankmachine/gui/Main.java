package bankmachine.gui;

import javax.swing.*;
import java.util.ArrayList;

public class Main {
    public static ArrayList<String> sampleChoices = new ArrayList<>();
    public static GUISupportable listener = new GUISupportableDemo();

    public static void main(String[] args) {

        for (int i = 1; i < 6; i++) {
            sampleChoices.add("Sample Choice " + i);
        }
        JFrame frame = new JFrame("ChoiceForm");
        frame.setContentPane(new ChoiceForm(sampleChoices, listener).panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void action() {
        System.out.println(sampleChoices.indexOf(((GUISupportableDemo) listener).getSelectedString()));
    }

//    public void buttonClicked(){
//        System.out.println("Clicked");
//    }
}
