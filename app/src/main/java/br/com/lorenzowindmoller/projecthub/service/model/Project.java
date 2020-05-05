package br.com.lorenzowindmoller.projecthub.service.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "projects_table")
public class Project {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int user_id;

    private String name;

    private String type;

    private String description;

    private byte[] image;

    public Project(int user_id, String name, String type, String description, byte[] image) {
        this.user_id = user_id;
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

    public int getUser_id() {
        return user_id;
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
