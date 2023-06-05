package com.ddm.sgo.ui.project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ddm.sgo.R;
import com.ddm.sgo.adapter.ProjectListItemAdapter;
import com.ddm.sgo.databinding.FragmentProjectListBinding;
import com.ddm.sgo.infra.local_storage.LocalStorage;
import com.ddm.sgo.model.Project;
import com.ddm.sgo.repositories.ProjectRepository;
import com.ddm.sgo.ui.project_menu.ProjectMenuActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ProjectListFragment extends Fragment {

    private Context context;
    private FragmentProjectListBinding binding;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private void setProjectListOnView() {
        long lastCheckinProjectId = Long.parseLong(LocalStorage.getInstance(getContext()).getValue(getString(R.string.last_checkin_id)));
        List<Project> projectList = ProjectRepository.getInstance(getContext()).getProjectList();
        ProjectListItemAdapter adapter = new ProjectListItemAdapter(projectList, lastCheckinProjectId);

        adapter.setOnClickListener(new ProjectListItemAdapter.OnClickListener() {
            @Override
            public void onClick(int position, Project model) {
                Intent intent = new Intent(getContext(), ProjectMenuActivity.class);
                intent.putExtra("project_id", model.uid);
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(adapter);
    }

    public void initializeComponents() {
//        Initialize create project button
        FloatingActionButton createProjectButton = binding.createProjectButton;
        createProjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createProjectIntent = new Intent(context, ProjectCreateFormActivity.class);
                getActivity().startActivity(createProjectIntent);
            }
        });

//        Initialize recycler view
        recyclerView = binding.recyclerView;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);


        swipeRefreshLayout = binding.swipeRefresh;
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setProjectListOnView();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity().getApplicationContext();
        binding = FragmentProjectListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        initializeComponents();
        setProjectListOnView();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}