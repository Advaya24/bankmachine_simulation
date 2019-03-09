package bankmachine.fileManager;

import bankmachine.BankMachine;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * File Writing class for the output to txt files (e.g. outgoing.txt)
 * Used by other classes that handle the respective methods.
 */

public class WriteFile implements FileManager {

    private File file;

    /**
     * Constructor for WriteFile class. Obtains filename attribute and sets file to the file in that path.
     *
     * @param filename Target filename for writing
     * @throws Exception throws NullPointerException if file in directory points to null
     */
    public WriteFile(String filename) {
        // Creates file with "filename" in the fileManager package
        //System.out.println(DATA_PATH + "/" + filename);
        this.file = new File(BankMachine.DATA_PATH + "/" + filename); //
    }

    public WriteFile() {
        this.file = null;
    }

    /**
     * Getter that returns filename.
     *
     * @return String filename
     */
    @Override
    public String getFileName() {
        String filename;

        if (file == null)
            filename = null;
        else
            filename = file.getName();

        return filename;
    }

    /**
     * Getter for date and time when file was lasted edited (mm/dd/yyy HH:mm:ss)
     */
    @Override
    public String getLastUpdated() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        return sdf.format(file.lastModified());
    }

    /**
     * Main Method for writing target data into file. Appends file instead of overwriting existing.
     *
     * @param data   data to be written
     * @param append boolean whether to append to existing data (true) or overwrite (false)
     */
    public boolean writeData(String data, Boolean append) {
        try (FileOutputStream output_stream = new FileOutputStream(file, append)) {
            output_stream.write(data.getBytes());
        } catch (IOException e) {
            return false;
        }
        return true;
    }

}
