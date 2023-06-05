package com.ddm.sgo.repositories;

import android.content.Context;

import com.ddm.sgo.dao.ProjectDAO;
import com.ddm.sgo.infra.local_database.LocalDatabase;
import com.ddm.sgo.model.Project;

import java.util.List;

public class ProjectRepository {
    private static ProjectRepository instance;

    private ProjectDAO projectDAO;

    private ProjectRepository() {
    }

    public static ProjectRepository getInstance(Context context) {
        if (instance == null) {
            instance = new ProjectRepository();
            instance.projectDAO = LocalDatabase.getDatabase(context).projectDAO();
        }

        return instance;
    }

    public Project findProjectById(long uid) {
        return this.projectDAO.findById(uid);
    }

    public void createProject(Project newProject) {
        this.projectDAO.insertAll(newProject);
    }

    public List<Project> getProjectList() {
        return projectDAO.getAll();
    }
}
