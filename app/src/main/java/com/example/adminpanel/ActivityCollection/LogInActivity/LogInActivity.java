package com.example.adminpanel.ActivityCollection.LogInActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.adminpanel.APICollection.ApiCollection;
import com.example.adminpanel.MainActivity;
import com.example.adminpanel.ModelCollection.LogInResponse.LogInResponse;
import com.example.adminpanel.R;
import com.example.adminpanel.SSHSolve.HttpsTrustManager;
import com.example.adminpanel.ServiceColletion.LogInServices.LoginService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

public class LogInActivity extends AppCompatActivity {
    private TextView username, password;
    private Button logIn;
    private ProgressBar progressBar;
    private TextView errorMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        userInterface();

        onLogInButtonClick();

    }

    private void onLogInButtonClick() {
        logIn.setOnClickListener(v -> {
            if (formValidation()) {
                logInApiCall();
            }
        });
    }

    private void logInApiCall() {
        try {
            HttpsTrustManager.allowAllSSL();
            logIn.setEnabled(false);
            logIn.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            errorMsg.setVisibility(View.GONE);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Username", username.getText().toString());
            jsonObject.put("Password", password.getText().toString());
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    ApiCollection.logIn,
                    jsonObject,
                    response -> {
                        logIn.setEnabled(true);
                        logIn.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        try {
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            LogInResponse logInResponse = gson.fromJson(String.valueOf(response), LogInResponse.class);
                            if (logInResponse.getSuccess()) {
                                errorMsg.setVisibility(View.GONE);
                                if (LoginService.addDetails(logInResponse.getTokenNumber())) {
                                    startActivity(new Intent(this, MainActivity.class));
                                    finish();
                                }
                            } else {
                                Toast.makeText(this, "Invalid Username password!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception ex) {
                            errorMsg.setVisibility(View.VISIBLE);
                            Toast.makeText(this, "Invalid Username password!", Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        logIn.setEnabled(true);
                        logIn.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        errorMsg.setVisibility(View.VISIBLE);
                        errorMsg.setText(error.getMessage());
                        Toast.makeText(this, "Error" + error, Toast.LENGTH_SHORT).show();
                    }
            );
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(request);
        } catch (Exception ex) {
            Toast.makeText(this, "Exception: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean formValidation() {
        if (username.getText().toString().isEmpty()) {
            username.setError("Enter username");
            username.requestFocus();
            return false;
        } else if (password.getText().toString().isEmpty()) {
            username.setError(null);
            password.setError("Enter password");
            password.requestFocus();
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }

    private void userInterface() {
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        logIn = findViewById(R.id.logIn);
        progressBar = findViewById(R.id.progress_circular);
        errorMsg = findViewById(R.id.errorMsg);
    }
}
