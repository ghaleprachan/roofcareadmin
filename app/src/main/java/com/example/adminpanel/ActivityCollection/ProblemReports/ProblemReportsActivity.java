package com.example.adminpanel.ActivityCollection.ProblemReports;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminpanel.AdapterColletion.ProblemReport.ProblemReportsAdapter;
import com.example.adminpanel.AdapterColletion.ReportedOffer.OfferReportsAdapter;
import com.example.adminpanel.R;
import com.example.adminpanel.ServiceColletion.OfferReports.OfferReportsService;
import com.example.adminpanel.ServiceColletion.ProblemReport.ProblemReportsService;

import java.util.Objects;

public class ProblemReportsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_reports);
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
            ProblemReportsAdapter reportsAdapter = new ProblemReportsAdapter(this, ProblemReportsService.getSelectedReport(getSelectedItem()));
            recyclerView.setAdapter(reportsAdapter);
        } catch (Exception ex) {
            Toast.makeText(this, "Exception: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void userInterface() {
        recyclerView = findViewById(R.id.recyclerView);
    }
}
