package com.ddm.sgo.repositories;

import android.content.Context;

import com.ddm.sgo.dao.AppointmentDAO;
import com.ddm.sgo.infra.local_database.LocalDatabase;
import com.ddm.sgo.model.Appointment;

import java.util.List;

public class AppointmentRepository {
    private static AppointmentRepository instance;

    private AppointmentDAO appointmentDAO;

    private AppointmentRepository() {
    }

    public static AppointmentRepository getInstance(Context context) {
        if (instance == null) {
            instance = new AppointmentRepository();
            instance.appointmentDAO = LocalDatabase.getDatabase(context).appointmentDAO();
        }

        return instance;
    }

    public Appointment findAppointmentById(long uid) {
        return this.appointmentDAO.findById(uid);
    }

    public void createAppointment(Appointment newAppointment) {
        this.appointmentDAO.insertAll(newAppointment);
    }

    public void updateAppointment(Appointment appointment) {
        this.appointmentDAO.update(appointment);
    }

    public List<Appointment> getAppointmentList() {
        return appointmentDAO.getAll();
    }

    public List<Appointment> getAppointmentListNotSynced() {
        return appointmentDAO.getAllNotSynced();
    }

}
