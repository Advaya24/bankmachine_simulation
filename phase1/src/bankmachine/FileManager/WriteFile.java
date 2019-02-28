package bankmachine.FileManager;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;

public class WriteFile implements FileManager {

    private File file;

    public WriteFile(String filename) {

        //this.file = new File(filename);

        // Creates file with "filenamae" in the FileManager package
        this.file = new File("phase1/src/bankmachine/FileManager", filename);
    }

    public WriteFile() {
        this.file = null;
    }

    @Override
    public String getFileName() {
        String filename;

        if (file == null)
            filename = null;
        else
            filename = file.getName();

        return filename;
    }

    @Override
    public String getLastUpdated() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        return sdf.format(file.lastModified());
    }

    public void writeData(String str) throws Exception {
        FileOutputStream fos = new FileOutputStream(file, false);
        fos.write(str.getBytes());
        fos.close();
    }
}
