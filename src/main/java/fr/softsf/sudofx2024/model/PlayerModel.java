package fr.softsf.sudofx2024.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "player")
public class PlayerModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID playeruuid;

    @ManyToOne
    @JoinColumn(name = "languagelanguageuuid")
    private PlayerLanguageModel languageuuid;

    @ManyToOne
    @JoinColumn(name = "backgroundbackgrounduuid")
    private BackgroundModel backgrounduuid;

    @NotNull
    @Size(max = 256)
    @Column(nullable = false, unique = true)
    private String name;

    private final boolean isselected = false;

    @NotNull
    private LocalDateTime createdat;

    @NotNull
    private LocalDateTime updatedat;

    public PlayerModel() {
        // Constructeur par défaut
    }

    public PlayerModel(UUID playeruuid, PlayerLanguageModel languageuuid, BackgroundModel backgrounduuid, String name, LocalDateTime createdat, LocalDateTime updatedat) {
        this.playeruuid = playeruuid;
        this.languageuuid = languageuuid;
        this.backgrounduuid = backgrounduuid;
        this.name = name;
        this.createdat = createdat;
        this.updatedat = updatedat;
    }

    // Getters et Setters
    public UUID getPlayeruuid() {
        return playeruuid;
    }

    public void setPlayeruuid(UUID playeruuid) {
        this.playeruuid = playeruuid;
    }

    public PlayerLanguageModel getLanguageuuid() {
        return languageuuid;
    }

    public void setLanguageuuid(PlayerLanguageModel languageuuid) {
        this.languageuuid = languageuuid;
    }

    public BackgroundModel getBackgrounduuid() {
        return backgrounduuid;
    }

    public void setBackgrounduuid(BackgroundModel backgrounduuid) {
        this.backgrounduuid = backgrounduuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isselected;
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
    public static PlayerModelBuilder builder() {
        return new PlayerModelBuilder();
    }

    public static class PlayerModelBuilder {
        private UUID playeruuid;
        private PlayerLanguageModel languageuuid;
        private BackgroundModel backgrounduuid;
        private String name;
        private LocalDateTime createdat;
        private LocalDateTime updatedat;

        private PlayerModelBuilder() {
        }

        public PlayerModelBuilder playeruuid(UUID playeruuid) {
            this.playeruuid = playeruuid;
            return this;
        }

        public PlayerModelBuilder languageuuid(PlayerLanguageModel languageuuid) {
            this.languageuuid = languageuuid;
            return this;
        }

        public PlayerModelBuilder backgrounduuid(BackgroundModel backgrounduuid) {
            this.backgrounduuid = backgrounduuid;
            return this;
        }

        public PlayerModelBuilder name(String name) {
            this.name = name;
            return this;
        }

        public PlayerModelBuilder createdat(LocalDateTime createdat) {
            this.createdat = createdat;
            return this;
        }

        public PlayerModelBuilder updatedat(LocalDateTime updatedat) {
            this.updatedat = updatedat;
            return this;
        }

        public PlayerModel build() {
            return new PlayerModel(playeruuid, languageuuid, backgrounduuid, name, createdat, updatedat);
        }
    }

    @Override
    public String toString() {
        return "PlayerModel{" +
                "playeruuid=" + playeruuid +
                ", languageuuid=" + languageuuid +
                ", backgrounduuid=" + backgrounduuid +
                ", name='" + name + '\'' +
                ", isselected=" + isselected +
                ", createdat=" + createdat +
                ", updatedat=" + updatedat +
                '}';
    }

}
