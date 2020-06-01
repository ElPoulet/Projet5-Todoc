package com.cleanup.todoc.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.cleanup.todoc.di.DI;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repository.Repository;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    private Repository repository;

    private LiveData<List<Task>> allTasks;
    private LiveData<List<Project>> projectList;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        repository = DI.getTaskRepository(application);
        allTasks = repository.getAllTasks();
        projectList = repository.getAllProjects();
    }


    public LiveData<List<Task>> getAllTasks(){
        return allTasks;
    }

    public LiveData<List<Project>> getAllProjects() {
        return  projectList;
    }

    public void deleteTask(Task task){
        repository.delete(task);
    }

    public void addNewTask(long projectId, String name, long creationTimestamp) {
        repository.addNewTask(projectId, name, creationTimestamp);
    }
}
