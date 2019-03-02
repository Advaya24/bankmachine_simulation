package bankmachine.FileManager;

import java.io.*;
import java.util.ArrayList;

public class ObjectFileReader {

    private String fileName;

    ObjectFileReader(String fileName) {
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
    public ArrayList<Serializable> read() {
        ArrayList<Serializable> arrayList = new ArrayList<>();
        FileInputStream fileIn = null;
        ObjectInputStream inputStream = null;
        try {
            fileIn = new FileInputStream(fileName);
            inputStream = new ObjectInputStream(fileIn);

            while (true) {
                arrayList.add((Serializable)inputStream.readObject());
            }

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
