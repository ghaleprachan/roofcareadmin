package com.example.adminpanel.ActivityCollection.AddMunicipality;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.adminpanel.APICollection.ApiCollection;
import com.example.adminpanel.ModelCollection.DeleteDistrict.DeleteDistrictResponse;
import com.example.adminpanel.R;
import com.example.adminpanel.ServiceColletion.PlacesService.PlacesServices;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.Objects;

public class AddMunicipality extends AppCompatActivity {
    private EditText districtName, municipalityName;
    private Button add;
    private ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_municipality);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add " +
                PlacesServices.getMunicipalities(getDistrictId()).get(0).getDistrictName() +
                " Municipality");

        userInterface();
        districtName.setText(PlacesServices.getMunicipalities(getDistrictId()).get(0).getDistrictName());
        onAddClick();
    }

    private void onAddClick() {
        add.setOnClickListener(v -> {
            if (formValidation()) {
                addMunicipalityAPICall();
            }
        });
    }

    private void addMunicipalityAPICall() {
        try {
            add.setEnabled(false);
            add.setVisibility(View.GONE);
            loading.setVisibility(View.VISIBLE);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("MunicipalityName", municipalityName.getText().toString());
            jsonObject.put("DistrictId", getDistrictId());

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    ApiCollection.addMunicipality,
                    jsonObject,
                    response -> {
                        add.setEnabled(true);
                        add.setVisibility(View.VISIBLE);
                        loading.setVisibility(View.GONE);
                        try {
                            DeleteDistrictResponse deleteDistrictResponse = new GsonBuilder().create().fromJson(
                                    String.valueOf(response), DeleteDistrictResponse.class
                            );
                            if (deleteDistrictResponse.getSuccess()) {
                                Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show();
                                municipalityName.setText("");
                            }
                        } catch (Exception ex) {
                            Toast.makeText(this, "Response Exception: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        add.setEnabled(true);
                        add.setVisibility(View.VISIBLE);
                        loading.setVisibility(View.GONE);
                        Toast.makeText(this, "Error: " + error, Toast.LENGTH_SHORT).show();
                    }
            );
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(request);

        } catch (Exception ex) {
            Toast.makeText(this, "Exception: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean formValidation() {
        if (municipalityName.getText().toString().isEmpty()) {
            municipalityName.requestFocus();
            municipalityName.setError("Add Name");
            return false;
        } else {
            return true;
        }
    }

    private void userInterface() {
        districtName = findViewById(R.id.districtName);
        municipalityName = findViewById(R.id.municipalityname);
        add = findViewById(R.id.addMunicipality);
        loading = findViewById(R.id.loading);
    }

    private Integer getDistrictId() {
        return getIntent().getIntExtra("DistrictId", 0);
    }
}
