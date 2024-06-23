package fr.softsf.sudofx2024.service;

import java.util.Optional;

import fr.softsf.sudofx2024.repository.SoftwareRepository;

import fr.softsf.sudofx2024.model.Software;
import jakarta.persistence.NoResultException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SoftwareService {

    @Autowired
    private SoftwareRepository softwareRepository;

    public Optional<Software> getSoftware() {
        try {
            return Optional.of(softwareRepository.findAll().get(0));
        } catch (NoResultException e) {
            log.error(String.format("██ No software found : %s", e.getMessage()), e);
            return Optional.empty();
        } catch (Exception e) {
            log.error(String.format("██ Exception retrieving software : %s", e.getMessage()), e);
            return Optional.empty();
        }
    }

    public Optional<Software> updateSoftware(Software software) {
        try {
            return Optional.of(softwareRepository.save(software));
        } catch (Exception e) {
            log.error(String.format("██ Error updating software : %s", e.getMessage()), e);
            return Optional.empty();
        }
    }
}
