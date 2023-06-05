package com.ddm.sgo.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.ddm.sgo.model.Project;

import java.util.List;

@Dao
public interface ProjectDAO {
    @Query("SELECT * FROM projects")
    List<Project> getAll();

    @Query("SELECT * FROM projects WHERE uid IN (:projectIds)")
    List<Project> loadAllByIds(int[] projectIds);

    @Query("SELECT * FROM projects WHERE synchronized_at = 0")
    List<Project> getAllNotSynced();

    @Query("SELECT * FROM projects WHERE uid = :uid LIMIT 1")
    Project findById(long uid);

    @Insert
    void insertAll(Project... projects);

    @Delete
    void delete(Project project);
}
