package com.example.adminpanel.UIFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.adminpanel.APICollection.ApiCollection;
import com.example.adminpanel.ActivityCollection.AddDistrict.AddDistrict;
import com.example.adminpanel.AdapterColletion.District.DistrictAdapter;
import com.example.adminpanel.ModelCollection.PlacesResponse.PlacesResponse;
import com.example.adminpanel.R;
import com.example.adminpanel.ServiceColletion.LogInServices.LoginService;
import com.example.adminpanel.ServiceColletion.PlacesService.PlacesServices;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Districts extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.districts_fragment, container, false);
    }

    private RecyclerView districtsView;
    private LinearLayout loading;
    private SwipeRefreshLayout refreshLayout;
    private FloatingActionButton addDistrict;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userInterface(view);

        districtsAPICall();

        onRefresh();

        onAddDistrictClick();
    }

    private void onAddDistrictClick() {
        addDistrict.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AddDistrict.class);
            startActivity(intent);
        });
    }

    private void onRefresh() {
        refreshLayout.setOnRefreshListener(Districts.this::districtsAPICall);
    }

    private void districtsAPICall() {
        try {
            if (refreshLayout.isRefreshing()) {
                loading.setVisibility(View.GONE);
            } else {
                loading.setVisibility(View.VISIBLE);
            }
            addDistrict.setVisibility(View.GONE);
            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    ApiCollection.getPlaces + LoginService.userDetails.get(0).getAdminId(),
                    response -> {
                        addDistrict.setVisibility(View.VISIBLE);
                        refreshLayout.setRefreshing(false);
                        districtsView.setVisibility(View.VISIBLE);
                        loading.setVisibility(View.GONE);
                        try {
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            PlacesResponse placesResponse = gson.fromJson(response, PlacesResponse.class);
                            if (placesResponse.getSuccess()) {
                                if (PlacesServices.addAllPlaces(placesResponse.getResult())) {
                                    addToRecyclerView();
                                }
                            }
                        } catch (Exception ex) {
                            Toast.makeText(getContext(), "Response Exception: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        addDistrict.setVisibility(View.VISIBLE);
                        refreshLayout.setRefreshing(false);
                        districtsView.setVisibility(View.VISIBLE);
                        loading.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
                    }
            );
            request.setRetryPolicy(new RetryPolicy() {
                @Override
                public int getCurrentTimeout() {
                    return 50000;
                }

                @Override
                public int getCurrentRetryCount() {
                    return 50000;
                }

                @Override
                public void retry(VolleyError error) throws VolleyError {

                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
            requestQueue.add(request);
        } catch (Exception ex) {
            Toast.makeText(getContext(), "Failed to get districts " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void addToRecyclerView() {
        try {
            districtsView.setLayoutManager(new LinearLayoutManager(getContext()));
            DistrictAdapter districtAdapter = new DistrictAdapter(getContext(), PlacesServices.places);
            districtsView.setAdapter(districtAdapter);
        } catch (Exception ex) {
            Toast.makeText(getContext(), "Failed to add: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void userInterface(View view) {
        districtsView = view.findViewById(R.id.districtsView);
        loading = view.findViewById(R.id.loading);
        refreshLayout = view.findViewById(R.id.refresh);
        addDistrict = view.findViewById(R.id.addDistrict);
    }
}
