package bankmachine;

import bankmachine.FileManager.FileSearcher;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserManagerTest {
    private static UserManager<Client> clientUserManager;
    private static Client client;
    private static ArrayList<Client> clients;

    //    @BeforeAll
//    public static void setClientAuthenticator() {
//
//    }
//    @BeforeAll
//    public static void setClientAuthenticator() {
//
//    }
    @BeforeAll
    public static void setFileName(){
        FileSearcher fileSearcher = new FileSearcher();
        fileSearcher.setFileNameToSearch("FileManager");
        fileSearcher.searchForDirectory(new File(System.getProperty("user.dir")));
        final String fileManagerPath = fileSearcher.getResult().get(0);
        String fileName = fileManagerPath + "/testClientData.ser";
        clientUserManager = new UserManager<>(fileName);
        clientUserManager.clearData();
        client = mock(Client.class, withSettings().serializable());

        when(client.getUsername()).thenReturn("abc");
        when(client.getPassword()).thenReturn("def");


        clients = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Client newClient = mock(Client.class, withSettings().serializable());
            when(newClient.getUsername()).thenReturn("testUsername" + i);
            when(newClient.getPassword()).thenReturn("testPassword" + i);
            clients.add(newClient);
        }
    }
    @AfterAll
    public static void tearDown() {
        clientUserManager.clearData();
    }
    @BeforeEach
    public void setUp() {
        clientUserManager.clearData();
    }
//    @AfterEach
//    public void tearDown() {
//        clientUserManager.clearData();
//    }

    @Test
    public void testAdd() {
        clientUserManager.add(client);
        assertTrue(clientUserManager.get("abc").isPresent());
    }

    @Test
    public void testAddAll() {
        clientUserManager.addAll(clients);
        for (int i = 0; i < 10; i++) {
            Optional<Client> optionalClient = clientUserManager.get("testUsername"+ i);
            assertTrue(optionalClient.isPresent());
            assertEquals(optionalClient.get().getUsername(), "testUsername" + i);
        }
    }

    @Test
    public void testAuthenticate() {
        clientUserManager.addAll(clients);
        for (int i = 0; i < 10; i++) {
            Optional<Client> optionalClient = clientUserManager.authenticate("testUsername"+ i, "testPassword" + i);
            assertTrue(optionalClient.isPresent());
            assertEquals(optionalClient.get().getUsername(), "testUsername" + i);

            Optional<Client> nullClient = clientUserManager.authenticate("testUsername" + i, "incorrectPassword");
            assertFalse(nullClient.isPresent());

            nullClient = clientUserManager.authenticate("incorrectUsername", "testPassword" + i);
            assertFalse(nullClient.isPresent());
        }
    }
}
