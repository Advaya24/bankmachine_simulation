package bankmachine.fileManager;

import bankmachine.BankMachine;

import java.io.File;
import java.text.SimpleDateFormat;
import java.io.FileReader;

public class ReadFile implements FileManager {

    private File file; // Sets private file as java.io.File type.

    public ReadFile(String filename) throws Exception { // Constructor for ReadFile

        try {
            //System.out.println(DATA_PATH + "/" + filename);
            this.file = new File(BankMachine.DATA_PATH + "/" + filename); //
        } catch  (NullPointerException e){
            System.out.println("The file attempted to be read does not exist");
        }

    }

    public ReadFile() { // Alternate Constructor for ReadFile if no filename is passed in sets file to null

        this.file = null;
    }

    @Override
    public String getFileName() { // Method for returning file name in system
        String filename;

        if (file == null) // If no filename was passed in returns null for file name
            filename = null;
        else
            filename = file.getName(); // Using java.io.File gets file name otherwise

        return filename;
    }

    @Override
    public String getLastUpdated() { // Method for returning system time of when file was last last modified

        if(file == null){
            return null;
        }
        //Declares last_modified as a SDF object
        SimpleDateFormat last_modified = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        return last_modified.format(file.lastModified());
    }

    public String getData() throws Exception {

        StringBuilder str = new StringBuilder(); // Initializes the output string for the data.

        // If the file is null sets string equal to null and then for getData returns null
        if(file == null){
            str = null;

        } else { // If file is not null

            // Assigns file_reader variable to FileReader object of target file
            FileReader file_reader = new FileReader(file);

            // Variable needed to cycle through stream of characters (=0)
            int end_of_stream;

            // Loop to cycle through array of characters from file. End of file is reached when read() returns -1.
            while ((end_of_stream = file_reader.read()) != -1) {
                //Appends data to output string
                str.append((char) end_of_stream);

            }
            // Closes file (important)
            file_reader.close();
        }
        return str.toString();


    }

}
