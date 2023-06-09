package com.ddm.sgo.ui.synchronization;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ddm.sgo.adapter.synchronization_queue.SynchronizationQueueItem;
import com.ddm.sgo.adapter.synchronization_queue.SynchronizationQueueItemAdapter;
import com.ddm.sgo.databinding.FragmentSynchronizationQueueBinding;
import com.ddm.sgo.model.Appointment;
import com.ddm.sgo.model.Project;
import com.ddm.sgo.model.Team;
import com.ddm.sgo.repositories.AppointmentRepository;
import com.ddm.sgo.repositories.ProjectRepository;
import com.ddm.sgo.repositories.TeamRepository;
import com.ddm.sgo.services.synchronization_service.SynchronizationService;
import com.ddm.sgo.util.date_parser.DateParser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class SynchronizationQueueFragment extends Fragment {
    private Context context;
    private FragmentSynchronizationQueueBinding binding;
    private RecyclerView projectsRecyclerView;
    private RecyclerView appointmentsRecyclerView;
    private RecyclerView teamsRecyclerView;
    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            loadDataOnView();
        }
    };
    private SwipeRefreshLayout swipeRefreshLayout;

    private void loadDataOnView() {
        List<Team> teamList = TeamRepository.getInstance(getContext()).getTeamListNotSynced();
        List<Project> projectList = ProjectRepository.getInstance(getContext()).getProjectListNotSynced();
        List<Appointment> appointmentList = AppointmentRepository.getInstance(getContext()).getAppointmentListNotSynced();

        String pendingStatusText = "PENDENTE";

        List<SynchronizationQueueItem> teamQueueList = new ArrayList<>();
        for (Team team : teamList) {
            String itemCreatedAt = DateParser.formatMillisecondsToDate(team.createdAt);
            SynchronizationQueueItem item = new SynchronizationQueueItem(team.name, itemCreatedAt, pendingStatusText);
            teamQueueList.add(item);
        }

        List<SynchronizationQueueItem> projectQueueList = new ArrayList<>();
        for (Project project : projectList) {
            String itemCreatedAt = DateParser.formatMillisecondsToDate(project.createdAt);
            SynchronizationQueueItem item = new SynchronizationQueueItem(project.name, itemCreatedAt, pendingStatusText);
            projectQueueList.add(item);
        }

        List<SynchronizationQueueItem> appointmentQueueList = new ArrayList<>();
        for (Appointment appointment : appointmentList) {
            String itemCreatedAt = DateParser.formatMillisecondsToDate(appointment.createdAt);
            SynchronizationQueueItem item = new SynchronizationQueueItem(appointment.type, itemCreatedAt, pendingStatusText);
            appointmentQueueList.add(item);
        }

        SynchronizationQueueItemAdapter teamAdapter = new SynchronizationQueueItemAdapter(teamQueueList);
        SynchronizationQueueItemAdapter projectAdapter = new SynchronizationQueueItemAdapter(projectQueueList);
        SynchronizationQueueItemAdapter appointmentAdapter = new SynchronizationQueueItemAdapter(appointmentQueueList);

        TextView appointmentsText = binding.appointmentsText;
        TextView projectsText = binding.projectsText;
        TextView teamsText = binding.teamsText;

        if (appointmentQueueList.size() == 0) {
            appointmentsText.setVisibility(View.INVISIBLE);
        }
        if (projectQueueList.size() == 0) {
            projectsText.setVisibility(View.INVISIBLE);
        }
        if (teamQueueList.size() == 0) {
            teamsText.setVisibility(View.INVISIBLE);
        }

        projectsRecyclerView.setAdapter(projectAdapter);
        appointmentsRecyclerView.setAdapter(appointmentAdapter);
        teamsRecyclerView.setAdapter(teamAdapter);
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    public void initializeComponents() {
        FloatingActionButton createProjectButton = binding.syncQueueButton;
        createProjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMyServiceRunning(SynchronizationService.class)) return;
                Intent intent = new Intent(getContext(), SynchronizationService.class);
                context.startService(intent);
            }
        });


        //        Initialize recycler view
        projectsRecyclerView = binding.projectsRecyclerView;
        RecyclerView.LayoutManager projectsLayoutManager = new LinearLayoutManager(context);
        projectsRecyclerView.setLayoutManager(projectsLayoutManager);
        projectsRecyclerView.setHasFixedSize(true);

        appointmentsRecyclerView = binding.appointmentsRecyclerView;
        RecyclerView.LayoutManager appointmentsLayoutManager = new LinearLayoutManager(context);
        appointmentsRecyclerView.setLayoutManager(appointmentsLayoutManager);
        appointmentsRecyclerView.setHasFixedSize(true);

        teamsRecyclerView = binding.teamsRecyclerView;
        RecyclerView.LayoutManager teamsLayoutManager = new LinearLayoutManager(context);
        teamsRecyclerView.setLayoutManager(teamsLayoutManager);
        teamsRecyclerView.setHasFixedSize(true);


        swipeRefreshLayout = binding.swipeRefresh;
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadDataOnView();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity().getApplicationContext();
        binding = FragmentSynchronizationQueueBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        initializeComponents();
        loadDataOnView();

        getActivity().registerReceiver(this.broadcastReceiver, new IntentFilter("SYNC_BROADCAST"));
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().unregisterReceiver(broadcastReceiver);
        binding = null;
    }
}
