package bankmachine.FileManager;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class ObjectFileWriter {

//    private FileOutputStream fileOut;
//    private ObjectOutputStream outputStream;
    private String fileName;

    // TODO: Make this throwable instead of handling exception here?
    ObjectFileWriter(String fileName) {
        this.fileName = fileName;
//        try {
//            fileOut = new FileOutputStream(fileName);
//            outputStream = new ObjectOutputStream(fileOut);
////            fileOut.close();
////            outputStream.close();
//        } catch (IOException e) {
//            System.out.println(e.toString());
//        }
    }

    public boolean write(Serializable obj, boolean toAppend) {
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName, toAppend);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOut);
            outputStream.writeObject(obj);
            outputStream.close();
            fileOut.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean writeAll(ArrayList<Serializable> arrayList, boolean toAppend) {
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName, toAppend);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOut);
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
