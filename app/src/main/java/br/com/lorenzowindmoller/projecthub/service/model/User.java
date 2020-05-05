package br.com.lorenzowindmoller.projecthub.service.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "users_table")
public class User implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private String email;

    private String password;

    private String image;

    public User(String name, String email, String password, String image) {
        this.name = name;
        this.email = email;
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getImage() {
        return image;
    }
}
