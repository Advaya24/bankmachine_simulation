package bankmachine.gui;

import bankmachine.*;
import bankmachine.exception.ShutdownException;
import com.sun.istack.internal.Nullable;

import javax.swing.*;
import java.awt.*;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;

public class InputManager extends JFrame{

    /** Flag to check if mainLoop should be exited */
    private boolean exit = false;
    /**
     * Alias of static user manager from bankmachine
     */
    private UserManager userManager = BankMachine.USER_MANAGER;
    /**
     * Pattern for emails
     */
    private Pattern emailRe = Pattern.compile(
            "^[a-zA-Z0-9.\\-_]+@[a-zA-Z0-9.\\-_]+\\.[a-zA-Z]{2,3}$"
    );
    /**
     * Pattern for phone numbers
     */
    private Pattern phoneRe = Pattern.compile(
            "^\\(?\\d{3}\\)?-\\d{3}-\\d{4}$"
    );
    /**
     * Pattern for double
     */
    private Pattern doubleRe = Pattern.compile(
            "^\\d+(.\\d\\d?)?$"
    );

    private String getValue(Pattern p, String name) {
        while (true) {
            String email = getInput("Please enter a(n) " + name + ": ");
            if (p.matcher(email).matches()) {
                return email;
            }
            System.out.println("Invalid " + name);
        }
    }

    public String getEmail() {
        return getValue(emailRe, "email");
    }

    public String getPhone() {
        return getValue(phoneRe, "phone number");
    }

    public double getMoney() {
        while (true) {
            String s = getValue(doubleRe, "amount");
            try {
                return Double.parseDouble(s);
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount");
            }
        }
    }

    public int getInteger(String prompt) {
        while (true) {
            int i;
            try {
                i = Integer.parseInt(getInput(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Invalid integer");
                continue;
            }
            if (i < 0) {
                System.out.println("Positive numbers only please");
            } else {
                return i;
            }
        }
    }

    private Scanner input;

    public static void main(String[] args) {
        new InputManager().mainLoop();
    }

    public InputManager() {
        input = new Scanner(System.in);
        this.setSize(new Dimension(640, 480));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Prompts for input
    public String getInput(String s) {
        System.out.print(s);
        return input.nextLine();
    }

    private String getInput(Object[] objects) {
        printObjects(objects);
        return input.nextLine();
    }

    private void printObjects(Object[] objects) {
        for (Object o : objects) {
            System.out.print(o);
        }
    }

    public LocalDateTime getDate(){
        LocalDateTime datetime;
        while (true){
            int year = getInteger("Enter the year: ");
            int month = getInteger("Enter the month (1-12): ");
            int day = getInteger("Enter the day (1-31): ");
            try {
                return LocalDateTime.of(year, month, day, 0, 0);
            } catch (DateTimeException e){
                System.out.println("Invalid date");
            }
        }
    }

    /**
     * Displays menu of items to choose from, takes input and returns the chosen item
     *
     * @param items the list of options
     * @param <T>   the type of the options
     * @return item chosen
     */
    @Nullable
    public <T> T selectItem(List<T> items) {
        if (items.size() == 0) {
            return null;
        }

        for (int i = 0; i < items.size(); i++) {
            printObjects(new Object[]{"[", i + 1, "] "});
            System.out.println(items.get(i).toString());
        }

        int index;
        while (true) {
            String number = getInput(new Object[]{
                    "Enter a number from 1 to ", items.size(), ": "
            });
            try {
                index = Integer.parseInt(number);
            } catch (NumberFormatException e) {
                continue;
            }
            if (0 < index && index <= items.size()) {
                break;
            }
        }
        return items.get(index - 1);
    }

    public void setPanel(Form form){
        this.getContentPane().removeAll();
        this.repaint();
        this.getContentPane().add(form.getMainPanel(), BorderLayout.CENTER);
        this.setVisible(true);
    }

    private void attemptLogin(LoginForm form, String uname, String pass){
        BankMachineUser user = userManager.authenticate(uname, pass);
        if (user == null) {
            form.displayInvalid();
        } else {
            setPanel(new OptionsForm<String>(new String[]{"a", "b", "c"}){
                @Override
                public void onSelection(String s) {
                    System.out.println("Selected "+s);
                }
            });
        }
    }

    /**
     * Entry point for log in and all other actions
     */
    public void mainLoop() {
        this.setPanel(new LoginForm(){
            @Override
            public void onLogin() {
                attemptLogin(this, getName(), getPass());
            }
        });
    }

    /**
     * Display when there are multiple choices
     *
     * @param strings the strings to itemize
     * @return the response from the user
     */
    private String itemizeChoice(ArrayList<String> strings) {
        System.out.println("Please choose an option");
        String response = selectItem(strings);
        System.out.println("You chose " + response);
        return response;
    }

    //Verify if username and password exist and returns the accounts of the user.
    private BankMachineUser logIn() {
        while (true) {
            String username = getInput("Enter username: ");
            String password = getInput("Enter password: ");
            BankMachineUser user = userManager.authenticate(username, password);
            if (user == null) {
                System.out.println("Incorrect username/password");
            } else {
                return user;
            }
        }
    }

    // Following method kept for future use:
//    private void PerformTransaction(Account a) {
//        // call methods from the specific account
//    }
}
