package com.example.adminpanel.UIFragments;

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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.adminpanel.APICollection.ApiCollection;
import com.example.adminpanel.AdapterColletion.VerifyUser.VerifyUserAdapter;
import com.example.adminpanel.ModelCollection.VerifyUser.VerifyUserResponse;
import com.example.adminpanel.R;
import com.example.adminpanel.ServiceColletion.LogInServices.LoginService;
import com.example.adminpanel.ServiceColletion.VerifyUser.VerifyUserService;
import com.google.gson.GsonBuilder;

public class VerifyUser extends Fragment {
    private RecyclerView recyclerUsers;
    private LinearLayout loading;
    private SwipeRefreshLayout refreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.verify_user_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userInterface(view);
        getUserApiCall();

        onPageRefresh();
    }

    private void onPageRefresh() {
        refreshLayout.setOnRefreshListener(this::getUserApiCall);
    }

    private void getUserApiCall() {
        try {
            if (refreshLayout.isRefreshing()) {
                loading.setVisibility(View.GONE);
            } else {
                loading.setVisibility(View.VISIBLE);
            }
            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    ApiCollection.getUserDetails + LoginService.userDetails.get(0).getAdminId(),
                    response -> {
                        refreshLayout.setRefreshing(false);
                        loading.setVisibility(View.GONE);
                        try {
                            VerifyUserResponse userResponse = new GsonBuilder().create().fromJson(
                                    response, VerifyUserResponse.class
                            );
                            if (userResponse.getSuccess()) {
                                if (VerifyUserService.addAll(userResponse.getResult())) {
                                    fillUpRecycler();
                                }
                            }
                        } catch (Exception ex) {
                            Toast.makeText(getContext(), "Response Exception: " + ex, Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        refreshLayout.setRefreshing(false);
                        loading.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
                    }
            );
            RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
            requestQueue.add(request);
        } catch (Exception ex) {
            Toast.makeText(getContext(), "ExceptionL " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void fillUpRecycler() {
        try {
            recyclerUsers.setLayoutManager(new LinearLayoutManager(requireContext()));
            VerifyUserAdapter verifyUserAdapter = new VerifyUserAdapter(getContext(), VerifyUserService.users);
            recyclerUsers.setAdapter(verifyUserAdapter);
        } catch (Exception ex) {
            Toast.makeText(getContext(), "Exception: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void userInterface(View view) {
        recyclerUsers = view.findViewById(R.id.users);
        loading = view.findViewById(R.id.loading);
        refreshLayout = view.findViewById(R.id.refresh);
    }
}
