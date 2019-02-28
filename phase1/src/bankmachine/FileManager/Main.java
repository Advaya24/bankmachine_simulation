package bankmachine.FileManager;


public class Main {

    public Main() {
        // Auto-constructor added for good practice
    }

    public static void main(String[] args) throws Exception { //Main method to test read / write of files
        String filename = "filemanager_test.txt"; // Sets filename to our file
        ReadFile file_to_read = new ReadFile(filename); // calls readfile on that file

        System.out.println("--- Read File Test ---");
        System.out.println("* FileName: " + file_to_read.getFileName()); // prints filename
        System.out.println("* Last Modified: " + file_to_read.getLastUpdated()); // prints date + time last modified
        System.out.println(" "); // Empty line for visuals in console -- TO BE REMOVED
        System.out.println("* File Contents: ");
        System.out.println(file_to_read.getData()); // Prints data of file
    }
}