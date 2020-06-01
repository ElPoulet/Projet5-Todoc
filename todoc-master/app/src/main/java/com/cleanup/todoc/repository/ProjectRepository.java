package com.cleanup.todoc.repository;

import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.cleanup.todoc.dao.ProjectDao;
import com.cleanup.todoc.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class ProjectRepository {
    private ProjectDao projectDao;

    private LiveData<List<Project>> allProjects;

    public ProjectRepository(ProjectDao projectDao){
        this.projectDao = projectDao;
        allProjects = this.projectDao.loadAllProjects();
    }

    public LiveData<List<Project>> getAllProjects(){ return allProjects;}

}
