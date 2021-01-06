package com.example.adminpanel.AdapterColletion.MunicipalityAdapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class MunicipalityAdapter extends RecyclerView.Adapter<MunicipalityAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<Result> districts;

    public MunicipalityAdapter(Context mContext, ArrayList<Result> districts) {
        this.mContext = mContext;
        this.districts = districts;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.districts_fragment_design, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.district.setText(districts.get(position).getDistrictName());
        holder.edit.setVisibility(View.GONE);

        onDeleteClick(holder.delete, position);
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.buddha);

        holder.cityImage.setVisibility(View.GONE);

        onViewMunicipalityClick(holder.viewMunicipality, position);
    }

    private void onViewMunicipalityClick(TextView viewMunicipality, int position) {
        viewMunicipality.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, MunicipalityActivity.class);
            intent.putExtra("DistrictId", districts.get(position).getDistrictId());
            mContext.startActivity(intent);
        });
    }

    private void onDeleteClick(ImageView delete, int position) {
        delete.setOnClickListener(v -> {
            try {
                final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
                builder.setTitle("Select Address");
                builder.setMessage("Are sure to cancel booking?");
                builder.setPositiveButton("Yes", (dialog, which) -> {
                    deleteCityAPI(position);
                });

                AlertDialog alert = builder.create();
                alert.show();
            } catch (Exception ex) {
                Toast.makeText(mContext, "Something Wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteCityAPI(int position) {
        try {
            StringRequest request = new StringRequest(
                    Request.Method.DELETE,
                    ApiCollection.deleteDistrict + districts.get(position).getDistrictId(),
                    response -> {
                        try {
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            DeleteDistrictResponse deleteDistrictResponse = gson.fromJson(response, DeleteDistrictResponse.class);
                            if (deleteDistrictResponse.getSuccess()) {
                                PlacesServices.places.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, PlacesServices.places.size());
                            }
                        } catch (Exception ex) {
                            Toast.makeText(mContext, "Response Exception: " + ex, Toast.LENGTH_SHORT).show();
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
        return districts.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView district, viewMunicipality;
        private ImageView delete, edit, cityImage;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            district = itemView.findViewById(R.id.district);
            delete = itemView.findViewById(R.id.deleteCity);
            edit = itemView.findViewById(R.id.editCity);
            cityImage = itemView.findViewById(R.id.cityImage);
            viewMunicipality = itemView.findViewById(R.id.viewMunicipality);
        }
    }
}
