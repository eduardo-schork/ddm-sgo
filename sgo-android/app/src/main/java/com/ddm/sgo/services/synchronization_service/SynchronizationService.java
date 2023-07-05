package com.ddm.sgo.services.synchronization_service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.ddm.sgo.infra.http_service.HttpRequestPort;
import com.ddm.sgo.model.Appointment;
import com.ddm.sgo.model.Project;
import com.ddm.sgo.model.Team;
import com.ddm.sgo.repositories.AppointmentRepository;
import com.ddm.sgo.repositories.ProjectRepository;
import com.ddm.sgo.repositories.TeamRepository;
import com.ddm.sgo.util.date_parser.DateParser;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SynchronizationService extends Service {
    HttpRequestPort httpRequestPort = HttpRequestPort.getInstance();
    Timer serviceTimer;
    TimerTask serviceTimerTask;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

    }

    private List<Team> getTeamData() {
        TeamRepository teamRepository = TeamRepository.getInstance(getApplicationContext());
        List<Team> teamList = teamRepository.getTeamListNotSynced();
        return teamList;
    }

    private void syncronizeTeamData(List<Team> teamList) {
        TeamRepository teamRepository = TeamRepository.getInstance(getApplicationContext());

        for (Team team : teamList) {
            Call<Team> call = httpRequestPort.getTeamApi().create(team);

            call.enqueue(new Callback<Team>() {
                @Override
                public void onResponse(Call<Team> call, Response<Team> response) {
                    long currentTime = DateParser.getCurrentMilliseconds();
                    team.synchronizedAt = currentTime;
                    teamRepository.updateTeam(team);
                }

                @Override
                public void onFailure(Call<Team> call, Throwable t) {
                    Log.d("onFailure", t.getMessage());
                }
            });
        }
    }

    private List<Project> getProjectData() {
        ProjectRepository projectRepository = ProjectRepository.getInstance(getApplicationContext());
        List<Project> projectList = projectRepository.getProjectListNotSynced();
        return projectList;
    }

    private void syncronizeProjectData(List<Project> projectList) {
        ProjectRepository projectRepository = ProjectRepository.getInstance(getApplicationContext());

        for (Project project : projectList) {
            Call<Project> call = httpRequestPort.getProjectApi().create(project);
            call.enqueue(new Callback<Project>() {
                @Override
                public void onResponse(Call<Project> call, Response<Project> response) {
                    long currentTime = DateParser.getCurrentMilliseconds();
                    project.synchronizedAt = currentTime;
                    projectRepository.updateProject(project);
                }

                @Override
                public void onFailure(Call<Project> call, Throwable t) {
                    Log.d("onFailure", t.getMessage());
                }
            });
        }
    }

    private List<Appointment> getAppointmentData() {
        AppointmentRepository appointmentRepository = AppointmentRepository.getInstance(getApplicationContext());
        List<Appointment> appointmentList = appointmentRepository.getAppointmentListNotSynced();
        return appointmentList;
    }

    private void syncronizeAppointmentData(List<Appointment> appointmentList) {
        AppointmentRepository appointmentRepository = AppointmentRepository.getInstance(getApplicationContext());

        for (Appointment appointment : appointmentList) {
            Call<Appointment> call = httpRequestPort.getAppointmentApi().create(appointment);

            call.enqueue(new Callback<Appointment>() {
                @Override
                public void onResponse(Call<Appointment> call, Response<Appointment> response) {
                    long currentTime = DateParser.getCurrentMilliseconds();
                    appointment.synchronizedAt = currentTime;
                    appointmentRepository.updateAppointment(appointment);
                }

                @Override
                public void onFailure(Call<Appointment> call, Throwable t) {
                    Log.d("onFailure", t.getMessage());
                }
            });
        }
    }

    private void emitFinishBroadcast() {
        this.sendBroadcast(new Intent().setAction("SYNC_BROADCAST"));
    }

    private void startServiceCore() {
        List<Project> projectList = getProjectData();
        List<Appointment> appointmentList = getAppointmentData();
        List<Team> teamList = getTeamData();

        boolean hasProjectsToSync = projectList.size() > 0;
        boolean hasAppointmentsToSync = appointmentList.size() > 0;
        boolean hasTeamsToSync = teamList.size() > 0;

        boolean shouldSynchronizeData = hasProjectsToSync || hasAppointmentsToSync || hasTeamsToSync;

        if (shouldSynchronizeData) {
            syncronizeAppointmentData(appointmentList);
            syncronizeProjectData(projectList);
            syncronizeTeamData(teamList);
        } else {
            emitFinishBroadcast();
            stopSelf();
        }
    }

    private void runServiceWithInterval() {
        int delay = 0;
        int period = 5 * 1000;

        serviceTimer = new Timer();
        serviceTimerTask = new TimerTask() {
            @Override
            public void run() {
                startServiceCore();
            }
        };

        serviceTimer.scheduleAtFixedRate(serviceTimerTask, delay, period);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Sincronização iniciada ", Toast.LENGTH_LONG).show();

        runServiceWithInterval();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        serviceTimer.cancel();
        Toast.makeText(this, "Sincronização terminou", Toast.LENGTH_LONG).show();
        super.onDestroy();
    }

}
