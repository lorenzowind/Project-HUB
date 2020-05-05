package br.com.lorenzowindmoller.projecthub.service.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import br.com.lorenzowindmoller.projecthub.service.model.Project.Project;
import br.com.lorenzowindmoller.projecthub.service.model.User.User;
import br.com.lorenzowindmoller.projecthub.service.repository.Project.ProjectDao;
import br.com.lorenzowindmoller.projecthub.service.repository.User.UserDao;

@Database(entities = {User.class, Project.class}, version = 1, exportSchema = false)
public abstract class ProjectDatabase extends RoomDatabase {

    private static ProjectDatabase instance;

    public abstract ProjectDao projectDao();
    public abstract UserDao userDao();

    public static synchronized ProjectDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ProjectDatabase.class,"project_hub_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
