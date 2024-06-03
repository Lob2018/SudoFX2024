package fr.softsf.sudofx2024.unit.utils.os;

import fr.softsf.sudofx2024.utils.os.OsInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OsInfoTest {

    @Test
    public void testOsInfoTest_UserHomePath_success() {
        assertEquals(System.getProperty("user.home"), OsInfo.USER_HOME);
    }

    @Test
    public void testOsInfoTest_LowerCaseOsName_success() {
        String expectedLowercaseOsName = System.getProperty("os.name").toLowerCase();
        assertEquals(expectedLowercaseOsName, OsInfo.OS_NAME);
    }

}
