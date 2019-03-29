package bankmachine;

import bankmachine.account.Account;
import bankmachine.account.SavingsAccount;
import bankmachine.fileManager.FileSearcher;
import bankmachine.users.BankMachineUser;
import bankmachine.users.Client;
import bankmachine.users.UserManager;
import org.junit.jupiter.api.*;


import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserManagerTest {
    private UserManager manager;

    @SuppressWarnings("Duplicates")
    @BeforeEach
    public void instantiateManager(){
        FileSearcher fileSearcher = new FileSearcher();
        fileSearcher.setFileNameToSearch("test");
        fileSearcher.searchForDirectoryIn(new File(System.getProperty("user.dir")));
        final String TEST_PATH = fileSearcher.getResult().get(0);
        fileSearcher.clearResults();
        fileSearcher.setFileNameToSearch("data");
        fileSearcher.searchForDirectoryIn(new File(TEST_PATH));
        manager = new UserManager(fileSearcher.getResult().get(0)+"/testClients.ser");
    }

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

        List<BankMachineUser> clients = new ArrayList<>();
        clients.add(client);
        new SavingsAccount(0, 120, client, LocalDateTime.now());
        new SavingsAccount(1, 100, client, LocalDateTime.now());
        new SavingsAccount(2, 50, client, LocalDateTime.now());
        manager.extend(clients);
        manager.saveData();
        clients = new ArrayList<>();
        for(BankMachineUser b:manager.loadFile()){
            if (b instanceof Client){
                clients.add(b);
            }
        }
        // One automatic bankmanager, and reimu
        assertEquals(2, clients.size());

        List<Account> accounts = ((Client) clients.get(1)).getClientsAccounts();
        assertEquals(3, accounts.size());
        assertEquals(120, accounts.get(0).getBalance());
    }
}
