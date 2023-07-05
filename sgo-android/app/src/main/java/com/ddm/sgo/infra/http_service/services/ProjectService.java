package com.ddm.sgo.infra.http_service.services;

import com.ddm.sgo.model.Project;
import com.ddm.sgo.model.Team;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ProjectService {

    @GET("/project")
    Call<Project> findAll();

    @POST("/project")
    Call<Project> create(@Body Project project);
}
