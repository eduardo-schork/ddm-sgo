package com.ddm.sgo.ui.team;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ddm.sgo.databinding.FragmentTeamCreateFormBinding;
import com.ddm.sgo.model.Team;
import com.ddm.sgo.repositories.TeamRepository;
import com.ddm.sgo.shared.toast_port.ToastPort;
import com.ddm.sgo.util.date_parser.DateParser;

public class TeamCreateFormFragment extends Fragment {

    private FragmentTeamCreateFormBinding binding;

    private Team sanitizeNewTeam() {
        EditText nameInput = binding.nameInput;
        EditText numberOfMembersInput = binding.numberOfMembersInput;

        Integer numberOfMembers = Integer.parseInt(numberOfMembersInput.getText().toString());

        long createdAt = DateParser.getCurrentMilliseconds();

        Team newTeam = new Team(
                nameInput.getText().toString(),
                numberOfMembers,
                createdAt
        );

        return newTeam;
    }

    private void createNewProject() {
        try {
            Team newTeam = sanitizeNewTeam();

            TeamRepository repository = TeamRepository.getInstance(getContext());
            repository.createProject(newTeam);

            ToastPort.showMessage(getContext(), "Equipe criada com sucesso!");
        } catch (Exception e) {
            ToastPort.showMessage(getContext(), "Não foi possível criar a equipe.");
        } finally {
            getActivity().finish();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTeamCreateFormBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button saveTeamButton = binding.saveProjectButton;
        saveTeamButton.setOnClickListener(v -> createNewProject());


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}