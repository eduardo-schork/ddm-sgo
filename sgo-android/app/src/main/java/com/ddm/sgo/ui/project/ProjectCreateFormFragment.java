package com.ddm.sgo.ui.project;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ddm.sgo.R;
import com.ddm.sgo.databinding.FragmentProjectCreateFormBinding;
import com.ddm.sgo.infra.geolocation.Geolocation;
import com.ddm.sgo.model.Project;
import com.ddm.sgo.repositories.ProjectRepository;
import com.ddm.sgo.shared.toast_port.ToastPort;
import com.ddm.sgo.util.date_parser.DateParser;

public class ProjectCreateFormFragment extends Fragment {

    private FragmentProjectCreateFormBinding binding;

    private Project sanitizeNewProject() {
        EditText nameInput = binding.nameInput;
        EditText cityInput = binding.cityInput;
        EditText stateInput = binding.stateInput;
        EditText countryInput = binding.countryInput;
        CheckBox geolocationCheckBox = binding.geolocationCheckBox;
        CheckBox imageCheckBox = binding.imageCheckBox;

        long currentTime = DateParser.getCurrentMilliseconds();

        Location location = Geolocation.getInstance(getContext()).getCurrentLocation();

        Project newProject = new Project(
                nameInput.getText().toString(),
                cityInput.getText().toString(),
                stateInput.getText().toString(),
                countryInput.getText().toString(),
                geolocationCheckBox.isChecked(),
                imageCheckBox.isChecked(),
                currentTime,
                location.getLatitude(),
                location.getLongitude()
        );

        return newProject;
    }

    private void createNewProject() {
        try {
            Project newProject = sanitizeNewProject();

            ProjectRepository repository = ProjectRepository.getInstance(getContext());
            repository.createProject(newProject);

            ToastPort.showMessage(getContext(), getString(R.string.project_create_success));
        } catch (Exception e) {
            ToastPort.showMessage(getContext(), getString(R.string.project_create_error));
        } finally {
            getActivity().finish();
        }
    }


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProjectCreateFormBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button saveProjectButton = binding.saveProjectButton;
        saveProjectButton.setOnClickListener(v -> createNewProject());

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}