package bankmachine.FileManager;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Main {

    public Main() {
        // Auto-constructor added for good practice
    }

    public static void main(String[] args) throws Exception { //Main method to test read / write of files

        //File does not exist exception test
        String exception_test = "DNE.txt";
        ReadFile ex = new ReadFile(exception_test);
        System.out.println(ex.getData());
        System.out.println(ex.getLastUpdated());


        //Standard ReadFile test
        String read_file = "input.txt";
        ReadFile in = new ReadFile(read_file);

        System.out.println("--- Read File Test ---");
        System.out.println("* FileName: " + in.getFileName()); // prints filename
        System.out.println("* Last Modified: " + in.getLastUpdated()); // prints date + time last modified
        System.out.println(" "); // Empty line for visuals in console -- TO BE REMOVED
        System.out.println("* File Contents: ");
        System.out.println(in.getData()); // Prints data of file */

        //Standard Writing File test
        WriteFile out = new WriteFile("output.txt");
        String output_content = "**Output Content**";

        System.out.println("--- Write File Test ---");
        System.out.println("Writing String: " + output_content);
        System.out.println("Filename: " + out.getFileName());
        out.writeData(output_content, true);
        System.out.println();


        String output_content2 = "222222";
        out.writeData(output_content2, true);

        //No tester for writing to file that doesn't exist. As that file will simply be created

        //Test for TimeInfo Class
        TimeInfo tf = new TimeInfo(); //Initalizes new TimeInfo object
        tf.setTime("31/07/2018 9:12:54"); // Sets date and time format: "dd/mm/yyyy hh:mm:ss" (Bank manager)
        tf.getTime(); // Returns Date Object of ATM date + time
    }
}
