package bankmachine.fileManager;

import java.io.*;
import java.util.ArrayList;

// Managed by: Advaya

/**
 * Reads objects of type T from file
 *
 * @param <T> a serializeable type
 */
public class ObjectFileReader<T extends Serializable> {

    /* Name of the file to read */
    private String fileName;

    public ObjectFileReader(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Read contents of the file
     *
     * @return array list containing all objects (of type T) from file
     */
    public ArrayList<T> read() {
        ArrayList<T> arrayList = new ArrayList<>();
        FileInputStream fileIn = null;
        ObjectInputStream inputStream = null;
        try {
            fileIn = new FileInputStream(fileName);
            inputStream = new ObjectInputStream(fileIn);
            arrayList = (ArrayList<T>) inputStream.readObject();
            return arrayList;

        } catch (EOFException eof) {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                fileIn.close();
            } catch (IOException | NullPointerException e) {
                return new ArrayList<>();
            }
            return arrayList;
        } catch (IOException | ClassCastException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }
}
