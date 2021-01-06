package com.example.adminpanel.AdapterColletion.SpReportAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.adminpanel.APICollection.ApiCollection;
import com.example.adminpanel.ModelCollection.SpReport.Result;
import com.example.adminpanel.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserReportsAdapter extends RecyclerView.Adapter<UserReportsAdapter.MyViewHolder> {
    private Context context;
    private com.example.adminpanel.ModelCollection.SpReport.Result result;

    public UserReportsAdapter(Context context, Result result) {
        this.context = context;
        this.result = result;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.reports_design, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.profile);
        Glide.with(context)
                .load(ApiCollection.BaseUrl + result.getReports().get(position).getReportedByImage())
                .apply(options)
                .into(holder.profileImage);
        holder.name.setText(result.getReports().get(position).getReportedByName());
        holder.report.setText(result.getReports().get(position).getReportDescription());
    }

    @Override
    public int getItemCount() {
        return result.getReportCount();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView profileImage;
        private TextView name, report;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.fullName);
            report = itemView.findViewById(R.id.report);
        }
    }
}
