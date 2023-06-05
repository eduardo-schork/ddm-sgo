package com.ddm.sgo.infra.local_database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.ddm.sgo.dao.AppointmentDAO;
import com.ddm.sgo.dao.ProjectDAO;
import com.ddm.sgo.dao.TeamDAO;
import com.ddm.sgo.model.Appointment;
import com.ddm.sgo.model.Project;
import com.ddm.sgo.model.Team;

@Database(version = 1, entities = {
        Project.class,
        Team.class,
        Appointment.class
})
public abstract class LocalDatabase extends RoomDatabase {
    private static volatile LocalDatabase localDatabaseInstance;

    public static LocalDatabase getDatabase(final Context context) {
        if (localDatabaseInstance == null) {
            synchronized (LocalDatabase.class) {
                if (localDatabaseInstance == null) {
                    localDatabaseInstance = Room.databaseBuilder(context.getApplicationContext(), LocalDatabase.class, "database")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return localDatabaseInstance;
    }

    public abstract ProjectDAO projectDAO();

    public abstract TeamDAO teamDAO();

    public abstract AppointmentDAO appointmentDAO();

}
