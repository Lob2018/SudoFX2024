package fr.softsf.sudofx2024.viewmodel;

import java.time.LocalDateTime;
import java.util.Optional;

import fr.softsf.sudofx2024.model.SoftwareModel;
import fr.softsf.sudofx2024.service.SoftwareService;
import fr.softsf.sudofx2024.utils.JVMApplicationProperties;
import fr.softsf.sudofx2024.view.FullMenuView;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * FullMenuViewModel with business logic (not final)
 */
@Component
public class FullMenuViewModel {
    @Autowired
    SoftwareService softwareService;

    StringProperty _welcome;

    public StringProperty welcomeProperty() {
        if (_welcome == null) {
            _welcome = new SimpleStringProperty(this, "");
        }
        return _welcome;
    }

    private void setWelcome(String output) {
        welcomeProperty().set(output);
    }


    public void test() {
        Optional<SoftwareModel> currentSoftware = softwareService.getSoftware();
        System.out.println("###### GET ####### " + currentSoftware);
        if (currentSoftware.isPresent()) {
            SoftwareModel softwareToUpdate = currentSoftware.get();
            softwareToUpdate.setLastversion(JVMApplicationProperties.getAppVersion());
            softwareToUpdate.setCurrentversion(JVMApplicationProperties.getAppVersion());
            softwareToUpdate.setUpdatedat(LocalDateTime.now());
            Optional<SoftwareModel> updatedSoftwareOptional = softwareService.updateSoftware(softwareToUpdate);
            if (updatedSoftwareOptional.isPresent()) {
                System.out.println("###### UPDATED ####### " + updatedSoftwareOptional.get());
                setWelcome("Version : " + updatedSoftwareOptional.get().getCurrentversion() +
                        "\nMise à jour : " + updatedSoftwareOptional.get().getUpdatedat());
            } else {
                System.out.println("Erreur lors de la mise à jour du logiciel.");
            }
        } else {
            SoftwareModel software = SoftwareModel.builder()
                    .currentversion(JVMApplicationProperties.getAppVersion())
                    .lastversion(JVMApplicationProperties.getAppVersion())
                    .createdat(LocalDateTime.now())
                    .updatedat(LocalDateTime.now())
                    .build();
            Optional<SoftwareModel> createdSoftware = softwareService.updateSoftware(software);
            System.out.println("###### CREATED ####### " + createdSoftware);
        }
    }
}
