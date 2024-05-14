package fr.softsf.sudofx2024.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "player")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlayerModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID playeruuid;

    @ManyToOne
    @JoinColumn(name = "playerlanguageplayerlanguageuuid")
    private PlayerLanguageModel playerlanguageuuid;

    @ManyToOne
    @JoinColumn(name = "backgroundbackgrounduuid")
    private BackgroundModel backgrounduuid;

    @ManyToOne
    @JoinColumn(name = "menumenuuuid")
    private MenuModel menuuuid;

    @NotNull
    @Size(max = 256)
    @Column(nullable = false, unique = true)
    private String name;

    @Builder.Default
    private boolean isselected = false;

    @NotNull
    private LocalDateTime createdat;

    @NotNull
    private LocalDateTime updatedat;
}
