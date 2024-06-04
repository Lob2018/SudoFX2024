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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID gameuuid;

    @ManyToOne
    @Cascade(CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "gridgriduuid")
    private Grid griduuid;

    @ManyToOne
    @Cascade(CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "playerplayeruuid")
    private Player playeruuid;

    public Player getPlayer() {
        return playeruuid;
    }

    public void setPlayer(Player player) {
        this.playeruuid = player;
    }

    @ManyToOne
    @Cascade(CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "levelleveluuid")
    private GameLevel leveluuid;

    @Builder.Default
    private boolean isselected = false;

    @NotNull
    private LocalDateTime createdat;

    @NotNull
    private LocalDateTime updatedat;
}
