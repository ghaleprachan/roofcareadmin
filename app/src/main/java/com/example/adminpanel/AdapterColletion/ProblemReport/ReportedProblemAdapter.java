package com.example.adminpanel.AdapterColletion.ProblemReport;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.adminpanel.APICollection.ApiCollection;
import com.example.adminpanel.ActivityCollection.OfferReports.OfferReportsActivity;
import com.example.adminpanel.ActivityCollection.ProblemReports.ProblemReportsActivity;
import com.example.adminpanel.DateFormat.DateFormat;
import com.example.adminpanel.ModelCollection.DeleteDistrict.DeleteDistrictResponse;
import com.example.adminpanel.ModelCollection.ReportedProblems.Result;
import com.example.adminpanel.R;
import com.google.gson.GsonBuilder;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class ReportedProblemAdapter extends RecyclerView.Adapter<ReportedProblemAdapter.MyViewHolder> {
    private Context context;
    private List<Result> reportedProblems;

    public ReportedProblemAdapter(Context context, List<Result> reportedProblems) {
        this.context = context;
        this.reportedProblems = reportedProblems;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.reported_offer_design, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.profile);
        Glide.with(context)
                .load(ApiCollection.BaseUrl + reportedProblems.get(position).getProblemByImage())
                .apply(options)
                .into(holder.postedByImage);
        Glide.with(context)
                .load(ApiCollection.BaseUrl + reportedProblems.get(position).getProblemImage())
                .into(holder.postImage);
        holder.validDate.setVisibility(View.GONE);
        holder.postedByName.setText(reportedProblems.get(position).getProblemByName());
        holder.postedDate.setText("Posted On " + DateFormat.convertToDate(reportedProblems.get(position).getPostedDate(), "MMM dd, yyyy 'at' h:ss a"));
        holder.postDescription.setText(reportedProblems.get(position).getProblemDescription());
        holder.viewReports.setText(reportedProblems.get(position).getReportCount().toString() + " Reports");
        onRemovePostClick(holder.removePost, holder.progressBar, position);
        onViewReportClick(holder.viewReports, position);
    }

    private void onViewReportClick(Button viewReports, int position) {
        viewReports.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProblemReportsActivity.class);
            intent.putExtra("position", position);
            context.startActivity(intent);
        });
    }

    private void onRemovePostClick(Button removePost, ProgressBar progressBar, int position) {
        removePost.setOnClickListener(v -> {
            try {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
                builder.setTitle("Remove Problem");
                builder.setMessage("Are sure to remove problem?");
                builder.setPositiveButton("Yes", (dialog, which) -> {
                    deletePost(position, progressBar, removePost);
                });

                AlertDialog alert = builder.create();
                alert.show();
            } catch (Exception ex) {
                Toast.makeText(context, "Something Wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deletePost(int position, ProgressBar progressBar, Button removePost) {
        try {
            progressBar.setVisibility(View.VISIBLE);
            removePost.setVisibility(View.GONE);
            StringRequest request = new StringRequest(
                    Request.Method.DELETE,
                    ApiCollection.deleteReportedProblem + reportedProblems.get(position).getProblemId(),
                    response -> {
                        Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        try {
                            DeleteDistrictResponse districtResponse = new GsonBuilder().create().fromJson(response, DeleteDistrictResponse.class);
                            if (districtResponse.getSuccess()) {
                                removePost.setVisibility(View.VISIBLE);
                                reportedProblems.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, reportedProblems.size());
                                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception ex) {
                            Toast.makeText(context, "Parse Exception: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        progressBar.setVisibility(View.GONE);
                        removePost.setVisibility(View.VISIBLE);
                        Toast.makeText(context, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    });
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(request);
        } catch (Exception ex) {
            Toast.makeText(context, "Exception: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return reportedProblems.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView postedByName, postedDate, validDate;
        private CircleImageView postedByImage;
        private ImageView postImage;
        private ExpandableTextView postDescription;
        private Button viewReports, removePost;
        private ProgressBar progressBar;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            postDescription = itemView.findViewById(R.id.expand_text_view);
            postedByName = itemView.findViewById(R.id.postedByName);
            postedByImage = itemView.findViewById(R.id.postedByImage);
            postedDate = itemView.findViewById(R.id.postedDate);
            validDate = itemView.findViewById(R.id.postValidDate);
            postImage = itemView.findViewById(R.id.postImage);
            viewReports = itemView.findViewById(R.id.viewReports);
            removePost = itemView.findViewById(R.id.removePost);
            progressBar = itemView.findViewById(R.id.deleteLoading);
        }
    }
}
