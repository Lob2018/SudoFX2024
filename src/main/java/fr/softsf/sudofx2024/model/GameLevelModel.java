package fr.softsf.sudofx2024.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

@Entity
@Table(name = "gamelevel")
public class GameLevelModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID leveluuid;

    @NotNull
    @Size(max = 64)
    private String levelname;

    public GameLevelModel() {
        // Constructeur par défaut
    }

    public GameLevelModel(UUID leveluuid, String levelname) {
        this.leveluuid = leveluuid;
        this.levelname = levelname;
    }

    // Getters et Setters
    public UUID getLeveluuid() {
        return leveluuid;
    }

    public void setLeveluuid(UUID leveluuid) {
        this.leveluuid = leveluuid;
    }

    public String getLevelname() {
        return levelname;
    }

    public void setLevelname(String levelname) {
        this.levelname = levelname;
    }

    // Méthode Builder
    public static GameLevelModelBuilder builder() {
        return new GameLevelModelBuilder();
    }

    public static class GameLevelModelBuilder {
        private UUID leveluuid;
        private String levelname;

        private GameLevelModelBuilder() {
        }

        public GameLevelModelBuilder leveluuid(UUID leveluuid) {
            this.leveluuid = leveluuid;
            return this;
        }

        public GameLevelModelBuilder levelname(String levelname) {
            this.levelname = levelname;
            return this;
        }

        public GameLevelModel build() {
            return new GameLevelModel(leveluuid, levelname);
        }
    }

    @Override
    public String toString() {
        return "GameLevelModel{" +
                "leveluuid=" + leveluuid +
                ", levelname='" + levelname + '\'' +
                '}';
    }

}
