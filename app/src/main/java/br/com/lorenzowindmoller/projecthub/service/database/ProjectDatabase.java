package br.com.lorenzowindmoller.projecthub.service.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import br.com.lorenzowindmoller.projecthub.service.model.Project;
import br.com.lorenzowindmoller.projecthub.service.model.User;
import br.com.lorenzowindmoller.projecthub.service.repository.Project.ProjectDao;
import br.com.lorenzowindmoller.projecthub.service.repository.User.UserDao;

@Database(entities = {User.class, Project.class}, version = 1)
public abstract class ProjectDatabase extends RoomDatabase {

    private static ProjectDatabase instance;

    public abstract ProjectDao projectDao();
    public abstract UserDao userDao();

    public static synchronized ProjectDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ProjectDatabase.class,"project_hub_database")
                    .fallbackToDestructiveMigration()
                    //.addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    /*private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
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
            projectDao.insert(new Project(0,"Project 1", "Organization", "A cool project.", null));
            return null;
        }
    }*/
}
