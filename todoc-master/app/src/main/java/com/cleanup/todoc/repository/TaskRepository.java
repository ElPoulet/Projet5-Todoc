package com.cleanup.todoc.repository;

import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.cleanup.todoc.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskRepository {
    private TaskDao taskDao;

    private LiveData<List<Task>> allTasks;

    public TaskRepository(TaskDao taskDao){
        this.taskDao = taskDao;
        allTasks = this.taskDao.getAllTasks();
    }

    public void delete(Task task){
        new TaskRepository.DeleteTaskAsyncTask(taskDao).execute(task);
    }


    public LiveData<List<Task>> getAllTasks(){
        return allTasks;
    }


    public void addNewTask(long projectId, String name, long creationTimestamp){
        Task task = new Task(0, projectId, name, creationTimestamp);
        new TaskRepository.InsertTaskAsyncTask(taskDao).execute(task);
    }


    private static class InsertTaskAsyncTask extends AsyncTask<Task, Void, Void> {
        private TaskDao taskDao;

        private InsertTaskAsyncTask(TaskDao taskDao){
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks){
            taskDao.insert(tasks[0]);
            return null;
        }
    }


    private static class DeleteTaskAsyncTask extends AsyncTask<Task, Void, Void> {
        private TaskDao taskDao;

        private DeleteTaskAsyncTask(TaskDao taskDao){
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks){
            taskDao.delete(tasks[0]);
            return null;
        }
    }
}
