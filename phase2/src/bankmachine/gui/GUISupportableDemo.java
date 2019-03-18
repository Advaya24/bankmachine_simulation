package bankmachine.gui;


import java.util.function.Function;

public class GUISupportableDemo implements GUISupportable {
    private String selectedString;
    private Function<Void, Void> function;
    public GUISupportableDemo(Function<Void, Void> function) {
        this.function = function;
    }
    @Override
    public void buttonClicked(String s) {
        System.out.println("Selected " + s);
        System.out.println(s.getClass());
        selectedString = s;
        function.apply(null);
    }

    public String getSelectedString(){
        return selectedString;
    }
}
