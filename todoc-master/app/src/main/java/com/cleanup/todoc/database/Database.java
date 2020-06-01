package com.cleanup.todoc.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.cleanup.todoc.dao.ProjectDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.dao.TaskDao;

@android.arch.persistence.room.Database(entities = {Task.class, Project.class}, version = 1)
public abstract class Database extends RoomDatabase {

    private static Database instance;

    public abstract TaskDao taskDao();
    public abstract ProjectDao projectDao();

    public static synchronized Database getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), Database.class, "task_database")
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }


    public static Database getNewDatabaseInMemory(final Context context){
        instance = Room.inMemoryDatabaseBuilder(context.getApplicationContext(),
                Database.class)
                .allowMainThreadQueries()
                .addCallback(roomCallback)
                .fallbackToDestructiveMigration()
                .build();
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

        private PopulateDbAsyncTask(Database db){
            projectDao = db.projectDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            Log.d("testDb", "doInBackground: ");
            projectDao.deleteAll();
            Project[] projects = Project.getAllProjects();
            for (Project project : projects) {
                projectDao.insert(project);
            }
            return null;
        }
    }
}
