package fr.softsf.sudofx2024.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "menu")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuid;

    @NotNull
    @Setter
    private int mode;
}