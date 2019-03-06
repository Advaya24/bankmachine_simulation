package bankmachine;

import bankmachine.account.Account;
import bankmachine.account.SavingsAccount;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DataLoaderTest {
    private DataLoader dataLoader = new DataLoader("./testFiles");

    @Test
    public void testReadWrite() {
        Client client = new Client(
                "Hakurei Reimu",
                "hreimu@gensok.yo",
                "1-800-669-6524",
                "hreimu",
                "SanaeSmellsLikeOldSocks"
        );
        List<Client> clients = new ArrayList<>();
        clients.add(client);
        new SavingsAccount(120, client, LocalDateTime.now());
        new SavingsAccount(100, client, LocalDateTime.now());
        new SavingsAccount(50, client, LocalDateTime.now());
        dataLoader.saveFile("/testClients.ser", clients);
        clients = dataLoader.loadFile("/testClients.ser");
        assertEquals(1, clients.size());

        List<Account> accounts = clients.get(0).getClientsAccounts();
        assertEquals(3, accounts.size());
        assertEquals(120, accounts.get(0).getBalance());
    }
}
