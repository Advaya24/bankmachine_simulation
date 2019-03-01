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

        //System.out.println( new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()) );
        //System.out.println(new SimpleDateFormat("MM/dd/yyyy").format(Calendar.getInstance().getTime()));


        TimeInfo tf = new TimeInfo();
        //tf.setTime(1,22,34);
        System.out.println(tf.getTime());

    }
}
