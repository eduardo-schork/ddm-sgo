package com.ddm.sgo.ui.team;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.ddm.sgo.R;

public class TeamCreateFormActivity extends AppCompatActivity {

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Nova Equipe");

        setContentView(R.layout.activity_team_create_form);
    }
}