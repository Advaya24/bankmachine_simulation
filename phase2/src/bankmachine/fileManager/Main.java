package bankmachine.fileManager;

import bankmachine.BankMachine;
import bankmachine.users.BankMachineUser;
import bankmachine.users.BankManager;
import bankmachine.users.Client;
import bankmachine.users.UserManager;

import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public Main() {
        // Auto-constructor added for good practice
    }

    public static void main(String[] args) throws IOException { //Main method to test read / write of files

        //File does not exist exception test
//        String exception_test = "DNE.txt";
//        ReadFile ex = new ReadFile(exception_test);
//        System.out.println(ex.getData());
//        System.out.println(ex.getLastUpdated());
//
//
        //Standard ReadFile test

        WriteFile out2 = new WriteFile("alerts.txt");
        String outputStuff = "ree";
        out2.writeData(outputStuff, true);
        String path = System.getProperty("user.dir");
        System.out.println(path);
        String read_file = "input.txt";
        ReadFile in = new ReadFile(read_file);

        System.out.println("--- Read File Test ---");
        System.out.println("* FileName: " + in.getFileName()); // prints filename
        System.out.println("* Last Modified: " + in.getLastUpdated()); // prints date + time last modified
        System.out.println(" "); // Empty line for visuals in console -- TO BE REMOVED
        System.out.println("* File Contents: ");
        System.out.println(in.getData()); // Prints data of file */

        //Standard Writing File test
        WriteFile out = new WriteFile("output.txt");
        String output_content = "**Output Content**";

        System.out.println("--- Write File Test ---");
        System.out.println("Writing String: " + output_content);
        System.out.println("Filename: " + out.getFileName());
        out.writeData(output_content, true);
        System.out.println();


        String output_content2 = "222222";
        out.writeData(output_content2, true);

        //No tester for writing to file that doesn't exist. As that file will simply be created

        //Test for TimeInfo Class
//        TimeInfo tf = new TimeInfo(); //Initializes new TimeInfo object
//        tf.setTime("2018-07-31T9:12:54"); // Sets date and time in ISO 8601
//        tf.getTime(); // Returns Date Object of ATM date + time

        // Setting up fileManager path
        final String fileManagerPath = BankMachine.DATA_PATH;

        // Test ObjectFileWriter and ObjectFileReader
        ObjectFileWriter<BankMachineUser> writer = new ObjectFileWriter<>(fileManagerPath + "/testObjectFile.ser");
        BankMachineUser singleUser = new BankManager(0, "Advaya", "hi@lol.com", "0552343", "Test username 1", "testPassword");

        writer.clear();
        if (writer.write(singleUser)) {
            System.out.println("Wrote single user to file");
        } else {
            System.out.println("Failed to write single user");
        }
        ArrayList<BankMachineUser> users = new ArrayList<>();
        for (int i = 2; i <= 5; i++) {
            users.add(new BankManager(i, "Name" + i, "email" + i + "@lol.com", "Number" + i, "Test username " + i, "testPassword" + i));
        }
        if (writer.writeAll(users)) {
            System.out.println("Wrote array list to file");
        } else {
            System.out.println("Failed to write array list");
        }


//        fileSearcher.searchDirectory(new File(System.getProperty("user.dir")), "testClientData.ser");


        ObjectFileReader<BankMachineUser> reader = new ObjectFileReader<>(fileManagerPath + "/testObjectFile.ser");
        for (BankMachineUser object : reader.read()) {
            System.out.println("From file: " + object.getUsername());
        }


        // Test Authenticator functionality
        UserManager clientManager = new UserManager(BankMachine.DATA_PATH);
        clientManager.newClient("ABC XYZ", "abc.xyz@gmail.com", "6661231234", "abc", "def");

        BankMachineUser optionalClient = clientManager.authenticate("abc", "def");

        if (optionalClient != null && optionalClient instanceof Client) {
            ((Client) optionalClient).printAccountSummary();
        } else {
            System.out.println("Client not found :(");
        }
        //BankMachine.USER_MANAGER.getMap().clear();
        for (int i = 1; i <= 5; i++) {
            clientManager.newClient("Test " + i, "test" + i + "@gmail.com", "666124123" + i, "Test username " + i, "testPassword" + i);
        }
        // We shouldn't delete clients
        /*
        optionalClient = clientManager.get("abc");

        if (optionalClient != null) System.out.println("ABC didn't get deleted!");
        else System.out.println("Successfully deleted ABC");
        */
        Client testClient1 = (Client) clientManager.authenticate("Test username 1", "testPassword1");
        if (testClient1 != null) testClient1.printAccountSummary();
        else System.out.println("Client not found :(");

        Client testClient2 = (Client) clientManager.get("Test username 2");
        if (testClient2 != null) {
            testClient2.printAccountSummary();
            testClient2.setUserName("New username");
        } else System.out.println("Client not found :(");

        clientManager.runOnAll((BankMachineUser c) -> {
            if (c instanceof Client) {
                ((Client) c).printAccountSummary();
            }
            return null;
        });

    }

    private static void testFunction(Client c) {
        c.printAccountSummary();
    }

}
