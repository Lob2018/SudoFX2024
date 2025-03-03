package fr.softsf.sudokufx.configuration.database;

import fr.softsf.sudokufx.SudoMain;
import fr.softsf.sudokufx.enums.MyRegex;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {SudoMain.class})
class GenerateSecretUTest {
    private final GenerateSecret generateSecret;

    @Autowired
    public GenerateSecretUTest(GenerateSecret generateSecret) {
        this.generateSecret = generateSecret;
    }

    @Test
    void givenPassayGenerator_whenGeneratePassword_thenPasswordMatchesRegex() {
        String password = generateSecret.generatePassaySecret();
        String regex = MyRegex.INSTANCE.getSecretPattern().pattern();
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
