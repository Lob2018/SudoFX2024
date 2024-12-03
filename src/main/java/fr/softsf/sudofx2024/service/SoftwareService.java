package fr.softsf.sudofx2024.service;

import java.util.Optional;

import fr.softsf.sudofx2024.dto.SoftwareDto;
import fr.softsf.sudofx2024.interfaces.mapper.ISoftwareMapper;
import fr.softsf.sudofx2024.repository.SoftwareRepository;

import fr.softsf.sudofx2024.model.Software;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class SoftwareService {

    @Autowired
    private SoftwareRepository softwareRepository;

    private final ISoftwareMapper iSoftwareMapper = ISoftwareMapper.INSTANCE;

    public Optional<SoftwareDto> getSoftware() {
        try {
            return softwareRepository.findFirstSoftware()
                    .map(iSoftwareMapper::mapSoftwareToDto)
                    .or(() -> {
                        log.error("██ No software found.");
                        return Optional.empty();
                    });
        } catch (Exception e) {
            log.error("██ Exception retrieving software : {}", e.getMessage(), e);
            return Optional.empty();
        }
    }

    @Transactional
    public Optional<SoftwareDto> updateSoftware(SoftwareDto softwareDto) {
        try {
            Software software = iSoftwareMapper.mapSoftwareDtoToSoftware(softwareDto);
            Software updatedSoftware = softwareRepository.save(software);
            return Optional.ofNullable(iSoftwareMapper.mapSoftwareToDto(updatedSoftware));
        } catch (Exception e) {
            log.error("██ Error updating software : {}", e.getMessage(), e);
            return Optional.empty();
        }
    }
}
