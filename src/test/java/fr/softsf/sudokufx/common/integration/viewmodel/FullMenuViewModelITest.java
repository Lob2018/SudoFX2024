package fr.softsf.sudokufx.common.integration.viewmodel;

import fr.softsf.sudokufx.SudoMain;
import fr.softsf.sudokufx.configuration.database.DataSourceConfigTest;
import fr.softsf.sudokufx.dto.SoftwareDto;
import fr.softsf.sudokufx.service.SoftwareService;
import fr.softsf.sudokufx.viewmodel.FullMenuViewModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {SudoMain.class})
@ActiveProfiles("test")
@Import(DataSourceConfigTest.class)
class FullMenuViewModelITest {

    @Autowired
    private SoftwareService softwareService;

    @Autowired
    private FullMenuViewModel fullMenuViewModel;

    @Test
    void testSoftwareFound() {
        fullMenuViewModel.test();
        Optional<SoftwareDto> software = softwareService.getSoftware();
        assertTrue(software.isPresent(), "The software should be found.");
        assertEquals("1.0.0", software.get().currentversion(), "The current version should be '1.0.0'");
        assertEquals("1.0.0", software.get().lastversion(), "The latest version should be '1.0.0'");
        assertTrue(fullMenuViewModel.welcomeProperty().get().contains("Version : 1.0.0"), "The message should contain the version.");
    }
}
