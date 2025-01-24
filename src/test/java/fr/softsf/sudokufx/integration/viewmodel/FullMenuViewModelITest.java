package fr.softsf.sudokufx.integration.viewmodel;

import static org.junit.jupiter.api.Assertions.*;

import fr.softsf.sudokufx.dto.SoftwareDto;
import fr.softsf.sudokufx.service.SoftwareService;
import fr.softsf.sudokufx.viewmodel.FullMenuViewModel;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import uk.org.webcompere.systemstubs.jupiter.SystemStub;
import uk.org.webcompere.systemstubs.jupiter.SystemStubsExtension;
import uk.org.webcompere.systemstubs.properties.SystemProperties;

import java.util.Optional;

@Slf4j
@SpringBootTest
@TestPropertySource("classpath:fr/softsf/sudokufx/application-test.properties")
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(SystemStubsExtension.class)
class FullMenuViewModelITest {

    @SystemStub
    private SystemProperties systemProperties;

    @Autowired
    private SoftwareService softwareService;

    @Autowired
    private FullMenuViewModel fullMenuViewModel;

    @BeforeEach
    void eachSetup() {
        log.info("↓↓↓↓↓↓↓↓↓↓ START INTEGRATION TEST : FullMenuViewModelITest ↓↓↓↓↓↓↓↓↓↓");
        systemProperties.set("app.name", "SudokuFX");
        systemProperties.set("app.version", "1.0.0");
    }

    @AfterEach
    void eachEnded() {
        log.info("↑↑↑↑↑↑↑↑↑↑  END  INTEGRATION TEST : FullMenuViewModelITest ↑↑↑↑↑↑↑↑↑↑");
    }

    @Test
    @Order(0)
    void testSoftwareFound() {
        fullMenuViewModel.test();
        Optional<SoftwareDto> software = softwareService.getSoftware();
        assertTrue(software.isPresent(), "The software should be found.");
        assertEquals("v1.0.0", software.get().currentversion(), "The current version should be 'v1.0.0'");
        assertEquals("v1.0.0", software.get().lastversion(), "The latest version should be 'v1.0.0'");
        assertTrue(fullMenuViewModel.welcomeProperty().get().contains("Version : v1.0.0"), "The message should contain the version.");
    }
}
