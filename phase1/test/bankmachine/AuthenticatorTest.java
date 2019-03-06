package bankmachine;

import org.junit.jupiter.api.*;

import java.util.HashMap;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthenticatorTest {
    private static Authenticator authenticator;


    @Test
    public void testAuthenticate() {
        HashMap<String, BankMachineUser> users = new HashMap<>();
        BankMachineUser marisa = mock(BankMachineUser.class);
        when(marisa.getUsername()).thenReturn("mkirisame");
        when(marisa.getPassword()).thenReturn("da ze!");
        users.put("mkirisame", marisa);
        authenticator = new Authenticator(users);
        assertEquals(null, authenticator.authenticate("Sanae", "bleh"));
        assertEquals(null, authenticator.authenticate("mkirisame", "sneaky sneak"));
        assertNotEquals(null, authenticator.authenticate("mkirisame", "da ze!"));
        assertEquals(marisa, authenticator.authenticate("mkirisame", "da ze!"));
    }
}
