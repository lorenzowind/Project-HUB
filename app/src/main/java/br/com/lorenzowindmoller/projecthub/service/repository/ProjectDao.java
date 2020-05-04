package br.com.lorenzowindmoller.projecthub.service.repository;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.com.lorenzowindmoller.projecthub.service.model.Project;

@Dao
public interface ProjectDao {

    @Insert
    void insert(Project project);

    @Update
    void update(Project project);

    @Delete
    void delete(Project project);

    @Query("DELETE FROM projects_table")
    void deleteAllProjects();

    @Query("SELECT * FROM projects_table")
    LiveData<List<Project>> getAllProjects();
}
