package fr.softsf.sudofx2024.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

@Entity
@Table(name = "background")
public class BackgroundModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID backgrounduuid;

    @NotNull
    @Size(max = 7)
    private String hexcolor;

    @Size(max = 260)
    private String imagepath;

    private final boolean isimage = false;
    private final boolean isselected = false;

    public BackgroundModel() {
        // Constructeur par défaut
    }

    public BackgroundModel(UUID backgrounduuid, String hexcolor, String imagepath) {
        this.backgrounduuid = backgrounduuid;
        this.hexcolor = hexcolor;
        this.imagepath = imagepath;
    }

    // Getters et Setters
    public UUID getBackgrounduuid() {
        return backgrounduuid;
    }

    public void setBackgrounduuid(UUID backgrounduuid) {
        this.backgrounduuid = backgrounduuid;
    }

    public String getHexcolor() {
        return hexcolor;
    }

    public void setHexcolor(String hexcolor) {
        this.hexcolor = hexcolor;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public boolean isImage() {
        return isimage;
    }

    public boolean isSelected() {
        return isselected;
    }

    // Méthode Builder
    public static BackgroundModelBuilder builder() {
        return new BackgroundModelBuilder();
    }

    public static class BackgroundModelBuilder {
        private UUID backgrounduuid;
        private String hexcolor;
        private String imagepath;

        private BackgroundModelBuilder() {
        }

        public BackgroundModelBuilder backgrounduuid(UUID backgrounduuid) {
            this.backgrounduuid = backgrounduuid;
            return this;
        }

        public BackgroundModelBuilder hexcolor(String hexcolor) {
            this.hexcolor = hexcolor;
            return this;
        }

        public BackgroundModelBuilder imagepath(String imagepath) {
            this.imagepath = imagepath;
            return this;
        }

        public BackgroundModel build() {
            return new BackgroundModel(backgrounduuid, hexcolor, imagepath);
        }
    }

    @Override
    public String toString() {
        return "BackgroundModel{" +
                "backgrounduuid=" + backgrounduuid +
                ", hexcolor='" + hexcolor + '\'' +
                ", imagepath='" + imagepath + '\'' +
                ", isimage=" + isimage +
                ", isselected=" + isselected +
                '}';
    }

}
