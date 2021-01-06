package com.example.adminpanel.AdapterColletion.ViewMunicipality;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
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
import com.example.adminpanel.ActivityCollection.Municipality.MunicipalityActivity;
import com.example.adminpanel.ModelCollection.DeleteDistrict.DeleteDistrictResponse;
import com.example.adminpanel.ModelCollection.PlacesResponse.Result;
import com.example.adminpanel.R;
import com.example.adminpanel.ServiceColletion.PlacesService.PlacesServices;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class ViewMunicipalityAdapter extends RecyclerView.Adapter<ViewMunicipalityAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<Result> districts;

    public ViewMunicipalityAdapter(Context mContext, ArrayList<Result> districts) {
        this.mContext = mContext;
        this.districts = districts;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.view_municipality_design, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.municipality.setText(districts.get(0).getMunicipalities().get(position).getMunicipalityName());
        onDeleteClick(holder.delete, position);
    }

    private void onDeleteClick(ImageView delete, int position) {
        delete.setOnClickListener(v -> {
            try {
                final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
                builder.setTitle("Delete Municipality");
                builder.setMessage("Are sure to delete municipality?");
                builder.setPositiveButton("Yes", (dialog, which) -> {
                    deleteMunicipalityApiCall(position);
                });

                AlertDialog alert = builder.create();
                alert.show();
            } catch (Exception ex) {
                Toast.makeText(mContext, "Something Wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteMunicipalityApiCall(int position) {
        try {
            StringRequest request = new StringRequest(
                    Request.Method.DELETE,
                    ApiCollection.deleteMunicipality + districts.get(0).getMunicipalities().get(position).getMunicipalityId(),
                    response -> {
                        try {
                            DeleteDistrictResponse deleteDistrictResponse = new GsonBuilder().create().fromJson(
                                    response, DeleteDistrictResponse.class
                            );
                            if (deleteDistrictResponse.getSuccess()) {
                                districts.get(0).getMunicipalities().remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, districts.get(0).getMunicipalities().size());
                            }
                        } catch (Exception ex) {
                            Toast.makeText(mContext, "Response Parse error: " + ex, Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        Toast.makeText(mContext, "Error: " + error, Toast.LENGTH_SHORT).show();
                    }
            );
            RequestQueue requestQueue = Volley.newRequestQueue(mContext);
            requestQueue.add(request);
        } catch (Exception ex) {
            Toast.makeText(mContext, "Exception: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return districts.get(0).getMunicipalities().size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView municipality;
        private ImageView delete;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            municipality = itemView.findViewById(R.id.municipalities);
            delete = itemView.findViewById(R.id.deleteMunicipality);
        }
    }
}
