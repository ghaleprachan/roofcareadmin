package com.example.adminpanel.ActivityCollection.AddDistrict;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.adminpanel.APICollection.ApiCollection;
import com.example.adminpanel.ModelCollection.AddDistrict.AddDistrictResponse;
import com.example.adminpanel.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

public class AddDistrict extends AppCompatActivity {
    private EditText districtName;
    private ImageView districtImage;
    private Button addDistrict;
    private ProgressBar progressBar;
    private Bitmap bitmap;
    private TextView errorMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_district);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        userInterface();

        onImageClick();
        onAddButtonClick();
    }

    private void onAddButtonClick() {
        addDistrict.setOnClickListener(v -> {
            if (formValidation()) {
                addDistrictApiCall();
            }
        });
    }

    private void addDistrictApiCall() {
        try {
            addDistrict.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);
            addDistrict.setVisibility(View.GONE);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("DistrictName", districtName.getText().toString());
            jsonObject.put("DistrictImage", bitmapToString(bitmap));
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    ApiCollection.addDistrict,
                    jsonObject,
                    response -> {
                        try {
                            addDistrict.setEnabled(true);
                            progressBar.setVisibility(View.GONE);
                            addDistrict.setVisibility(View.VISIBLE);
                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();
                            AddDistrictResponse addDistrictResponse = gson.fromJson(String.valueOf(response), AddDistrictResponse.class);
                            if (addDistrictResponse.getSuccess()) {
                                Toast.makeText(this, "District Added", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            } else {
                                Toast.makeText(this, addDistrictResponse.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception ex) {
                            Toast.makeText(this, "Response Exception: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        addDistrict.setEnabled(true);
                        progressBar.setVisibility(View.GONE);
                        addDistrict.setVisibility(View.VISIBLE);
                        Toast.makeText(this, "Error: " + error, Toast.LENGTH_SHORT).show();
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
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(request);
        } catch (Exception ex) {
            Toast.makeText(this, "Exception: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void onImageClick() {
        districtImage.setOnClickListener(v -> {
            selectImage();
        });
    }

    private void selectImage() {
        try {
            CropImage.activity().start(AddDistrict.this);
        } catch (Exception ex) {
            Toast.makeText(this, "Failed to upload image: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                assert result != null;
                Uri mImageUri = result.getUri();
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mImageUri);
                districtImage.setImageBitmap(bitmap);

                if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception e = result.getError();
                    Toast.makeText(this, "Possible error is: " + e, Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Exception: " + ex, Toast.LENGTH_SHORT).show();
        }
    }

    private String bitmapToString(Bitmap bitmap) {
        if (bitmap != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] imgBytes = byteArrayOutputStream.toByteArray();
            return Base64.encodeToString(imgBytes, Base64.DEFAULT);
        } else {
            return null;
        }
    }

    private boolean formValidation() {
        if (districtName.getText().toString().isEmpty()) {
            districtName.requestFocus();
            districtName.setError("Add Name");
            return false;
        } else if (bitmap == null) {
            districtName.setError(null);
            errorMsg.setVisibility(View.VISIBLE);
            return false;
        } else {
            errorMsg.setVisibility(View.GONE);
            return true;
        }
    }

    private void userInterface() {
        districtName = findViewById(R.id.districtName);
        districtImage = findViewById(R.id.addImage);
        addDistrict = findViewById(R.id.addDistrict);
        progressBar = findViewById(R.id.progressBar);
        errorMsg = findViewById(R.id.errorMsg);
    }
}
