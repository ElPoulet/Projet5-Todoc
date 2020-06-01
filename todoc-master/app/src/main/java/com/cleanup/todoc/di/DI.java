package com.cleanup.todoc.di;

import android.app.Application;

import com.cleanup.todoc.database.Database;
import com.cleanup.todoc.repository.ProjectRepository;
import com.cleanup.todoc.repository.Repository;
import com.cleanup.todoc.repository.TaskRepository;

public class DI {
    private static boolean instantiateDbInMemory = false;

    private static Repository repository = null;
    private static ProjectRepository projectRepository = null;
    private static TaskRepository taskRepository = null;

    public static Repository getTaskRepository(Application application) {
        if(repository == null) {
            Database db;
            if(instantiateDbInMemory) {
                db = Database.getNewDatabaseInMemory(application);
            } else {
                db = Database.getInstance(application); //Pour avoir la db
            }
            projectRepository = new ProjectRepository(db.projectDao());
            taskRepository = new TaskRepository(db.taskDao());
            repository = new Repository(taskRepository,projectRepository);
        }
        return repository;
    }


    public static void setInstantiateDbInMemory(boolean value) {
        instantiateDbInMemory = value;
    }
}
