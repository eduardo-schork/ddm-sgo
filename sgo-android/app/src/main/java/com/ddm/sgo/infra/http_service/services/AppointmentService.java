package com.ddm.sgo.infra.http_service.services;

import com.ddm.sgo.model.Appointment;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AppointmentService {
    @GET("/appointment")
    Call<Appointment> findAll();

    @POST("/appointment")
    Call<Appointment> create(@Body Appointment appointment);
}
