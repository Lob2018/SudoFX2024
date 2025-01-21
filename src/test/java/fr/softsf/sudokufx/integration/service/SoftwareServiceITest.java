package fr.softsf.sudokufx.integration.service;

import fr.softsf.sudokufx.dto.SoftwareDto;
import fr.softsf.sudokufx.interfaces.mapper.ISoftwareMapper;
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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SoftwareServiceITest {

    private static final ISoftwareMapper iSoftwareMapper = ISoftwareMapper.INSTANCE;
    private final String currentVersion = "1.0.0";
    private final String lastVersion = "1.0.1";
    private final SoftwareService softwareService;
    @MockitoBean
    private SoftwareRepository softwareRepository;

    private Software software;

    @Autowired
    SoftwareServiceITest(SoftwareService softwareService) {
        this.softwareService = softwareService;
    }

    @BeforeEach
    void setUp() {
        software = Software.builder()
                .softwareid(1L)
                .currentversion(currentVersion)
                .lastversion(lastVersion)
                .createdat(LocalDateTime.now())
                .updatedat(LocalDateTime.now()).build();
        assert software != null;
    }

    @Test
    void testGetSoftware_success() {
        Mockito.when(softwareRepository.findFirstSoftware())
                .thenReturn(Optional.of(software));
        Optional<SoftwareDto> softwareDto = softwareService.getSoftware();
        assertTrue(softwareDto.isPresent());
        assertThat(softwareDto.get().currentversion())
                .isEqualTo(currentVersion);
    }

    @Test
    void testGetSoftware_NoSoftwareFound() {
        Mockito.when(softwareRepository.findFirstSoftware())
                .thenReturn(Optional.empty());
        Optional<SoftwareDto> softwareDto = softwareService.getSoftware();
        assertFalse(softwareDto.isPresent());
    }

    @Test
    public void testGetSoftware_ExceptionThrown() {
        Mockito.when(softwareRepository.findFirstSoftware())
                .thenThrow(new RuntimeException("Database error"));
        Optional<SoftwareDto> softwareDto = softwareService.getSoftware();
        assertFalse(softwareDto.isPresent());
    }

    @Test
    void testUpdateSoftware_success() {
        Mockito.when(softwareRepository.save(Mockito.any(Software.class)))
                .thenReturn(software);
        software.setCurrentversion(lastVersion);
        software.setLastversion("1.0.2");
        software.setUpdatedat(LocalDateTime.now());
        SoftwareDto softwareDto = iSoftwareMapper.mapSoftwareToDto(software);
        Optional<SoftwareDto> softwareDtoUpdated = softwareService.updateSoftware(softwareDto);
        assertTrue(softwareDtoUpdated.isPresent());
        assertThat(softwareDtoUpdated.get().currentversion())
                .isEqualTo(lastVersion);
    }

    @Test
    public void testUpdateSoftware_ExceptionThrown() {
        Mockito.when(softwareRepository.save(Mockito.any(Software.class)))
                .thenThrow(new RuntimeException("Database error"));
        software.setCurrentversion(lastVersion);
        software.setLastversion("1.0.2");
        software.setUpdatedat(LocalDateTime.now());
        SoftwareDto softwareDto = iSoftwareMapper.mapSoftwareToDto(software);
        Optional<SoftwareDto> softwareDtoUpdated = softwareService.updateSoftware(softwareDto);
        assertFalse(softwareDtoUpdated.isPresent());
    }
}
