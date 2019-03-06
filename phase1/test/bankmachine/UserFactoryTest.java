package bankmachine;

import org.junit.jupiter.api.*;

import java.util.HashMap;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserFactoryTest {
    private static UserFactory authenticator;


    @Test
    public void testAuthenticate() {
        BankMachineUser marisa = mock(BankMachineUser.class);
        when(marisa.getUsername()).thenReturn("mkirisame");
        when(marisa.getPassword()).thenReturn("da ze!");
        authenticator = new UserFactory();
        authenticator.addInstance(marisa);
        assertEquals(null, authenticator.authenticate("Sanae", "bleh"));
        assertEquals(null, authenticator.authenticate("mkirisame", "sneaky sneak"));
        assertNotEquals(null, authenticator.authenticate("mkirisame", "da ze!"));
        assertEquals(marisa, authenticator.authenticate("mkirisame", "da ze!"));
    }
}
