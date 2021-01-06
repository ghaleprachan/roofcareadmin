package com.example.adminpanel.ActivityCollection.UserReports;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminpanel.AdapterColletion.SpReportAdapter.UserReportsAdapter;
import com.example.adminpanel.R;
import com.example.adminpanel.ServiceColletion.SpReport.SpReportService;

import java.util.Objects;

public class UserReportActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_report_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        userInterface();
        populateRecyclerView();
    }

    private Integer getSelectedItem() {
        return getIntent().getIntExtra("position", -7);
    }

    private void populateRecyclerView() {
        try {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            UserReportsAdapter reportsAdapter = new UserReportsAdapter(this, SpReportService.getSelectedReports(getSelectedItem()));
            recyclerView.setAdapter(reportsAdapter);
        } catch (Exception ex) {
            Toast.makeText(this, "Exception: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void userInterface() {
        recyclerView = findViewById(R.id.recyclerView);
    }
}
