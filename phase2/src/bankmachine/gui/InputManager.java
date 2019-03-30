package bankmachine.gui;


import bankmachine.BankMachine;
import bankmachine.exception.InvalidEmailException;
import bankmachine.exception.InvalidPhoneNumberException;

import javax.swing.*;
import java.awt.*;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class InputManager extends JFrame {

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

    public void checkEmail(String email) throws InvalidEmailException {
        if (!emailRe.matcher(email).matches()) {
            throw new InvalidEmailException();
        }
    }

    public void checkPhone(String phone) throws InvalidPhoneNumberException {
        if (!phoneRe.matcher(phone).matches()) {
            throw new InvalidPhoneNumberException();
        }
    }

    private Scanner input;

    public static void main(String[] args) {
        new InputManager().mainLoop();
    }

    public InputManager() {
        input = new Scanner(System.in);
        this.setSize(new Dimension(800, 800));
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

    public LocalDateTime getDate() {

        while (true) {
            int year = getInteger("Enter the year: ");
            int month = getInteger("Enter the month (1-12): ");
            int day = getInteger("Enter the day (1-31): ");
            try {
                return LocalDateTime.of(year, month, day, 0, 0);
            } catch (DateTimeException e) {
                System.out.println("Invalid date");
            }
        }
    }


    public void setPanel(Form form) {
        this.getContentPane().removeAll();
        this.repaint();
        this.getContentPane().add(form.getMainPanel(), BorderLayout.CENTER);
        this.setVisible(true);
    }

    /**
     * Entry point for log in and all other actions
     */
    public void mainLoop() {
        new LoginGUI().handleInput(this);
    }

    public void exit() {
        BankMachine.USER_MANAGER.saveData();
        this.dispose();
        System.exit(0);
    }


    // Following method kept for future use:
//    private void PerformTransaction(Account a) {
//        // call methods from the specific account
//    }
}
