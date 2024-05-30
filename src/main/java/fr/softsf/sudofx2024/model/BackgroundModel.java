package fr.softsf.sudofx2024.model;

import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "background")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BackgroundModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID backgrounduuid;

    @NotNull
    @Size(max = 7)
    private String hexcolor;

    @Size(max = 260)
    private String imagepath;

    @Builder.Default
    private boolean isimage = false;
}
