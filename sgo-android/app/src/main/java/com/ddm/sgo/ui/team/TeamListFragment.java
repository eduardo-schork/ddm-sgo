package com.ddm.sgo.ui.team;

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

import com.ddm.sgo.adapter.TeamListItemAdapter;
import com.ddm.sgo.databinding.FragmentTeamListBinding;
import com.ddm.sgo.model.Team;
import com.ddm.sgo.repositories.TeamRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TeamListFragment extends Fragment {
    private Context context;

    private FragmentTeamListBinding binding;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    private void setTeamListOnView() {
        List<Team> teamList = TeamRepository.getInstance(getContext()).getTeamList();
        TeamListItemAdapter adapter = new TeamListItemAdapter(teamList);

        adapter.setOnClickListener(new TeamListItemAdapter.OnClickListener() {
            @Override
            public void onClick(int position, Team model) {
//                Intent intent = new Intent(getContext(), ProjectMenuActivity.class);
//                intent.putExtra("project_id", model.uid);
//                startActivity(intent);
            }
        });

        recyclerView.setAdapter(adapter);
    }

    public void initializeComponents() {
        FloatingActionButton createProjectButton = binding.createTeamButton;
        createProjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(context, TeamCreateFormActivity.class);
                getActivity().startActivity(myIntent);
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
                setTeamListOnView();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity().getApplicationContext();
        binding = FragmentTeamListBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        initializeComponents();
        setTeamListOnView();

        return root;
    }

    @Override
    public void onResume() {
        setTeamListOnView();
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}