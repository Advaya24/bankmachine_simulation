package bankmachine.FileManager;

import bankmachine.BankMachine;
import bankmachine.UserManager;
import bankmachine.BankMachineUser;
import bankmachine.Client;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Function;

public class Main {

    public Main() {
        // Auto-constructor added for good practice
    }

    public static void main(String[] args) throws Exception { //Main method to test read / write of files

        //File does not exist exception test
//        String exception_test = "DNE.txt";
//        ReadFile ex = new ReadFile(exception_test);
//        System.out.println(ex.getData());
//        System.out.println(ex.getLastUpdated());
//
//
        //Standard ReadFile test
        WriteFile out2 = new WriteFile("alerts.txt");
        String outputstuff = "ree";
        out2.writeData(outputstuff, true);
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

        // Setting up FileManager path
        final String fileManagerPath = BankMachine.fileManagerPath;

        // Test ObjectFileWriter and ObjectFileReader
        ObjectFileWriter<BankMachineUser> writer = new ObjectFileWriter<>(fileManagerPath + "/testObjectFile.ser");
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


//        fileSearcher.searchDirectory(new File(System.getProperty("user.dir")), "testClientData.ser");


        ObjectFileReader<BankMachineUser> reader = new ObjectFileReader<>(fileManagerPath + "/testObjectFile.ser");
        for (BankMachineUser object : reader.read()) {
            System.out.println("From file: " + object.getUsername());
        }


        // Test UserManager functionality
        UserManager<Client> clientManager = new UserManager<>(fileManagerPath + "/testClientData.ser");
        clientManager.add(new Client("ABC XYZ", "abc.xyz@gmail.com", "6661231234", "abc", "def"));

        Optional<Client> optionalClient = clientManager.authenticate("abc", "def");

        if (optionalClient.isPresent()) optionalClient.get().printAccountSummary();
        else System.out.println("Client not found :(");

        ArrayList<Client> clients = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            clients.add(new Client("Test " + i, "test" + i + "@gmail.com", "666124123" + i, "Test username " + i, "testPassword" + i));
        }
        clientManager.clearData();
        clientManager.addAll(clients);

        optionalClient = clientManager.get("abc");

        if (optionalClient.isPresent()) System.out.println("ABC didn't get deleted!");
        else System.out.println("Successfully deleted ABC");

        Optional<Client> testClient1 = clientManager.authenticate("Test username 1", "testPassword1");
        if (testClient1.isPresent()) testClient1.get().printAccountSummary();
        else System.out.println("Client not found :(");

        Optional<Client> testClient2 = clientManager.get("Test username 2");
        if (testClient2.isPresent()) {
            Client client = testClient2.get();
            client.printAccountSummary();
            client.setUserName("New username");
            clientManager.updateFile();
        } else System.out.println("Client not found :(");

        UserManager<Client> newClientManager = new UserManager<>(fileManagerPath + "/testClientData.ser");
        testClient2 = newClientManager.get("New username");
        if (testClient2.isPresent()) {
            Client client = testClient2.get();
            client.printAccountSummary();
        } else System.out.println("Didn't work");

        clientManager.runOnAll((Client c) -> {
            c.printAccountSummary();
            return null;
        });

    }

    private static void testFunction(Client c) {
        c.printAccountSummary();
    }

}
