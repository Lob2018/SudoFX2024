package fr.softsf.sudofx2024.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.CascadeType;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "player")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playerid;

    @ManyToOne
    @Cascade(CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "playerlanguageplayerlanguageid")
    private PlayerLanguage playerlanguageid;

    @OneToOne
    @Cascade(CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "backgroundbackgroundid")
    private Background backgroundid;

    @ManyToOne
    @Cascade(CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "menumenuid")
    private Menu menuid;

    @Getter
    @OneToMany(mappedBy = "playerid", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    @OrderBy("updatedat")
    private Set<Game> games = new LinkedHashSet<>();

    public void addGame(Game game) {
        games.add(game);
        game.setPlayer(this);
    }

    public void removeGame(Game game) {
        games.remove(game);
        game.setPlayer(null);
    }

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
