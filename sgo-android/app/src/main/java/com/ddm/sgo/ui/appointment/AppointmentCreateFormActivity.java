package com.ddm.sgo.ui.appointment;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ddm.sgo.MainActivity;
import com.ddm.sgo.R;
import com.ddm.sgo.infra.geolocation.Geolocation;
import com.ddm.sgo.infra.local_storage.LocalStorage;
import com.ddm.sgo.model.Appointment;
import com.ddm.sgo.model.Project;
import com.ddm.sgo.model.Team;
import com.ddm.sgo.repositories.AppointmentRepository;
import com.ddm.sgo.repositories.ProjectRepository;
import com.ddm.sgo.repositories.TeamRepository;
import com.ddm.sgo.shared.toast_port.ToastPort;
import com.ddm.sgo.util.date_parser.DateParser;

import java.util.List;
import java.util.Objects;

public class AppointmentCreateFormActivity extends AppCompatActivity {
    TextView appointmentDateText;
    CheckBox geolocationCheckBox;
    RadioGroup presentTeamRadioGroup;
    Project projectData;
    List<Team> teamsList;
    long currentTime;
    String appointmentType;

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void loadData() {
        currentTime = DateParser.getCurrentMilliseconds();

        long projectId = 0;
        if (getIntent().hasExtra("project_id")) {
            projectId = (long) getIntent().getSerializableExtra("project_id");
        }

        if (getIntent().hasExtra("appointment_type")) {
            appointmentType = (String) getIntent().getSerializableExtra("appointment_type");
        }

        if (projectId == 0) finish();

        projectData = ProjectRepository.getInstance(getApplicationContext()).findProjectById(projectId);
        teamsList = TeamRepository.getInstance(getApplicationContext()).getTeamList();
    }

    private void initializeComponents() {
        geolocationCheckBox = findViewById(R.id.geolocationCheckBox);
        geolocationCheckBox.setChecked(projectData.shouldPointGeolocation);
        geolocationCheckBox.setEnabled(false);

        String currentTimeFormatted = DateParser.formatMillisecondsToDate(currentTime);
        appointmentDateText = findViewById(R.id.appointmentDateText);
        appointmentDateText.setText(currentTimeFormatted);

        presentTeamRadioGroup = findViewById(R.id.presentTeamRadioGroup);

        final RadioButton[] radioButtons = new RadioButton[teamsList.size()];
        RadioGroup presentTeamRadioGroup = findViewById(R.id.presentTeamRadioGroup);

        for (int i = 0; i < teamsList.size(); i++) {
            Team currentTeam = teamsList.get(i);
            radioButtons[i] = new RadioButton(this);
            radioButtons[i].setText(currentTeam.name);
            radioButtons[i].setId((int) currentTeam.uid);
            presentTeamRadioGroup.addView(radioButtons[i]);
        }

        Button createAppointmentButton = findViewById(R.id.createAppointmentButton);
        createAppointmentButton.setOnClickListener(v -> onCreateAppointmentHandler());
    }

    private void onCreateAppointmentHandler() {
        try {
            Location currentLocation = Geolocation.getInstance(getApplicationContext()).getCurrentLocation();

            Appointment newAppointment = new Appointment();

            newAppointment.presentTeamUid = presentTeamRadioGroup.getCheckedRadioButtonId();
            newAppointment.date = currentTime;
            newAppointment.projectUid = projectData.uid;
            newAppointment.type = appointmentType;
            newAppointment.createdAt = DateParser.getCurrentMilliseconds();

            if (projectData.shouldPointGeolocation) {
                newAppointment.latitude = currentLocation.getLatitude();
                newAppointment.longitude = currentLocation.getLongitude();
            }

            AppointmentRepository.getInstance(getApplicationContext()).createAppointment(newAppointment);

            if (Objects.equals(appointmentType, getString(R.string.appointment_checkin))) {
                LocalStorage.setValue(getString(R.string.last_checkin_id), Long.toString(projectData.uid));
            } else {
                LocalStorage.setValue(getString(R.string.last_checkin_id), "0");
            }

            ToastPort.showMessage(getApplicationContext(), "Apontamento criado com sucesso!");

            Intent intent = new Intent(AppointmentCreateFormActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } catch (Exception e) {
            ToastPort.showMessage(getApplicationContext(), "Não foi possível criar o apontamento");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_create_form);

        loadData();

        Geolocation.getInstance(getApplicationContext()).getCurrentLocation();

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (Objects.equals(appointmentType, getString(R.string.appointment_checkin))) {
            getSupportActionBar().setTitle("Entrada na obra");
        }

        if (Objects.equals(appointmentType, getString(R.string.appointment_checkout))) {
            getSupportActionBar().setTitle("Saída da obra");
        }

        initializeComponents();
    }
}