package bankmachine.fileManager;
import bankmachine.BankMachine;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.io.FileReader;

/** File Reading class for the standardized text files (alerts.txt, outgoing.txt, deposits.txt
 * Used by other classes that handle the respective methods.
 */

public class ReadFile implements FileManager {

    private File file; // Sets private file as java.io.File type.

    /** Constructor for ReadFile class. Obtains filename attribute and sets file to the file in that path.
     *
     * @param filename Target filename for reading
     * @throws Exception throws NullPointerException if file in directory points to null
     */
    public ReadFile(String filename) throws NullPointerException { // Constructor for ReadFile

        try {
            this.file = new File(BankMachine.DATA_PATH + "/" + filename); //
        } catch  (NullPointerException e){
            throw new NullPointerException("File not found!");
        }

    }

    public ReadFile() { // Alternate Constructor for ReadFile if no filename is passed in sets file to null

        this.file = null;
    }

    /** Getter that returns filename.
     * @return String filename
     */
    @Override
    public String getFileName() { // Method for returning file name in system
        String filename;

        if (file == null) // If no filename was passed in returns null for file name
            filename = null;
        else
            filename = file.getName(); // Using java.io.File gets file name otherwise

        return filename;
    }

    /** Gets system time of when file was last updated.
     * @return String of system time in format: MM/dd/yyyy HH:mm:ss
     */
    @Override
    public String getLastUpdated() { // Method for returning system time of when file was last last modified

        if(file == null){
        }
        //Declares last_modified as a SDF object
        SimpleDateFormat last_modified = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        return last_modified.format(file.lastModified());
    }

    /** Method that obtains data in target file.
     * @return Returns data as string.
     * @throws Exception FileNotFoundException if file does not exist and method is called.
     */
    public String getData() throws Exception {

        StringBuilder str = new StringBuilder(); // Initializes the output string for the data.

        try{
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
            return str.toString();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File not found!");
        }



    }

}
