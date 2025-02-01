package fr.softsf.sudokufx.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "grid")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Grid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gridid;

    @NotNull
    @Setter
    @Size(max = 81)
    private String defaultgridvalue;

    @NotNull
    @Setter
    @Size(max = 810)
    private String gridvalue;

    @NotNull
    @Setter
    @Min(0)
    @Max(100)
    private Byte difficulty;

}
