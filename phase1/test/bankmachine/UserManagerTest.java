package bankmachine;

import bankmachine.FileManager.FileSearcher;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserManagerTest {
    private static UserManager userManager;


    @Test
    public void testAuthenticate() {
        HashMap<String, BankMachineUser> users = new HashMap<>();
        BankMachineUser marisa = mock(BankMachineUser.class);
        when(marisa.getUsername()).thenReturn("mkirisame");
        when(marisa.getPassword()).thenReturn("da ze!");
        users.put("mkirisame", marisa);
        userManager = new UserManager(users);
        assertEquals(null, userManager.authenticate("Sanae", "bleh"));
        assertEquals(null, userManager.authenticate("mkirisame", "sneaky sneak"));
        assertNotEquals(null, userManager.authenticate("mkirisame", "da ze!"));
        assertEquals(marisa, userManager.authenticate("mkirisame", "da ze!"));
    }
}
