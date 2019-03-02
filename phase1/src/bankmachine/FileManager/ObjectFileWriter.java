package bankmachine.FileManager;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class ObjectFileWriter {

    private FileOutputStream fileOut;
    private ObjectOutputStream outputStream;

    // TODO: Make this throwable instead of handling exception here?
    ObjectFileWriter(String fileName) {
        try {
            fileOut = new FileOutputStream(fileName);
            outputStream = new ObjectOutputStream(fileOut);
            fileOut.close();
            outputStream.close();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    public boolean write(Serializable obj) {
        try {
            outputStream.writeObject(obj);
            outputStream.close();
            fileOut.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean write(ArrayList<Serializable> arrayList) {
        try {
            for (Serializable obj : arrayList) {
                outputStream.writeObject(obj);
            }
            outputStream.close();
            fileOut.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
