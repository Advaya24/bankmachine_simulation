package bankmachine.FileManager;

import java.io.File;
import java.text.SimpleDateFormat;
import java.io.FileReader;

public class ReadFile implements FileManager {

    private File file; // Sets private file as java.io.File type.

    public ReadFile(String filename) { // Constructor for ReadFile

        //this.file = new File(filename);
        this.file = new File(ReadFile.class.getResource(filename).getFile());
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
        //Declares last_modified as a SDF object
        SimpleDateFormat last_modified = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        return last_modified.format(file.lastModified());
    }

    public String getData() throws Exception {

        FileReader file_reader = new FileReader(file);

        int end_of_stream;
        String str = "";
        while((end_of_stream=file_reader.read()) != -1){
            str += ((char)end_of_stream);

        }
        file_reader.close();
        return str;


    }

}
