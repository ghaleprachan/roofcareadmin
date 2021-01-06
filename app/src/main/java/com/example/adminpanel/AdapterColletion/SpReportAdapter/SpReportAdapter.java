package com.example.adminpanel.AdapterColletion.SpReportAdapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestCoordinator;
import com.bumptech.glide.request.RequestOptions;
import com.example.adminpanel.APICollection.ApiCollection;
import com.example.adminpanel.ActivityCollection.UserReports.UserReportActivity;
import com.example.adminpanel.ModelCollection.DeleteDistrict.DeleteDistrictResponse;
import com.example.adminpanel.ModelCollection.SpReport.Result;
import com.example.adminpanel.R;
import com.google.gson.GsonBuilder;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class SpReportAdapter extends RecyclerView.Adapter<SpReportAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Result> userList;

    public SpReportAdapter(Context context, ArrayList<Result> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.sp_report_design, parent, false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(userList.get(position).getFullName());
        holder.ratingBar.setRating(Float.parseFloat(String.valueOf(userList.get(position).getUserRating())));
        holder.gender.setText(userList.get(position).getGender());
        holder.aboutUser.setText(userList.get(position).getAboutUser());
        holder.viewReports.setText(userList.get(position).getReportCount().toString() + " Reports");
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.profile);
        holder.removeUser.setText("Remove User");
        Glide.with(context)
                .load(ApiCollection.BaseUrl + userList.get(position).getUserProfileImage())
                .apply(options)
                .into(holder.profileImage);

        removeUserClick(holder.removeUser, holder.progressBar, position);
        viewReports(holder.viewReports, position);
    }

    private void viewReports(Button viewReports, int position) {
        viewReports.setOnClickListener(v -> {
            Intent intent = new Intent(context, UserReportActivity.class);
            intent.putExtra("position", position);
            context.startActivity(intent);
        });
    }

    private void removeUserClick(Button removeUser, ProgressBar progressBar, int position) {
        removeUser.setOnClickListener(v -> {
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
            builder.setTitle("Deregister User");
            builder.setMessage("Are sure to remove user?");
            builder.setPositiveButton("Yes", (dialog, which) -> {
                removeUser(removeUser, progressBar, position);
            });

            AlertDialog alert = builder.create();
            alert.show();
        });
    }

    private void removeUser(Button removeUser, ProgressBar progressBar, int position) {
        try {
            removeUser.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            StringRequest request = new StringRequest(
                    Request.Method.DELETE,
                    ApiCollection.deregisterUser + userList.get(position).getUserId(),
                    response -> {
                        removeUser.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        try {
                            DeleteDistrictResponse delete = new GsonBuilder().create().fromJson(response, DeleteDistrictResponse.class);
                            if (delete.getSuccess()) {
                                userList.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, userList.size());
                                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "This user has bookings incomplete" + response, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception ex) {
                            Toast.makeText(context, "Parse Exception: " + ex, Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        removeUser.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(context, "Error: " + error, Toast.LENGTH_SHORT).show();
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
                public void retry(VolleyError error) {

                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(request);

        } catch (Exception ex) {
            Toast.makeText(context, "Exception is here.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private ExpandableTextView aboutUser;
        private TextView name, gender;
        private RatingBar ratingBar;
        private Button viewReports, removeUser;
        private CircleImageView profileImage;
        private ProgressBar progressBar;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            aboutUser = itemView.findViewById(R.id.expand_text_view);
            name = itemView.findViewById(R.id.name);
            gender = itemView.findViewById(R.id.gender);
            ratingBar = itemView.findViewById(R.id.ratings);
            viewReports = itemView.findViewById(R.id.viewReports);
            removeUser = itemView.findViewById(R.id.removeUser);
            profileImage = itemView.findViewById(R.id.image);
            progressBar = itemView.findViewById(R.id.removeLoading);
        }
    }
}
