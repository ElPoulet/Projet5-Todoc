package com.cleanup.todoc.repository;


import android.arch.lifecycle.LiveData;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class Repository {
    private TaskRepository taskDao;
    private ProjectRepository projectDao;

    public Repository(TaskRepository taskRepository,ProjectRepository projectRepository){
        this.taskDao = taskRepository;
        this.projectDao = projectRepository;
    }

    public LiveData<List<Task>> getAllTasks(){
        return taskDao.getAllTasks();
    }

    public void delete(Task task){
        taskDao.delete(task);
    }

    public LiveData<List<Project>> getAllProjects(){
        return projectDao.getAllProjects();
    }

    public void addNewTask(long projectId, String name, long creationTimestamp) {
        taskDao.addNewTask(projectId, name, creationTimestamp);
    }
}
