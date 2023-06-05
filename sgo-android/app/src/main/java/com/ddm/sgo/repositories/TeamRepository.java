package com.ddm.sgo.repositories;

import android.content.Context;

import com.ddm.sgo.dao.TeamDAO;
import com.ddm.sgo.infra.local_database.LocalDatabase;
import com.ddm.sgo.model.Team;

import java.util.List;

public class TeamRepository {
    private static TeamRepository instance;
    private TeamDAO teamDAO;

    private TeamRepository() {
    }

    public static TeamRepository getInstance(Context context) {
        if (instance == null) {
            instance = new TeamRepository();
            instance.teamDAO = LocalDatabase.getDatabase(context).teamDAO();
        }

        return instance;
    }

    public void createProject(Team newTeam) {
        this.teamDAO.insertAll(newTeam);
    }

    public List<Team> getTeamList() {
        return this.teamDAO.getAll();
    }
}
