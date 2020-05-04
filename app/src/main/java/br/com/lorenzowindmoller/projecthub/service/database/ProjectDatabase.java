package br.com.lorenzowindmoller.projecthub.service.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import br.com.lorenzowindmoller.projecthub.service.model.Project;
import br.com.lorenzowindmoller.projecthub.service.repository.ProjectDao;

@Database(entities = {Project.class}, version = 1)
public abstract class ProjectDatabase extends RoomDatabase {

    private static ProjectDatabase instance;

    public abstract ProjectDao projectDao();

    public static synchronized ProjectDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ProjectDatabase.class,"projects_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private ProjectDao projectDao;

        private PopulateDbAsyncTask(ProjectDatabase db) {
            projectDao = db.projectDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            projectDao.insert(new Project("Project 1", "Organization", "A cool project.", null));
            return null;
        }
    }
}
