package fr.softsf.sudofx2024.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "game")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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

    @Builder.Default
    private boolean isselected = false;

    @NotNull
    private LocalDateTime createdat;

    @NotNull
    private LocalDateTime updatedat;
}
