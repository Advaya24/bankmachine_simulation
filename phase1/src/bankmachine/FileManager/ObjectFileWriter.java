package bankmachine.FileManager;


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ObjectFileWriter {

//    private FileOutputStream fileOut;
//    private ObjectOutputStream outputStream;
    private String fileName;

    // TODO: Make this throwable instead of handling exception here?
    ObjectFileWriter(String fileName) {
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
                outputStream.writeObject(new ArrayList<Serializable>());
                outputStream.close();
                fileOut.close();
            } catch (IOException e2) {
                System.out.println("File not created\n" + Arrays.toString(e2.getStackTrace()));
            }
        }
//        try {
//            fileOut = new FileOutputStream(fileName);
//            outputStream = new ObjectOutputStream(fileOut);
////            fileOut.close();
////            outputStream.close();
//        } catch (IOException e) {
//            System.out.println(e.toString());
//        }
    }

    public boolean write(Serializable obj) {
//        try {
//            FileInputStream fileIn = new FileInputStream(fileName);
//            ObjectInputStream inputStream = new ObjectInputStream(fileIn);
//
//            ArrayList<Serializable> arrayList = (ArrayList<Serializable>)inputStream.readObject();
//
//            inputStream.close();
//            fileIn.close();
//
//            FileOutputStream fileOut = new FileOutputStream(fileName);
//            ObjectOutputStream outputStream = new ObjectOutputStream(fileOut);
//            if (arrayList != null) {
//                arrayList.add(obj);
//                outputStream.writeObject(arrayList);
//            } else {
//                arrayList = new ArrayList<>();
//                arrayList.add(obj);
//                outputStream.writeObject(arrayList);
//            }
//            outputStream.flush();
//            outputStream.close();
//            fileOut.close();
//            return true;
//        } catch (IOException | ClassNotFoundException | ClassCastException e) { // ClassNotFoundException
//            return false;
//        }
        ArrayList<Serializable> arrayList = new ArrayList<>();
        arrayList.add(obj);
        return writeAll(arrayList);
    }

    public boolean writeAll(ArrayList<Serializable> arrayList) {
//        try {
//            FileOutputStream fileOut = new FileOutputStream(fileName);
//            ObjectOutputStream outputStream = new ObjectOutputStream(fileOut);
//            for (Serializable obj : arrayList) {
//                outputStream.writeObject(obj);
//                outputStream.flush();
//            }
//            outputStream.close();
//            fileOut.close();
//            return true;
//        } catch (IOException e) {
//            return false;
//        }
        try {
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream inputStream = new ObjectInputStream(fileIn);

            ArrayList<Serializable> oldArrayList = (ArrayList<Serializable>)inputStream.readObject();

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

    public void clear() {
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOut);
            outputStream.writeObject(new ArrayList<Serializable>());
        } catch (IOException e) {
            System.out.println("Exception raised: " + Arrays.toString(e.getStackTrace()));
        }
    }
}
