package fr.softsf.sudofx2024.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "grid")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GridModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID griduuid;

    @Size(max = 162)
    private String defaultgridvalue;

    @Size(max = 162)
    private String gridvalue;
}
