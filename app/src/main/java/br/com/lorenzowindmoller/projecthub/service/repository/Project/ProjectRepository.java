package br.com.lorenzowindmoller.projecthub.service.repository.Project;

import android.app.Application;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import br.com.lorenzowindmoller.projecthub.service.database.ProjectDatabase;
import br.com.lorenzowindmoller.projecthub.service.model.Project.Project;

public class ProjectRepository {
    private ProjectDao projectDao;
    private LiveData<List<Project>> allProjects;

    public ProjectRepository(Application application) {
        ProjectDatabase database = ProjectDatabase.getInstance(application);
        projectDao = database.projectDao();
    }

    public void insert(Project project) {
        new InsertProjectAsyncTask(projectDao).execute(project);
    }

    public void update(Project project) {
        new UpdateProjectAsyncTask(projectDao).execute(project);
    }

    public void delete(Project project) {
        new DeleteProjectAsyncTask(projectDao).execute(project);
    }

    public void deleteAllProjects(int user_id) {
        new DeleteAllProjectAsyncTask(projectDao).execute(user_id);
    }

    public LiveData<List<Project>> getAllProjects(int user_id) {
        allProjects = projectDao.getAllProjects(user_id);
        return allProjects;
    }

    private static class InsertProjectAsyncTask extends AsyncTask<Project, Void, Void> {
        private ProjectDao projectDao;

        private InsertProjectAsyncTask(ProjectDao projectDao) {
            this.projectDao = projectDao;
        }

        @Override
        protected Void doInBackground(Project... projects) {
            projectDao.insert(projects[0]);
            return null;
        }
    }

    private static class UpdateProjectAsyncTask extends AsyncTask<Project, Void, Void> {
        private ProjectDao projectDao;

        private UpdateProjectAsyncTask(ProjectDao projectDao) {
            this.projectDao = projectDao;
        }

        @Override
        protected Void doInBackground(Project... projects) {
            projectDao.update(projects[0]);
            return null;
        }
    }

    private static class DeleteProjectAsyncTask extends AsyncTask<Project, Void, Void> {
        private ProjectDao projectDao;

        private DeleteProjectAsyncTask(ProjectDao projectDao) {
            this.projectDao = projectDao;
        }

        @Override
        protected Void doInBackground(Project... projects) {
            projectDao.delete(projects[0]);
            return null;
        }
    }

    private static class DeleteAllProjectAsyncTask extends AsyncTask<Integer, Void, Void> {
        private ProjectDao projectDao;

        private DeleteAllProjectAsyncTask(ProjectDao projectDao) {
            this.projectDao = projectDao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            projectDao.deleteAllProjects(integers[0]);
            return null;
        }
    }
}
