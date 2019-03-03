package bankmachine;

import bankmachine.FileManager.FileSearch;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.verification.After;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthenticatorTest {
    private Authenticator<Client> clientAuthenticator;
    private Client client;
    private ArrayList<Client> clients;

    @BeforeAll
    public void setClientAuthenticator() {
        FileSearch fileSearch = new FileSearch();
        fileSearch.setFileNameToSearch("FileManager");
        fileSearch.searchForDirectory(new File(System.getProperty("user.dir")));
        final String fileManagerPath = fileSearch.getResult().get(0);
        clientAuthenticator = new Authenticator<>("/testClientData.ser");
        clientAuthenticator.clearData();
        client = mock(Client.class);

        when(client.getUsername()).thenReturn("abc");
        when(client.getPassword()).thenReturn("def");

        for (int i = 0; i < 50; i++) {
            Client newClient = mock(Client.class);
            when(newClient.getUsername()).thenReturn("testUsername" + i);
            when(newClient.getUsername()).thenReturn("testPassword" + i);
            clients.add(newClient);
        }
    }
    @BeforeEach
    public void emptyData() {
        clientAuthenticator.clearData();
    }
    @AfterEach
    public void tearDown() {
        clientAuthenticator.clearData();
    }

    @Test
    public void testAdd() {
        clientAuthenticator.add(client);
        assertTrue(clientAuthenticator.get("abc").isPresent());
    }

    @Test
    public void testAddAll() {
        clientAuthenticator.addAll(clients);
        for (int i = 0; i < 50; i++) {
            Optional<Client> optionalClient = clientAuthenticator.get("testUsername"+ i);
            assertTrue(optionalClient.isPresent());
            assertEquals(optionalClient.get().getUsername(), "testUsername" + i);
        }
    }

    @Test
    public void testAuthenticate() {
        clientAuthenticator.addAll(clients);
        for (int i = 0; i < 50; i++) {
            Optional<Client> optionalClient = clientAuthenticator.authenticate("testUsername"+ i, "testPassword" + i);
            assertTrue(optionalClient.isPresent());
            assertEquals(optionalClient.get().getUsername(), "testUsername" + i);

            Optional<Client> nullClient = clientAuthenticator.authenticate("testUsername" + i, "incorrectPassword");
            assertFalse(nullClient.isPresent());

            nullClient = clientAuthenticator.authenticate("incorrectUsername", "testPassword" + i);
            assertFalse(nullClient.isPresent());
        }
    }
}
