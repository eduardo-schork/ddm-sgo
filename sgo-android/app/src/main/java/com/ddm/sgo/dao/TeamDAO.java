package com.ddm.sgo.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ddm.sgo.model.Team;

import java.util.List;

@Dao
public interface TeamDAO {
    @Query("SELECT * FROM teams")
    List<Team> getAll();

    @Query("SELECT * FROM teams WHERE synchronized_at = 0")
    List<Team> getAllNotSynced();

    @Query("SELECT * FROM teams WHERE uid IN (:teamIds)")
    List<Team> loadAllByIds(int[] teamIds);

    @Insert
    void insertAll(Team... users);

    @Update
    void update(Team team);

    @Delete
    void delete(Team user);
}
