package fr.softsf.sudofx2024.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "game")
public class GameModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID gameuuid;

    @ManyToOne
    @JoinColumn(name = "gridgriduuid")
    private GridModel griduuid;

    @ManyToOne
    @JoinColumn(name = "playerplayeruuid")
    private PlayerModel playeruuid;

    @ManyToOne
    @JoinColumn(name = "levelleveluuid")
    private GameLevelModel leveluuid;

    private final boolean isselected = false;

    @NotNull
    private LocalDateTime createdat;

    @NotNull
    private LocalDateTime updatedat;

    public GameModel() {
        // Constructeur par défaut
    }

    public GameModel(UUID gameuuid, GridModel griduuid, PlayerModel playeruuid, GameLevelModel leveluuid, LocalDateTime createdat, LocalDateTime updatedat) {
        this.gameuuid = gameuuid;
        this.griduuid = griduuid;
        this.playeruuid = playeruuid;
        this.leveluuid = leveluuid;
        this.createdat = createdat;
        this.updatedat = updatedat;
    }

    // Getters et Setters
    public UUID getGameuuid() {
        return gameuuid;
    }

    public void setGameuuid(UUID gameuuid) {
        this.gameuuid = gameuuid;
    }

    public GridModel getGriduuid() {
        return griduuid;
    }

    public void setGriduuid(GridModel griduuid) {
        this.griduuid = griduuid;
    }

    public PlayerModel getPlayeruuid() {
        return playeruuid;
    }

    public void setPlayeruuid(PlayerModel playeruuid) {
        this.playeruuid = playeruuid;
    }

    public GameLevelModel getLeveluuid() {
        return leveluuid;
    }

    public void setLeveluuid(GameLevelModel leveluuid) {
        this.leveluuid = leveluuid;
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
    public static GameModelBuilder builder() {
        return new GameModelBuilder();
    }

    public static class GameModelBuilder {
        private UUID gameuuid;
        private GridModel griduuid;
        private PlayerModel playeruuid;
        private GameLevelModel leveluuid;
        private LocalDateTime createdat;
        private LocalDateTime updatedat;

        private GameModelBuilder() {
        }

        public GameModelBuilder gameuuid(UUID gameuuid) {
            this.gameuuid = gameuuid;
            return this;
        }

        public GameModelBuilder griduuid(GridModel griduuid) {
            this.griduuid = griduuid;
            return this;
        }

        public GameModelBuilder playeruuid(PlayerModel playeruuid) {
            this.playeruuid = playeruuid;
            return this;
        }

        public GameModelBuilder leveluuid(GameLevelModel leveluuid) {
            this.leveluuid = leveluuid;
            return this;
        }

        public GameModelBuilder createdat(LocalDateTime createdat) {
            this.createdat = createdat;
            return this;
        }

        public GameModelBuilder updatedat(LocalDateTime updatedat) {
            this.updatedat = updatedat;
            return this;
        }

        public GameModel build() {
            return new GameModel(gameuuid, griduuid, playeruuid, leveluuid, createdat, updatedat);
        }
    }

    @Override
    public String toString() {
        return "GameModel{" +
                "gameuuid=" + gameuuid +
                ", griduuid=" + griduuid +
                ", playeruuid=" + playeruuid +
                ", leveluuid=" + leveluuid +
                ", isselected=" + isselected +
                ", createdat=" + createdat +
                ", updatedat=" + updatedat +
                '}';
    }

}
