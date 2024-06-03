package fr.softsf.sudofx2024.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "software")
public class SoftwareModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID softwareuuid;

    @Size(max = 128)
    private String currentversion;

    @Size(max = 128)
    private String lastversion;

    @NotNull
    private LocalDateTime createdat;

    @NotNull
    private LocalDateTime updatedat;

    public SoftwareModel() {
        // Constructeur par défaut
    }

    public SoftwareModel(UUID softwareuuid, String currentversion, String lastversion, LocalDateTime createdat, LocalDateTime updatedat) {
        this.softwareuuid = softwareuuid;
        this.currentversion = currentversion;
        this.lastversion = lastversion;
        this.createdat = createdat;
        this.updatedat = updatedat;
    }

    // Getters et Setters
    public UUID getSoftwareuuid() {
        return softwareuuid;
    }

    public void setSoftwareuuid(UUID softwareuuid) {
        this.softwareuuid = softwareuuid;
    }

    public String getCurrentversion() {
        return currentversion;
    }

    public void setCurrentversion(String currentversion) {
        this.currentversion = currentversion;
    }

    public String getLastversion() {
        return lastversion;
    }

    public void setLastversion(String lastversion) {
        this.lastversion = lastversion;
    }

    public LocalDateTime getCreatedat() {
        return createdat;
    }

    public void setCreatedat(LocalDateTime createdat) {
        this.createdat = createdat;
    }

    public LocalDateTime getUpdatedat() {
        return updatedat;
    }

    public void setUpdatedat(LocalDateTime updatedat) {
        this.updatedat = updatedat;
    }

    // Méthode Builder
    public static SoftwareModelBuilder builder() {
        return new SoftwareModelBuilder();
    }

    public static class SoftwareModelBuilder {
        private UUID softwareuuid;
        private String currentversion;
        private String lastversion;
        private LocalDateTime createdat;
        private LocalDateTime updatedat;

        private SoftwareModelBuilder() {
        }

        public SoftwareModelBuilder softwareuuid(UUID softwareuuid) {
            this.softwareuuid = softwareuuid;
            return this;
        }

        public SoftwareModelBuilder currentversion(String currentversion) {
            this.currentversion = currentversion;
            return this;
        }

        public SoftwareModelBuilder lastversion(String lastversion) {
            this.lastversion = lastversion;
            return this;
        }

        public SoftwareModelBuilder createdat(LocalDateTime createdat) {
            this.createdat = createdat;
            return this;
        }

        public SoftwareModelBuilder updatedat(LocalDateTime updatedat) {
            this.updatedat = updatedat;
            return this;
        }

        public SoftwareModel build() {
            return new SoftwareModel(softwareuuid, currentversion, lastversion, createdat, updatedat);
        }
    }

    @Override
    public String toString() {
        return "SoftwareModel{" +
                "softwareuuid=" + softwareuuid +
                ", currentversion='" + currentversion + '\'' +
                ", lastversion='" + lastversion + '\'' +
                ", createdat=" + createdat +
                ", updatedat=" + updatedat +
                '}';
    }

}


