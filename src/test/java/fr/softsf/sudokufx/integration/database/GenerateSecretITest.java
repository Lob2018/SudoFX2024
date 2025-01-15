package fr.softsf.sudokufx.integration.database;

import fr.softsf.sudokufx.utils.MyRegex;
import fr.softsf.sudokufx.utils.database.keystore.GenerateSecret;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GenerateSecretITest {

    @Test
    void whenPasswordGeneratedUsingPassay_thenSuccessful() {
        String password = GenerateSecret.generatePassaySecret();
        String regex = MyRegex.getSECRET();
        assertTrue(password.matches(regex));
        assertFalse("".matches(regex));
        assertFalse("LLLLLLLLLLLLLLLLLLLLLLLL".matches(regex));
        assertFalse("llllllllllllllllllllllll".matches(regex));
        assertFalse("@@@@@@@@@@@@@@@@@@@@@@@@".matches(regex));
        assertFalse("111111111111111111111111".matches(regex));
        assertFalse("                        ".matches(regex));
        assertFalse("LLLLLLLLLLLLLLLLLLLLLLLL".matches(regex));
        assertFalse("llllllllllllllllllllllll".matches(regex));
        assertFalse("@@@@@@@@@@@@@@@@@@@@@@@@".matches(regex));
        assertFalse("111111111111111111111111".matches(regex));
        assertFalse("9uCQD1x$^UeWfn#OAb!Y1YFH1".matches(regex));
        assertFalse("9uCQD1x$^UeWfn#OAb!Y1YF".matches(regex));
    }
}
