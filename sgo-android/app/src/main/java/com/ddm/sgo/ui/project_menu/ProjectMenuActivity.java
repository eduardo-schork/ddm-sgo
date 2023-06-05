package com.ddm.sgo.ui.project_menu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ddm.sgo.R;
import com.ddm.sgo.infra.local_storage.LocalStorage;
import com.ddm.sgo.model.Project;
import com.ddm.sgo.repositories.ProjectRepository;
import com.ddm.sgo.ui.appointment.AppointmentCreateFormActivity;

public class ProjectMenuActivity extends AppCompatActivity {

    private Button checkinButton;
    private Button checkoutButton;
    private Button backButton;

    private Project projectData;

    private long lastCheckinId;

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private String formatLocalizationText() {
        return projectData.city + ", " + projectData.state + ", " + projectData.country;
    }

    private String formatGeocalizationText() {
        return projectData.latitude + " | " + projectData.longitude;
    }

    private void initializeComponents() {
        checkinButton = findViewById(R.id.checkinButton);
        checkinButton.setEnabled(false);

        checkoutButton = findViewById(R.id.checkoutButton);
        checkoutButton.setEnabled(false);

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> onSupportNavigateUp());

        TextView localizationText = findViewById(R.id.localizationText);
        localizationText.setText(formatLocalizationText());

        TextView geolocationText = findViewById(R.id.geolocationText);
        geolocationText.setText(formatGeocalizationText());

        Log.d("oieee", Boolean.toString(projectData.shouldPointGeolocation));
        Log.d("oieee", Boolean.toString(projectData.shouldPointImage));

        CheckBox geolocationCheckBox = findViewById(R.id.menuGeolocationBox);
        geolocationCheckBox.setChecked(projectData.shouldPointGeolocation);

        CheckBox imageCheckBox = findViewById(R.id.menuImageBox);
        imageCheckBox.setChecked(projectData.shouldPointImage);

        if (lastCheckinId == 0)
            checkinButton.setEnabled(true);

        if (lastCheckinId == projectData.uid)
            checkoutButton.setEnabled(true);

        checkinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProjectMenuActivity.this, AppointmentCreateFormActivity.class);
                intent.putExtra("project_id", projectData.uid);
                intent.putExtra("appointment_type", getString(R.string.appointment_checkin));
                startActivity(intent);
            }
        });

        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProjectMenuActivity.this, AppointmentCreateFormActivity.class);
                intent.putExtra("project_id", projectData.uid);
                intent.putExtra("appointment_type", getString(R.string.appointment_checkout));
                startActivity(intent);
            }
        });
    }

    private void getProjectData() {
        String lastCheckinIdString = LocalStorage.getInstance(getApplicationContext()).getValue(getString(R.string.last_checkin_id));
        if (lastCheckinIdString != null) {
            lastCheckinId = Integer.parseInt(lastCheckinIdString);
        }

        long projectId = 0;
        if (getIntent().hasExtra("project_id")) {
            projectId = (long) getIntent().getSerializableExtra("project_id");
        }

        if (projectId == 0) finish();

        projectData = ProjectRepository.getInstance(getApplicationContext()).findProjectById(projectId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_menu);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getProjectData();
        getSupportActionBar().setTitle(projectData.name);

        initializeComponents();
    }
}