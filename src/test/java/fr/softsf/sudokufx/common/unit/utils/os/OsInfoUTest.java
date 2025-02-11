package fr.softsf.sudokufx.common.unit.utils.os;

import org.junit.jupiter.api.Test;

import static fr.softsf.sudokufx.utils.MyEnums.OsName.OS_NAME;
import static fr.softsf.sudokufx.utils.MyEnums.Paths.USER_HOME;
import static org.junit.jupiter.api.Assertions.assertEquals;

class OsInfoUTest {

    @Test
    void testOsInfoTest_UserHomePath_success() {
        assertEquals(System.getProperty("user.home").replace("\\", "/"), USER_HOME.getPath());
    }

    @Test
    void testOsInfoTest_LowerCaseOsName_success() {
        String expectedLowercaseOsName = System.getProperty("os.name").toLowerCase();
        assertEquals(expectedLowercaseOsName, OS_NAME.getOs());
    }

}
