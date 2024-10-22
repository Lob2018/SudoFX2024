package fr.softsf.sudofx2024.model;

import jakarta.persistence.*;
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

    @Setter
    @Size(max = 162)
    private String defaultgridvalue;

    @Setter
    @Size(max = 162)
    private String gridvalue;

}
