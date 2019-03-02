package bankmachine.FileManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ObjectFileReader {

    private FileInputStream fileIn;
    private ObjectInputStream inputStream;

    ObjectFileReader(String fileName) throws IOException {
        fileIn = new FileInputStream(fileName);
        inputStream = new ObjectInputStream(inputStream);
        fileIn.close();
        inputStream.close();
    }




}
