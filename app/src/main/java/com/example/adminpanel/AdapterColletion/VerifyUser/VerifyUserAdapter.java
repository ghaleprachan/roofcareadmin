package com.example.adminpanel.AdapterColletion.VerifyUser;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.adminpanel.APICollection.ApiCollection;
import com.example.adminpanel.ModelCollection.DeleteDistrict.DeleteDistrictResponse;
import com.example.adminpanel.ModelCollection.VerifyUser.Result;
import com.example.adminpanel.R;
import com.example.adminpanel.ServiceColletion.PlacesService.PlacesServices;
import com.example.adminpanel.ServiceColletion.VerifyUser.VerifyUserService;
import com.google.gson.GsonBuilder;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.ArrayList;

public class VerifyUserAdapter extends RecyclerView.Adapter<VerifyUserAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<Result> users;

    public VerifyUserAdapter(Context mContext, ArrayList<Result> districts) {
        this.mContext = mContext;
        this.users = districts;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.verify_user_fragment_design, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.fullName.setText(users.get(position).getFullName());
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.profile);
        Glide.with(mContext)
                .load(ApiCollection.BaseUrl + users.get(position).getUserProfileImage())
                .apply(options)
                .into(holder.profileImage);
        holder.gender.setText(users.get(position).getGender());
        holder.aboutUser.setText(users.get(position).getAboutUser());

        onVerifyClick(holder.verifyProfile, holder.progressBar, position);
        onContactClick(holder.contacts, position);
        onEmailsClick(holder.emails, position);
        onProfessionClick(holder.professions, position);
    }

    private void onProfessionClick(CardView professions, int position) {
        professions.setOnClickListener(v -> {
            try {
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(mContext,
                        android.R.layout.simple_list_item_1);
                for (int i = 0; i < users.get(position).getProfessions().size(); i++) {
                    arrayAdapter.add(users.get(position).getProfessions().get(i).getProfessionName());
                }
                final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setNegativeButton("Ok", (dialog, which) -> dialog.dismiss());
                builder.setTitle(users.get(position).getFullName() + " Professions");
                builder.setIcon(R.drawable.ic_phone_black_24dp);
                if (arrayAdapter.getCount() == 0) {
                    builder.setMessage("No Profession.");
                } else {
                    builder.setAdapter(arrayAdapter, (dialog, item) -> {
                        try {
                            Toast.makeText(mContext, "Professions: " + arrayAdapter.getItem(item), Toast.LENGTH_SHORT).show();
                        } catch (Exception ex) {
                            Toast.makeText(mContext, "Failed: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                AlertDialog alert = builder.create();
                alert.show();
            } catch (Exception ex) {
                Toast.makeText(mContext, "Something Wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onEmailsClick(CardView emails, int position) {
        emails.setOnClickListener(v -> {
            try {
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(mContext,
                        android.R.layout.simple_list_item_1);
                for (int i = 0; i < users.get(position).getEmails().size(); i++) {
                    arrayAdapter.add(users.get(position).getEmails().get(i).getEmail1());
                }
                final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setNegativeButton("Ok", (dialog, which) -> dialog.dismiss());
                builder.setTitle(users.get(position).getFullName() + " Emails");
                builder.setIcon(R.drawable.ic_phone_black_24dp);
                if (arrayAdapter.getCount() == 0) {
                    builder.setMessage("No Emails Available.");
                } else {
                    builder.setAdapter(arrayAdapter, (dialog, item) -> {
                        try {
                            mContext.startActivity(new Intent(Intent.ACTION_SENDTO,
                                    Uri.parse("mailto:" + arrayAdapter.getItem(item))));
                        } catch (Exception ex) {
                            Toast.makeText(mContext, "Can't open " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                AlertDialog alert = builder.create();
                alert.show();
            } catch (Exception ex) {
                Toast.makeText(mContext, "Something Wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onContactClick(CardView contacts, int position) {
        contacts.setOnClickListener(v -> {
            try {
                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(mContext,
                        android.R.layout.simple_list_item_1);
                for (int i = 0; i < users.get(position).getContacts().size(); i++) {
                    arrayAdapter.add(users.get(position).getContacts().get(i).getContactNumber());
                }
                final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setNegativeButton("Ok", (dialog, which) -> dialog.dismiss());
                builder.setTitle(users.get(position).getFullName() + " Contacts");
                builder.setIcon(R.drawable.ic_phone_black_24dp);
                if (arrayAdapter.getCount() == 0) {
                    builder.setMessage("No Contacts Available.");
                } else {
                    builder.setAdapter(arrayAdapter, (dialog, item) -> {
                        try {
                            mContext.startActivity(new Intent(Intent.ACTION_DIAL,
                                    Uri.parse("tel:" + arrayAdapter.getItem(item))));
                        } catch (Exception ex) {
                            Toast.makeText(mContext, "Can't Contact", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                AlertDialog alert = builder.create();
                alert.show();
            } catch (Exception ex) {
                Toast.makeText(mContext, "Something Wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onVerifyClick(Button verifyProfile, ProgressBar progressBar, int position) {
        verifyProfile.setOnClickListener(v -> {
            try {
                final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
                builder.setTitle("Verify User");
                builder.setMessage("Are sure to verify user?");
                builder.setPositiveButton("Yes", (dialog, which) -> {
                    verifyUserAPICall(position, progressBar, verifyProfile);
                });

                AlertDialog alert = builder.create();
                alert.show();
            } catch (Exception ex) {
                Toast.makeText(mContext, "Something Wrong!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void verifyUserAPICall(int position, ProgressBar progressBar, Button verifyProfile) {
        try {
            verifyProfile.setEnabled(false);
            verifyProfile.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            StringRequest request = new StringRequest(
                    Request.Method.PUT,
                    ApiCollection.verifyProfile + users.get(position).getUserId(),
                    response -> {
                        verifyProfile.setEnabled(true);
                        verifyProfile.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        try {
                            DeleteDistrictResponse verifyResponse = new GsonBuilder().create().fromJson(
                                    response, DeleteDistrictResponse.class
                            );
                            if (verifyResponse.getSuccess()) {
                                VerifyUserService.users.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, VerifyUserService.users.size());
                                Toast.makeText(mContext, "Success to verify", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception ex) {
                            Toast.makeText(mContext, "Response Exception: " + ex, Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        verifyProfile.setEnabled(true);
                        verifyProfile.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(mContext, "Error: " + error, Toast.LENGTH_SHORT).show();
                    }
            );
            RequestQueue requestQueue = Volley.newRequestQueue(mContext);
            requestQueue.add(request);
        } catch (Exception ex) {
            Toast.makeText(mContext, "Exception: " + ex, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView fullName, gender;
        private ExpandableTextView aboutUser;
        private Button verifyProfile;
        private CardView contacts, emails, professions;
        private ProgressBar progressBar;
        private ImageView profileImage;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            fullName = itemView.findViewById(R.id.fullName);
            aboutUser = itemView.findViewById(R.id.expand_text_view);
            gender = itemView.findViewById(R.id.gender);
            verifyProfile = itemView.findViewById(R.id.verifyProfile);
            contacts = itemView.findViewById(R.id.contacts);
            emails = itemView.findViewById(R.id.emails);
            professions = itemView.findViewById(R.id.professions);
            progressBar = itemView.findViewById(R.id.progressBar);
            profileImage = itemView.findViewById(R.id.profileImage);
        }
    }
}
