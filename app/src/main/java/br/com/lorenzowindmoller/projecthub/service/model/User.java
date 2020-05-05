package br.com.lorenzowindmoller.projecthub.service.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "users_table")
public class User {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private String email;

    private String password;

    private byte[] image;

    public User(String name, String email, String password, byte[] image) {
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

    public byte[] getImage() {
        return image;
    }
}
