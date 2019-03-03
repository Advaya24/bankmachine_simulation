package bankmachine.FileManager;

import bankmachine.Authenticator;
import bankmachine.BankMachineUser;
import bankmachine.Client;
import bankmachine.LoginType;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public class Main {

    public Main() {
        // Auto-constructor added for good practice
    }

    public static void main(String[] args) throws Exception { //Main method to test read / write of files

        // TODO: UNCOMMENT THIS CODE!
        //File does not exist exception test
//        String exception_test = "DNE.txt";
//        ReadFile ex = new ReadFile(exception_test);
//        System.out.println(ex.getData());
//        System.out.println(ex.getLastUpdated());
//
//
        //Standard ReadFile test

//        String path = System.getProperty("user.dir");
//        System.out.println(path);
//        String read_file = "input.txt";
//        ReadFile in = new ReadFile(read_file);
//
//        System.out.println("--- Read File Test ---");
//        System.out.println("* FileName: " + in.getFileName()); // prints filename
//        System.out.println("* Last Modified: " + in.getLastUpdated()); // prints date + time last modified
//        System.out.println(" "); // Empty line for visuals in console -- TO BE REMOVED
//        System.out.println("* File Contents: ");
//        System.out.println(in.getData()); // Prints data of file */
//
//        //Standard Writing File test
//        WriteFile out = new WriteFile("output.txt");
//        String output_content = "**Output Content**";
//
//        System.out.println("--- Write File Test ---");
//        System.out.println("Writing String: " + output_content);
//        System.out.println("Filename: " + out.getFileName());
//        out.writeData(output_content, true);
//        System.out.println();

//
//        String output_content2 = "222222";
//        out.writeData(output_content2, true);
//
//        //No tester for writing to file that doesn't exist. As that file will simply be created
//
//        //Test for TimeInfo Class
//        TimeInfo tf = new TimeInfo(); //Initalizes new TimeInfo object
//        tf.setTime("31/07/2018 9:12:54"); // Sets date and time format: "dd/mm/yyyy hh:mm:ss" (Bank manager)
//        tf.getTime(); // Returns Date Object of ATM date + time

        // Test ObjectFileWriter and ObjectFileReader
        ObjectFileWriter<BankMachineUser> writer = new ObjectFileWriter<>("src/bankmachine/FileManager/testObjectFile.ser");
        BankMachineUser singleUser = new BankMachineUser("Test username 1", "testPassword");

        writer.clear();
        if (writer.write(singleUser)) {
            System.out.println("Wrote single user to file");
        } else {
            System.out.println("Failed to write single user");
        }
        ArrayList<BankMachineUser> users = new ArrayList<>();
        for (int i = 2; i <= 5; i++) {
            users.add(new BankMachineUser("Test username " + i, "testPassword" + i));
        }
        if (writer.writeAll(users)) {
            System.out.println("Wrote array list to file");
        } else {
            System.out.println("Failed to write array list");
        }

        ObjectFileReader<BankMachineUser> reader = new ObjectFileReader<>("src/bankmachine/FileManager/testObjectFile.ser");
        for (BankMachineUser object : reader.read()) {
            System.out.println("From file: " + object.getUsername());
        }


        // Test Authenticator functionality
        Authenticator<Client> authenticator = new Authenticator<>("src/bankmachine/FileManager/testClientData.ser");
        authenticator.add(new Client("ABC XYZ", "abc.xyz@gmail.com", "6661231234", "abc", "def"));

        Optional<Client> optionalClient = authenticator.authenticate("abc", "def");

        if (optionalClient.isPresent()) optionalClient.get().printAccountSummary();
        else System.out.println("Client not found :(");

        ArrayList<Client> clients = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            clients.add(new Client("Test " + i, "test" + i + "@gmail.com", "666124123" + i, "Test username " + i, "testPassword" + i));
        }
        authenticator.clearData();
        authenticator.addAll(clients);

        optionalClient = authenticator.get("abc");

        if (optionalClient.isPresent()) System.out.println("ABC didn't get deleted!");
        else System.out.println("Successfully deleted ABC");

        Optional<Client> testClient1 = authenticator.authenticate("Test username 1", "testPassword1");
        if (testClient1.isPresent()) testClient1.get().printAccountSummary();
        else System.out.println("Client not found :(");

        Optional<Client> testClient2 = authenticator.get("Test username 2");
        if (testClient2.isPresent()) testClient2.get().printAccountSummary();
        else System.out.println("Client not found :(");

        final FileSearch fileSearch = new FileSearch();

//        fileSearch.searchDirectory(new File(System.getProperty("user.dir")), "testClientData.ser");

        fileSearch.setFileNameToSearch("FileManager");
        fileSearch.searchForDirectory(new File(System.getProperty("user.dir")));
        System.out.println(fileSearch.getResult());
    }

    static class FileSearch {

        private String fileNameToSearch;
        private List<String> result = new ArrayList<String>();

        public String getFileNameToSearch() {
            return fileNameToSearch;
        }

        public void setFileNameToSearch(String fileNameToSearch) {
            this.fileNameToSearch = fileNameToSearch;
        }

        public List<String> getResult() {
            return result;
        }

        public void searchDirectory(File directory, String fileNameToSearch) {

            setFileNameToSearch(fileNameToSearch);

            if (directory.isDirectory()) {
                search(directory);
            } else {
                System.out.println(directory.getAbsoluteFile() + " is not a directory!");
            }

        }

        private void search(File file) {

            if (file.isDirectory()) {
                System.out.println("Searching directory ... " + file.getAbsoluteFile());

                //do you have permission to read this directory?
                if (file.canRead()) {
                    for (File temp : file.listFiles()) {
                        if (temp.isDirectory()) {
                            search(temp);
                        } else {
                            if (getFileNameToSearch().equalsIgnoreCase(temp.getName())) {
                                result.add(temp.getAbsoluteFile().toString());
                            }

                        }
                    }

                } else {
                    System.out.println(file.getAbsoluteFile() + "Permission Denied");
                }
            }

        }

        private void searchForDirectory(File file) {
            if (file.isDirectory()) {
                System.out.println("Searching directory ... " + file.getAbsoluteFile());

                //do you have permission to read this directory?
                if (file.canRead()) {
                    for (File temp : file.listFiles()) {
                        if (temp.isDirectory()) {
                            if (getFileNameToSearch().equalsIgnoreCase(temp.getName())) {
                                result.add(temp.getAbsoluteFile().toString());
                            }
                            searchForDirectory(temp);
                        }
                    }

                } else {
                    System.out.println(file.getAbsoluteFile() + "Permission Denied");
                }
            }
        }


    }
}
