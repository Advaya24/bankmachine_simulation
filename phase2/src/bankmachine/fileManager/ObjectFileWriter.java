package bankmachine.fileManager;


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Managed by: Advaya

/**
 * Writes objects of type T to file
 *
 * @param <T> a serializeable type
 */

public class ObjectFileWriter<T extends Serializable> {

    /* Name of the file to write in */
    private String fileName;

    public ObjectFileWriter(String fileName) {
        this.fileName = fileName;
        try {
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream inputStream = new ObjectInputStream(fileIn);
            inputStream.close();
            fileIn.close();
        } catch (IOException e) {
            try {
                FileOutputStream fileOut = new FileOutputStream(fileName);
                ObjectOutputStream outputStream = new ObjectOutputStream(fileOut);
                outputStream.writeObject(new ArrayList<T>());
                outputStream.close();
                fileOut.close();
            } catch (IOException e2) {
                System.out.println("File not created\n" + Arrays.toString(e2.getStackTrace()));
            }
        }
    }

    /**
     * Write obj to the file corresponding to filename
     *
     * @param obj object to write
     * @return true if write was successful, false otherwise
     */
    public boolean write(T obj) {
        ArrayList<T> arrayList = new ArrayList<>();
        arrayList.add(obj);
        return writeAll(arrayList);
    }

    /**
     * Write all objects of type T stored in arrayList to the file corresponding to filename
     *
     * @param arrayList of objects
     * @return true if write was successful, false otherwise
     */
    public boolean writeAll(List<T> arrayList) {
        try {
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream inputStream = new ObjectInputStream(fileIn);

            @SuppressWarnings("unchecked") List<T> oldArrayList = (ArrayList<T>) inputStream.readObject();

            inputStream.close();
            fileIn.close();

            FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOut);
            if (oldArrayList != null) {
                oldArrayList.addAll(arrayList);
                outputStream.writeObject(oldArrayList);
            } else {
                outputStream.writeObject(arrayList);
            }
            outputStream.flush();
            outputStream.close();
            fileOut.close();
            return true;
        } catch (IOException | ClassNotFoundException | ClassCastException e) { // ClassNotFoundException
            return false;
        }
    }

    /**
     * Remove all objects from the file
     */
    public void clear() {
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOut);
            outputStream.writeObject(new ArrayList<T>());
        } catch (IOException e) {
            System.out.println("Exception raised: " + Arrays.toString(e.getStackTrace()));
        }
    }
}
