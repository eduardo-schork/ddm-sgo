package com.ddm.sgo.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.ddm.sgo.model.Team;

import java.util.List;

@Dao
public interface TeamDAO {
    @Query("SELECT * FROM teams")
    List<Team> getAll();

    @Query("SELECT * FROM teams WHERE uid IN (:teamIds)")
    List<Team> loadAllByIds(int[] teamIds);

//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " + "last_name LIKE :last LIMIT 1")
//    Team findByName(String first, String last);

    @Insert
    void insertAll(Team... users);

    @Delete
    void delete(Team user);
}
