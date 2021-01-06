package com.example.adminpanel.ActivityCollection.Municipality;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Toast;

import com.example.adminpanel.ActivityCollection.AddMunicipality.AddMunicipality;
import com.example.adminpanel.AdapterColletion.ViewMunicipality.ViewMunicipalityAdapter;
import com.example.adminpanel.R;
import com.example.adminpanel.ServiceColletion.PlacesService.PlacesServices;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class MunicipalityActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FloatingActionButton addMunicipality;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_municipality);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(PlacesServices
                .getMunicipalities(getDistrictId()).get(0).getDistrictName() + " Municipalities");

        userInterface();
        fillView();

        onAddMunicipalityClick();
    }

    private void onAddMunicipalityClick() {
        addMunicipality.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddMunicipality.class);
            intent.putExtra("DistrictId", getDistrictId());
            startActivity(intent);
        });
    }

    private void fillView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ViewMunicipalityAdapter viewMunicipalityAdapter = new ViewMunicipalityAdapter(this, PlacesServices
                .getMunicipalities(getDistrictId()));
        recyclerView.setAdapter(viewMunicipalityAdapter);
    }

    private void userInterface() {
        recyclerView = findViewById(R.id.municipalities);
        addMunicipality = findViewById(R.id.addMunicipality);
    }

    private Integer getDistrictId() {
        return getIntent().getIntExtra("DistrictId", 0);
    }
}
