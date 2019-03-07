package bankmachine;

import bankmachine.account.Account;
import bankmachine.account.SavingsAccount;
import org.junit.jupiter.api.*;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserManagerTest {
    private static UserManager manager = new UserManager("./testFiles");


    @Test
    public void testAuthenticate() {
        BankMachineUser marisa = mock(BankMachineUser.class);
        when(marisa.getUsername()).thenReturn("mkirisame");
        when(marisa.getPassword()).thenReturn("da ze!");
        manager.addInstance(marisa);
        assertEquals(null, manager.authenticate("Sanae", "bleh"));
        assertEquals(null, manager.authenticate("mkirisame", "sneaky sneak"));
        assertNotEquals(null, manager.authenticate("mkirisame", "da ze!"));
        assertEquals(marisa, manager.authenticate("mkirisame", "da ze!"));
    }

    @Test
    public void testReadWrite() {
        Client client = new Client(0,
                "Hakurei Reimu",
                "hreimu@gensok.yo",
                "1-800-669-6524",
                "hreimu",
                "SanaeSmellsLikeOldSocks"
        );

        List<Client> clients = new ArrayList<>();
        clients.add(client);
        new SavingsAccount(0, 120, client, LocalDateTime.now());
        new SavingsAccount(1, 100, client, LocalDateTime.now());
        new SavingsAccount(2, 50, client, LocalDateTime.now());
        manager.saveFile("/testClients.ser", clients);
        clients = manager.loadFile("/testClients.ser");
        assertEquals(1, clients.size());

        List<Account> accounts = clients.get(0).getClientsAccounts();
        assertEquals(3, accounts.size());
        assertEquals(120, accounts.get(0).getBalance());
    }
}
