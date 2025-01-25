package fr.softsf.sudokufx.integration.viewmodel;

import fr.softsf.sudokufx.dto.SoftwareDto;
import fr.softsf.sudokufx.service.SoftwareService;
import fr.softsf.sudokufx.viewmodel.FullMenuViewModel;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import uk.org.webcompere.systemstubs.jupiter.SystemStub;
import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;
import uk.org.webcompere.systemstubs.properties.SystemProperties;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@SpringBootTest
@TestPropertySource("classpath:fr/softsf/sudokufx/application-test.properties")
@ActiveProfiles("test")
@ExtendWith(SystemStubsExtension.class)
class FullMenuViewModelITest {

    private static final String APP_NAME_PROPERTY = "app.name";
    private static final String APP_VERSION_PROPERTY = "app.version";

    @SystemStub
    private SystemProperties systemProperties;

    @Autowired
    private SoftwareService softwareService;

    @Autowired
    private FullMenuViewModel fullMenuViewModel;

    @BeforeEach
    void eachSetup() {
        log.info("↓↓↓↓↓↓↓↓↓↓ START INTEGRATION TEST : FullMenuViewModelITest ↓↓↓↓↓↓↓↓↓↓");
        systemProperties.set(APP_NAME_PROPERTY, "SudokuFX");
        systemProperties.set(APP_VERSION_PROPERTY, "1.0.0");
    }

    @AfterEach
    void eachEnded() {
        log.info("↑↑↑↑↑↑↑↑↑↑  END  INTEGRATION TEST : FullMenuViewModelITest ↑↑↑↑↑↑↑↑↑↑");
    }

    @Test
    void testSoftwareFound() {
        fullMenuViewModel.test();
        Optional<SoftwareDto> software = softwareService.getSoftware();
        assertTrue(software.isPresent(), "The software should be found.");
        assertEquals("v1.0.0", software.get().currentversion(), "The current version should be 'v1.0.0'");
        assertEquals("v1.0.0", software.get().lastversion(), "The latest version should be 'v1.0.0'");
        assertTrue(fullMenuViewModel.welcomeProperty().get().contains("Version : v1.0.0"), "The message should contain the version.");
    }
}
