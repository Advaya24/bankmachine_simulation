package bankmachine;

import bankmachine.FileManager.FileSearcher;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserManagerTest {
    private static UserManager<Client> clientManager;
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
        clientManager = new UserManager<>(fileName);
        clientManager.clearData();
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
        clientManager.clearData();
    }
    @BeforeEach
    public void setUp() {
        clientManager.clearData();
    }
//    @AfterEach
//    public void tearDown() {
//        clientManager.clearData();
//    }

    @Test
    public void testAdd() {
        clientManager.add(client);
        assertTrue(clientManager.get("abc").isPresent());
        assertTrue(clientManager.get(client.getID()).isPresent());
    }

    @Test
    public void testAddAll() {
        clientManager.addAll(clients);
        for (int i = 0; i < 10; i++) {
            Optional<Client> optionalClient = clientManager.get("testUsername"+ i);
            assertTrue(optionalClient.isPresent());
            assertTrue(clientManager.get(clients.get(i).getID()).isPresent());
            assertEquals(optionalClient.get().getUsername(), "testUsername" + i);
        }
    }

    @Test
    public void testAuthenticate() {
        clientManager.addAll(clients);
        for (int i = 0; i < 10; i++) {
            Optional<Client> optionalClient = clientManager.authenticate("testUsername"+ i, "testPassword" + i);
            assertTrue(optionalClient.isPresent());
            assertEquals(optionalClient.get().getUsername(), "testUsername" + i);

            Optional<Client> nullClient = clientManager.authenticate("testUsername" + i, "incorrectPassword");
            assertFalse(nullClient.isPresent());

            nullClient = clientManager.authenticate("incorrectUsername", "testPassword" + i);
            assertFalse(nullClient.isPresent());
        }
    }
}
