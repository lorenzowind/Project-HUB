package br.com.lorenzowindmoller.projecthub.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import br.com.lorenzowindmoller.projecthub.service.model.Project;
import br.com.lorenzowindmoller.projecthub.service.repository.Project.ProjectRepository;

public class ProjectViewModel extends AndroidViewModel {
    private ProjectRepository repository;
    private LiveData<List<Project>> allProjects;

    public ProjectViewModel(@NonNull Application application, int user_id) {
        super(application);
        repository = new ProjectRepository(application, user_id);
        allProjects = repository.getAllProjects();
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

    public void deleteAllProjects() {
        repository.deleteAllProjects();
    }

    public LiveData<List<Project>> getAllProjects() {
        return allProjects;
    }
}
