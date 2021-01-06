package com.example.adminpanel.UIFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.adminpanel.APICollection.ApiCollection;
import com.example.adminpanel.APICollection.HubConnectionHolder;
import com.example.adminpanel.ModelCollection.DeleteDistrict.DeleteDistrictResponse;
import com.example.adminpanel.ModelCollection.Notification.NotificationResponse;
import com.example.adminpanel.R;
import com.google.gson.GsonBuilder;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;
import com.microsoft.signalr.HubConnectionState;

import org.json.JSONObject;

public class Dashboard extends Fragment {
    private EditText messageType, message;
    private Button sendBtn;
    private HubConnection hubConnection;
    private ProgressBar progressBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dashboard_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userInterface(view);
        onSendBtnClick();

        buildHubConnection();
    }

    private void buildHubConnection() {
        hubConnection = HubConnectionBuilder.create(HubConnectionHolder.HubUrl).build();
        hubConnection.start();
    }

    private void onSendBtnClick() {
        sendBtn.setOnClickListener(v -> {
            if (formValidation()) {
                notificationAPICall();
            }
        });
    }

    private void getNotifications(Integer notificationId) {
        try {
            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    ApiCollection.getNotification + notificationId,
                    response -> {
                        try {
                            NotificationResponse notificationResponse = new GsonBuilder().create().fromJson(
                                    response, NotificationResponse.class
                            );
                            if (notificationResponse.getSuccess()) {
                            }
                        } catch (Exception ex) {
                            Toast.makeText(getContext(), "Response Exception: " + ex, Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
                    }
            );
            RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
            requestQueue.add(request);
        } catch (Exception ex) {
            Toast.makeText(getContext(), "Exception: " + ex, Toast.LENGTH_SHORT).show();
        }
    }


    private void notificationAPICall() {
        try {
            sendBtn.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("NotificationType", messageType.getText().toString());
            jsonObject.put("NotificationText", message.getText().toString());
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    ApiCollection.addNotification,
                    jsonObject,
                    response -> {
                        sendBtn.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        try {
                            DeleteDistrictResponse notificationResponse = new GsonBuilder().create().fromJson(
                                    String.valueOf(response), DeleteDistrictResponse.class
                            );
                            if (notificationResponse.getSuccess()) {
                                hubConnection.start();
                                if (hubConnection.getConnectionState() == HubConnectionState.CONNECTED) {
                                    Integer notificationId = Integer.parseInt(notificationResponse.getResult());
                                    String header = messageType.getText().toString();
                                    String content = message.getText().toString();
                                    hubConnection.send("Notification", notificationId, header,
                                            content);
                                    messageType.setText("");
                                    message.setText("");
                                    Toast.makeText(getContext(), "Success: " + notificationResponse.getResult(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (Exception ex) {
                            Toast.makeText(getContext(), "Response Exception: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        progressBar.setVisibility(View.GONE);
                        sendBtn.setVisibility(View.VISIBLE);
                        Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
                    }
            );
            RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
            requestQueue.add(request);
        } catch (Exception ex) {
            Toast.makeText(getContext(), "Exception: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean formValidation() {
        if (messageType.getText().toString().isEmpty()) {
            messageType.setError("Please write heading");
            messageType.requestFocus();
            return false;
        } else if (message.getText().toString().isEmpty()) {
            message.setError("Write Message");
            message.requestFocus();
            messageType.setError(null);
            return false;
        } else {
            message.setError(null);
            return true;
        }
    }

    private void userInterface(View view) {
        messageType = view.findViewById(R.id.messageType);
        message = view.findViewById(R.id.message);
        sendBtn = view.findViewById(R.id.sendBtn);
        progressBar = view.findViewById(R.id.loading);
    }
}
