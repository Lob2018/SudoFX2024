package fr.softsf.sudokufx.integration.service;

import fr.softsf.sudokufx.dto.SoftwareDto;
import fr.softsf.sudokufx.model.Software;
import fr.softsf.sudokufx.repository.SoftwareRepository;
import fr.softsf.sudokufx.service.SoftwareService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SoftwareServiceITest {

    private final String currentVersion = "1.0.0";
    @Autowired
    private SoftwareService softwareService;
    @MockitoBean
    private SoftwareRepository softwareRepository;

    @BeforeEach
    public void setUp() {
        Software software = Software.builder()
                .softwareid(1L)
                .currentversion(currentVersion)
                .lastversion("1.0.1")
                .createdat(LocalDateTime.now())
                .updatedat(LocalDateTime.now()).build();
        assert software != null;
        Mockito.when(softwareRepository.findFirstSoftware())
                .thenReturn(Optional.of(software));
        Mockito.when(softwareRepository.save(Mockito.any(Software.class))).thenReturn(software);
    }

    @Test
    public void testGetSoftware_success() {
        Optional<SoftwareDto> softwareDto = softwareService.getSoftware();
        assertTrue(softwareDto.isPresent());
        assertThat(softwareDto.get().currentversion())
                .isEqualTo(currentVersion);
    }

    @Test
    public void testUpdateSoftware_success() {
        Optional<SoftwareDto> softwareDto = softwareService.getSoftware();
        assertTrue(softwareDto.isPresent());
        Optional<SoftwareDto> softwareDtoUpdated = softwareService.updateSoftware(softwareDto.get());
        assertTrue(softwareDtoUpdated.isPresent());
        assertThat(softwareDtoUpdated.get().currentversion())
                .isEqualTo(currentVersion);
    }
}
