package bankmachine.FileManager;

import java.io.File;

public class Main {

    public Main() {
        // Auto-constructor added for good practice
    }

    public static void main(String[] args) throws Exception { //Main method to test read / write of files


        // READING FILE TESTING
        String read_file = "input.txt";
        ReadFile in = new ReadFile(read_file);

        // Ignore this:
        //String filename = "filemanager_test.txt"; // Sets filename to our file
        //ReadFile file_to_read = new ReadFile(filename); // calls readfile on that file

        System.out.println("--- Read File Test ---");
        System.out.println("* FileName: " + in.getFileName()); // prints filename
        System.out.println("* Last Modified: " + in.getLastUpdated()); // prints date + time last modified
        System.out.println(" "); // Empty line for visuals in console -- TO BE REMOVED
        System.out.println("* File Contents: ");
        System.out.println(in.getData()); // Prints data of file */

        // Writing File

        WriteFile out = new WriteFile("output.txt");
        String output_content = "Adding this data to file";

        System.out.println("--- Write File Test ---");
        System.out.println("Writing String: " + output_content);
        //System.out.println(output_content);
        System.out.println("Filename: " + out.getFileName());
        out.writeData(output_content);
        System.out.println();
    }
}
