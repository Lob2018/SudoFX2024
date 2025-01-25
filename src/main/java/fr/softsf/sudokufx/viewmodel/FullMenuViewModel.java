package fr.softsf.sudokufx.viewmodel;

import fr.softsf.sudokufx.dto.SoftwareDto;
import fr.softsf.sudokufx.service.SoftwareService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * FullMenuViewModel with business logic (not final)
 */
@Component
public class FullMenuViewModel {
    private final SoftwareService softwareService;
    private StringProperty welcome;

    @Autowired
    public FullMenuViewModel(SoftwareService softwareService) {
        this.softwareService = softwareService;
    }

    public StringProperty welcomeProperty() {
        if (welcome == null) {
            welcome = new SimpleStringProperty(this, "");
        }
        return welcome;
    }

    private void setWelcome(String output) {
        welcomeProperty().set(output);
    }

    public void test() {
        Optional<SoftwareDto> currentSoftware = softwareService.getSoftware();
        if (currentSoftware.isPresent()) {
            System.out.println("###### GET ####### Software found: " + currentSoftware.get());
            updateSoftware(currentSoftware.get());
        } else {
            System.out.println("###### GET ####### No software found, creating a new one.");
            createSoftware();
        }
    }

    private void updateSoftware(SoftwareDto softwareToUpdate) {
        SoftwareDto softwareDto = new SoftwareDto(
                softwareToUpdate.softwareid(),
                softwareToUpdate.lastversion(),
                "1.0.1",
                softwareToUpdate.createdat(),
                LocalDateTime.now()
        );

        Optional<SoftwareDto> updatedSoftwareOptional = softwareService.updateSoftware(softwareDto);
        if (updatedSoftwareOptional.isPresent()) {
            System.out.println("###### UPDATED ####### " + updatedSoftwareOptional.get());
            setWelcome("Version : " + updatedSoftwareOptional.get().currentversion() +
                    "\nMise à jour : " + updatedSoftwareOptional.get().updatedat());
        } else {
            System.out.println("Erreur lors de la mise à jour du logiciel.");
        }
    }

    private void createSoftware() {
        SoftwareDto softwareDto = new SoftwareDto(
                null,
                "1.0.0",
                "1.0.0",
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        Optional<SoftwareDto> createdSoftware = softwareService.updateSoftware(softwareDto);
        System.out.println("###### CREATED ####### " + createdSoftware);
    }
}
