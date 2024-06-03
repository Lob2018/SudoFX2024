package fr.softsf.sudofx2024.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.UUID;

@Entity
@Table(name = "grid")
public class GridModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID griduuid;

    @Size(max = 162)
    private String defaultgridvalue;

    @Size(max = 162)
    private String gridvalue;

    public GridModel() {
        // Constructeur par défaut
    }

    public GridModel(UUID griduuid, String defaultgridvalue, String gridvalue) {
        this.griduuid = griduuid;
        this.defaultgridvalue = defaultgridvalue;
        this.gridvalue = gridvalue;
    }

    // Getters et Setters
    public UUID getGriduuid() {
        return griduuid;
    }

    public void setGriduuid(UUID griduuid) {
        this.griduuid = griduuid;
    }

    public String getDefaultgridvalue() {
        return defaultgridvalue;
    }

    public void setDefaultgridvalue(String defaultgridvalue) {
        this.defaultgridvalue = defaultgridvalue;
    }

    public String getGridvalue() {
        return gridvalue;
    }

    public void setGridvalue(String gridvalue) {
        this.gridvalue = gridvalue;
    }

    // Méthode Builder
    public static GridModelBuilder builder() {
        return new GridModelBuilder();
    }

    public static class GridModelBuilder {
        private UUID griduuid;
        private String defaultgridvalue;
        private String gridvalue;

        private GridModelBuilder() {
        }

        public GridModelBuilder griduuid(UUID griduuid) {
            this.griduuid = griduuid;
            return this;
        }

        public GridModelBuilder defaultgridvalue(String defaultgridvalue) {
            this.defaultgridvalue = defaultgridvalue;
            return this;
        }

        public GridModelBuilder gridvalue(String gridvalue) {
            this.gridvalue = gridvalue;
            return this;
        }

        public GridModel build() {
            return new GridModel(griduuid, defaultgridvalue, gridvalue);
        }
    }

    @Override
    public String toString() {
        return "GridModel{" +
                "griduuid=" + griduuid +
                ", defaultgridvalue='" + defaultgridvalue + '\'' +
                ", gridvalue='" + gridvalue + '\'' +
                '}';
    }

}
