package fr.softsf.sudofx2024.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "grid")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Grid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gridid;

    @Size(max = 162)
    private String defaultgridvalue;

    @Size(max = 162)
    private String gridvalue;
}
