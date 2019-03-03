package bankmachine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InputManager {
    private Scanner input;
    public static void main(String[] args) {
        new InputManager().mainLoop();
    }

    public InputManager(){
        input = new Scanner(System.in);
    }

    // Prompts for input
    private String getInput(String s) {
        System.out.print(s);
        return input.next();
    }

    private String getInput(Object[] objects){
        printObjects(objects);
        return input.next();
    }
    private void printObjects(Object[] objects){
        for (Object o:objects){
            System.out.print(o);
        }
    }

    private <T> T selectItem(List<T> items){
        if(items.size() == 0){ return null; }
        else if(items.size() == 1){ return items.get(0); }

        for(int i=0; i<items.size(); i++){
            printObjects(new Object[]{"[", i, "] "});
            System.out.println(items.get(i).toString());
        }

        int index;
        while(true){
            String number = getInput(new Object[]{
                    "Enter a number from 0 to ", items.size()-1, ": "
            });
            try{
                index = Integer.parseInt(number);
            } catch(NumberFormatException e){
                continue;
            }
            if (0<=index && index < items.size()){
                break;
            }
        }
        return items.get(index);
    }

    private void selectItemTest(){
        ArrayList<String> strings = new ArrayList<>(Arrays.asList(
                "Hakurei Reimu", "Marisa Kirisame", "Sanae Kochiya"
        ));
        System.out.println("Choose a name");
        String response = selectItem(strings);
        System.out.println("You chose " + response);
    }

    public void mainLoop(){
        // Login page
        // TODO: Change the conditions in the while loops so that it tries to find the list of usernames and
        //  corresponding password
        while(true) {
            String a = "John";
            String b = "123";
            String username = getInput("Enter username: ");
            if(username.equalsIgnoreCase("exit")){
                break;
            }
            String password = getInput("Enter password: ");
            if (username.equals(a) && password.equals(b)){
                System.out.println("Welcome!");
                selectItemTest();
                System.out.println("Goodbye");
            } else {
                System.out.println("Incorrect username/password");
            }
        }
    }


}
