package com.ddm.sgo.infra.http_service.services;

import com.ddm.sgo.model.Team;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface TeamService {
    @GET("/team")
    Call<Team> findAll();

    @POST("/team")
    Call<Team> create(@Body Team team);
}
