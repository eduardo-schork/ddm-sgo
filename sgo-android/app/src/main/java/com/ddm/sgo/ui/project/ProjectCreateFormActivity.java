package com.ddm.sgo.ui.project;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.ddm.sgo.R;

public class ProjectCreateFormActivity extends AppCompatActivity {

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
        getSupportActionBar().setTitle("Novo Projeto");

        setContentView(R.layout.activity_project_create_form);
    }
}