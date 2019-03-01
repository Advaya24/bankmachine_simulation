package bankmachine.FileManager;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;

public class WriteFile implements FileManager {

    private File file;

    // Constructor for WriteFile
    public WriteFile(String filename) {
        // Creates file with "filename" in the FileManager package
        this.file = new File("phase1/src/bankmachine/FileManager", filename);
    }

    public WriteFile() {
        this.file = null;
    }

    //Getter for filename. Returns null if file is null.
    @Override
    public String getFileName() {
        String filename;

        if (file == null)
            filename = null;
        else
            filename = file.getName();

        return filename;
    }

    //Getter for date and time when file was lasted edited (mm/dd/yyy HH:mm:ss)
    @Override
    public String getLastUpdated() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        return sdf.format(file.lastModified());
    }

    // Main Method for writing target data into file. Appends file instead of overwriting existing.
    public void writeData(String data) throws Exception {
        FileOutputStream output_stream = new FileOutputStream(file, true);
        output_stream.write(data.getBytes());
        output_stream.close();
    }
}
