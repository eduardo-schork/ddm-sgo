package com.ddm.sgo.infra.http_service;

import com.ddm.sgo.infra.http_service.services.AppointmentService;
import com.ddm.sgo.infra.http_service.services.ProjectService;
import com.ddm.sgo.infra.http_service.services.TeamService;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class HttpRequestPort {

    private static HttpRequestPort instance = null;
    private final TeamService teamApi;
    private final AppointmentService appointmentApi;
    private final ProjectService projectApi;

    private HttpRequestPort() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        teamApi = retrofit.create(TeamService.class);
        projectApi = retrofit.create(ProjectService.class);
        appointmentApi = retrofit.create(AppointmentService.class);
    }

    public static synchronized HttpRequestPort getInstance() {
        if (instance == null) {
            instance = new HttpRequestPort();
        }
        return instance;
    }

    public TeamService getTeamApi() {
        return teamApi;
    }

    public AppointmentService getAppointmentApi() {
        return appointmentApi;
    }

    public ProjectService getProjectApi() {
        return projectApi;
    }
}
