package bankmachine.gui;

public class GUISupportableDemo implements GUISupportable {
    private String selectedString;
    @Override
    public void buttonClicked(String s) {
        System.out.println("Selected " + s);
        System.out.println(s.getClass());
        selectedString = s;
        Main.action();
    }

    public String getSelectedString(){
        return selectedString;
    }
}
