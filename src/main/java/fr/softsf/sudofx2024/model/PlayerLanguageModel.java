package fr.softsf.sudofx2024.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

@Entity
@Table(name = "playerlanguage")
public class PlayerLanguageModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID languageuuid;

    @NotNull
    @Size(max = 2)
    private String iso;

    public PlayerLanguageModel() {
        // Constructeur par défaut
    }

    public PlayerLanguageModel(UUID languageuuid, String iso) {
        this.languageuuid = languageuuid;
        this.iso = iso;
    }

    // Getters et Setters
    public UUID getLanguageuuid() {
        return languageuuid;
    }

    public void setLanguageuuid(UUID languageuuid) {
        this.languageuuid = languageuuid;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    // Méthode Builder
    public static PlayerLanguageModelBuilder builder() {
        return new PlayerLanguageModelBuilder();
    }

    public static class PlayerLanguageModelBuilder {
        private UUID languageuuid;
        private String iso;

        private PlayerLanguageModelBuilder() {
        }

        public PlayerLanguageModelBuilder languageuuid(UUID languageuuid) {
            this.languageuuid = languageuuid;
            return this;
        }

        public PlayerLanguageModelBuilder iso(String iso) {
            this.iso = iso;
            return this;
        }

        public PlayerLanguageModel build() {
            return new PlayerLanguageModel(languageuuid, iso);
        }
    }

    @Override
    public String toString() {
        return "PlayerLanguageModel{" +
                "languageuuid=" + languageuuid +
                ", iso='" + iso + '\'' +
                '}';
    }

}
