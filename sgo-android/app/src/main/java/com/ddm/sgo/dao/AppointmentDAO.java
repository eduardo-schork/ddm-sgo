package com.ddm.sgo.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ddm.sgo.model.Appointment;

import java.util.List;

@Dao
public interface AppointmentDAO {
    @Query("SELECT * FROM appointments")
    List<Appointment> getAll();

    @Query("SELECT * FROM appointments WHERE uid IN (:appointmentIds)")
    List<Appointment> loadAllByIds(int[] appointmentIds);

    @Query("SELECT * FROM appointments WHERE synchronized_at = 0")
    List<Appointment> getAllNotSynced();

    @Query("SELECT * FROM appointments WHERE uid = :uid LIMIT 1")
    Appointment findById(long uid);

    @Update
    void update(Appointment appointment);


    @Insert
    void insertAll(Appointment... projects);

    @Delete
    void delete(Appointment project);
}
