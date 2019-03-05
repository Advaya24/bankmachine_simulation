package bankmachine.FileManager;

import bankmachine.BankMachine;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;

public class WriteFile implements FileManager {

    private File file;

    // Constructor for WriteFile
    public WriteFile(String filename) {
        // Creates file with "filename" in the FileManager package
        //System.out.println(fileManagerPath + "/" + filename);
        this.file = new File(BankMachine.fileManagerPath + "/" + filename); //
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
    // Data: data to be written  && append: boolean whether to append to existing data (true) or overwrite (false)
    public void writeData(String data, Boolean append) throws Exception {
        FileOutputStream output_stream = new FileOutputStream(file, append);
        output_stream.write(data.getBytes());
        output_stream.close();
    }

}
