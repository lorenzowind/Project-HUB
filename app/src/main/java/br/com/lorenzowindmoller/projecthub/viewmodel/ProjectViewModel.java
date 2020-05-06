package br.com.lorenzowindmoller.projecthub.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import java.util.List;

import br.com.lorenzowindmoller.projecthub.service.model.Project.Project;
import br.com.lorenzowindmoller.projecthub.service.repository.Project.ProjectRepository;

public class ProjectViewModel extends AndroidViewModel {
    private ProjectRepository repository;
    private LiveData<List<Project>> allProjects;

    public ProjectViewModel(@NonNull Application application) {
        super(application);
        repository = new ProjectRepository(application);
    }

    public void insert(Project project) {
        repository.insert(project);
    }

    public void update(Project project) {
        repository.update(project);
    }

    public void delete(Project project) {
        repository.delete(project);
    }

    public void deleteAllProjects(int user_id) {
        repository.deleteAllProjects(user_id);
    }

    public LiveData<List<Project>> getAllProjects(int user_id) {
        allProjects = repository.getAllProjects(user_id);
        return allProjects;
    }
}
