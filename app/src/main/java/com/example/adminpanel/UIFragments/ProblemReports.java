package com.example.adminpanel.UIFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
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
import com.example.adminpanel.AdapterColletion.ProblemReport.ReportedProblemAdapter;
import com.example.adminpanel.ModelCollection.ReportedProblems.ReportedProblemResponse;
import com.example.adminpanel.R;
import com.example.adminpanel.ServiceColletion.LogInServices.LoginService;
import com.example.adminpanel.ServiceColletion.ProblemReport.ProblemReportsService;
import com.google.gson.GsonBuilder;

public class ProblemReports extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.problem_report_fragment, container, false);
    }

    private ProgressBar loading;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userInterface(view);

        offerReportApiCall();
        onRefresh();
    }

    private void onRefresh() {
        refreshLayout.setOnRefreshListener(this::offerReportApiCall);
    }

    private void offerReportApiCall() {
        try {
            if (refreshLayout.isRefreshing()) {
                loading.setVisibility(View.GONE);
            } else {
                loading.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    ApiCollection.getReportedProblems + LoginService.getAdminId(),
                    response -> {
                        refreshLayout.setRefreshing(false);
                        loading.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        try {
                            ReportedProblemResponse offerResponse = new GsonBuilder().create().fromJson(
                                    response, ReportedProblemResponse.class
                            );
                            if (offerResponse.getSuccess()) {
                                if (ProblemReportsService.addAll(offerResponse.getResult())) {
                                    fillUpRecycler();
                                }
                            }
                        } catch (Exception ex) {
                            Toast.makeText(getContext(), "Response Parse Error: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        refreshLayout.setRefreshing(false);
                        Toast.makeText(getContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
            );
            request.setRetryPolicy(new RetryPolicy() {
                @Override
                public int getCurrentTimeout() {
                    return 60000;
                }

                @Override
                public int getCurrentRetryCount() {
                    return 60000;
                }

                @Override
                public void retry(VolleyError error) throws VolleyError {

                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
            requestQueue.add(request);
        } catch (Exception ex) {
            Toast.makeText(getContext(), "Exception: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void fillUpRecycler() {
        try {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            /*TODO: make adapter*/
            ReportedProblemAdapter offerAdapter = new ReportedProblemAdapter(getContext(), ProblemReportsService.problemList);
            recyclerView.setAdapter(offerAdapter);
        } catch (Exception ex) {
            Toast.makeText(getContext(), "Recycler Fill Up: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void userInterface(View view) {
        loading = view.findViewById(R.id.loading);
        recyclerView = view.findViewById(R.id.post_reports_view);
        refreshLayout = view.findViewById(R.id.refresh);
    }
}
