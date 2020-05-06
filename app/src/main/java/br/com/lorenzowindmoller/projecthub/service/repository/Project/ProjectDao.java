package br.com.lorenzowindmoller.projecthub.service.repository.Project;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.lorenzowindmoller.projecthub.service.model.Project.Project;

@Dao
public interface ProjectDao {

    @Insert
    void insert(Project project);

    @Update
    void update(Project project);

    @Delete
    void delete(Project project);

    @Query("DELETE FROM projects_table WHERE user_id = :user_id")
    void deleteAllProjects(int user_id);

    @Query("SELECT * FROM projects_table WHERE user_id = :user_id")
    LiveData<List<Project>> getAllProjects(int user_id);
}
