package com.cleanup.todoc;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.content.Context;
import android.support.test.runner.AndroidJUnit4;

import com.cleanup.todoc.dao.ProjectDao;
import com.cleanup.todoc.dao.TaskDao;
import com.cleanup.todoc.database.Database;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static com.cleanup.todoc.utils.LiveDataTestUtil.getValue;
import static org.hamcrest.Matchers.empty;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.contains;

@RunWith(AndroidJUnit4.class)
public class DatabaseInstrumentedTest {
    private TaskDao taskDao;
    private ProjectDao projectDao;
    private Database db;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void createDb() {
        Context context = getTargetContext();
        db = Database.getNewDatabaseInMemory(context);
        taskDao = db.taskDao();
        projectDao = db.projectDao();
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void getTaskAndGetProject() throws InterruptedException {
        Task expectedTask = new Task(1,0, "taskTest", 0);
        taskDao.insert(expectedTask);
        List<Project> allprojects = getValue(projectDao.loadAllProjects());
        assertNotNull(allprojects);
        List<Task> allTasks = getValue(taskDao.getAllTasks());
        assertNotNull(allTasks);

        taskDao.deleteAllTasks();
    }

    @Test
    public void insertTask() throws InterruptedException {
        Task expectedTask = new Task(1,0, "taskTest", 0);
        taskDao.insert(expectedTask);
        List<Project> allProjects = getValue(projectDao.loadAllProjects());
        assertNotNull(allProjects);

        List<Task> allTasks = getValue(taskDao.getAllTasks());
        assertThat(allTasks, contains(expectedTask));

        taskDao.deleteAllTasks();
    }

    @Test
    public void deleteTask() throws InterruptedException {

        Task expectedTask = new Task(1,0, "taskTest", 0);
        taskDao.insert(expectedTask);
        List<Project> allProjects = getValue(projectDao.loadAllProjects());
        assertNotNull(allProjects);

        taskDao.deleteAllTasks();
        List<Task> emptyAllTasks = getValue(taskDao.getAllTasks());
        assertThat(emptyAllTasks, empty());

    }


}