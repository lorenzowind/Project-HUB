package br.com.lorenzowindmoller.projecthub.service.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "projects_table")
public class Project {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private String type;

    private String description;

    private byte[] image;

    public Project(String name, String type, String description, byte[] image) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.image = image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public byte[] getImage() {
        return image;
    }
}
