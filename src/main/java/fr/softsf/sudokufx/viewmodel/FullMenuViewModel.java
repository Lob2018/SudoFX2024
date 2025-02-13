package fr.softsf.sudokufx.viewmodel;

import fr.softsf.sudokufx.dto.SoftwareDto;
import fr.softsf.sudokufx.interfaces.IGridMaster;
import fr.softsf.sudokufx.service.SoftwareService;
import fr.softsf.sudokufx.service.VersionService;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

/**
 * FullMenuViewModel with business logic (not final)
 */
@Component
public class FullMenuViewModel {
    private final SoftwareService softwareService;
    private final IGridMaster iGridMaster;
    private final VersionService versionService;

    private StringProperty welcome;
    private final BooleanProperty version = new SimpleBooleanProperty(false);

    @Autowired
    public FullMenuViewModel(SoftwareService softwareService, IGridMaster iGridMaster, VersionService versionService) {
        this.softwareService = softwareService;
        this.iGridMaster = iGridMaster;
        this.versionService = versionService;
        version.bind(versionService.versionUpToDateProperty());
    }

    public BooleanProperty versionUpToDateProperty() {
        return version;
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

        versionService.checkLatestVersion();

        SoftwareDto softwareDto = new SoftwareDto(
                softwareToUpdate.softwareid(),
                "1.0.0",
                "1.0.0",
                softwareToUpdate.createdat(),
                LocalDateTime.now()
        );

        Optional<SoftwareDto> updatedSoftwareOptional = softwareService.updateSoftware(softwareDto);
        if (updatedSoftwareOptional.isPresent()) {
            int niveau = 3;
            int[][] grilles = iGridMaster.creerLesGrilles(niveau);
            String grilleResolue = Arrays.toString(grilles[0]);
            String grilleAResoudre = Arrays.toString(grilles[1]);
            StringBuilder formattedGrilleResolue = new StringBuilder();
            StringBuilder formattedGrilleAResoudre = new StringBuilder();
            for (int i = 0; i < grilleResolue.length(); i++) {
                formattedGrilleResolue.append(grilleResolue.charAt(i));
                formattedGrilleAResoudre.append(grilleAResoudre.charAt(i));
                // Ajouter un saut de ligne tous les 9 caractères
                if ((i + 1) % 27 == 0 && i + 1 < grilleResolue.length()) {
                    formattedGrilleResolue.append("\n");
                    formattedGrilleAResoudre.append("\n");
                }
            }
            System.out.println("###### UPDATED ####### " + updatedSoftwareOptional.get());
            setWelcome("Version : " + updatedSoftwareOptional.get().currentversion() +
                    "\nMise à jour : " + updatedSoftwareOptional.get().updatedat() +
                    "\n" + formattedGrilleResolue +
                    "\n" + formattedGrilleAResoudre +
                    "\n\n Niveau " + niveau + "/3 avec " + grilles[2][0] + "% de difficuté");


//

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
