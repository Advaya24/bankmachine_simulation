package bankmachine.FileManager;

import java.io.*;
import java.util.ArrayList;

/**
 * Reads objects of type T from file
 * @param <T> a serializeable type
 */
public class ObjectFileReader<T extends Serializable> {

    private String fileName;

    public ObjectFileReader(String fileName) {
        this.fileName = fileName;
    }

//    @Override
//    public Iterator<Serializable> iterator() {
//        return new Iterator<Serializable>() {
//            @Override
//            public boolean hasNext() {
//                try {
//                    FileInputStream fileIn = new FileInputStream(fileName);
//                    ObjectInputStream inputStream = new ObjectInputStream(fileIn);
//                    int available = inputStream.available();
//                    fileIn.close();
//                    inputStream.close();
//                    return available > 0;
//                } catch (IOException e) {
//                    return false;
//                }
//            }
//
//            @Override
//            public Serializable next() {
//                try {
//                    FileInputStream fileIn = new FileInputStream(fileName);
//                    ObjectInputStream inputStream = new ObjectInputStream(fileIn);
//                    Serializable obj = (Serializable)inputStream.readObject();
//                    fileIn.close();
//                    inputStream.close();
//                    return obj;
//                } catch (IOException | ClassNotFoundException e) {
//                    return null;
//                }
//            }
//        };
//    }

    /**
     * Read contents of the file
     * @return array list containing all objects (of type T) from file
     */
    public ArrayList<T> read() {
        ArrayList<T> arrayList = new ArrayList<>();
        FileInputStream fileIn = null;
        ObjectInputStream inputStream = null;
        try {
            fileIn = new FileInputStream(fileName);
            inputStream = new ObjectInputStream(fileIn);
//            boolean toContinue = true;
//            while (toContinue) {
//                Serializable obj = (Serializable)inputStream.readObject();
//                if (obj != null) {
//                    arrayList.add(obj);
//                } else {
//                    toContinue = false;
//                }
//            }
            arrayList = (ArrayList<T>)inputStream.readObject();
            return arrayList;

        } catch (EOFException eof) {
            try {
                fileIn.close();
                inputStream.close();
            } catch (IOException | NullPointerException e) {
                return new ArrayList<>();
            }
            return arrayList;
        } catch (IOException | ClassCastException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }
}
