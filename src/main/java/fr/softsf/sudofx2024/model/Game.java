package fr.softsf.sudofx2024.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "game")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gameid;

    @OneToOne
    @Cascade(CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "gridgridid")
    private Grid gridid;

    @ManyToOne
    @Cascade(CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "playerplayerid")
    private Player playerid;

    public Player getPlayer() {
        return playerid;
    }

    public void setPlayer(Player player) {
        this.playerid = player;
    }

    @ManyToOne
    @Cascade(CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "levellevelid")
    private GameLevel levelid;

    @Builder.Default
    private boolean isselected = false;

    @NotNull
    private LocalDateTime createdat;

    @NotNull
    private LocalDateTime updatedat;
}
