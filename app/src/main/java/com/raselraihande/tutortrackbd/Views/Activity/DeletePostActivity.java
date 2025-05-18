package com.raselraihande.tutortrackbd.Views.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.raselraihande.tutortrackbd.Model.UploadResponse;
import com.raselraihande.tutortrackbd.Network.RetrofitClient;
import com.raselraihande.tutortrackbd.Network.TutorApiService;
import com.raselraihande.tutortrackbd.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeletePostActivity extends AppCompatActivity {
    private static final String PREFERENCES_FILE = "DeleteRequestPrefs";
    private static final String DELETE_REQUEST_COUNT = "deleteRequestCount";
    private static final String LAST_REQUEST_DATE = "lastRequestDate";

    private TextInputEditText edName, edNumber, edReason;
    private ImageView imagSrc, imgBack;
    private CardView cardImagpick;
    private Button btnDelete;
    private boolean allCheck = false;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_post);

        initiaLization();
        clickEvent();
        setTextWatchers();
    }

    private void clickEvent() {
        cardImagpick.setOnClickListener(view -> pickImage());

        btnDelete.setOnClickListener(view -> {
            if (allCheck) {
                if (canMakeDeleteRequest()) {
                    uploadData();
                } else {
                    Toast.makeText(DeletePostActivity.this, "You have reached the limit of 3 delete requests today.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imgBack.setOnClickListener(view -> finish());
    }

    private void uploadData() {
        String strName = getTrimmedText(edName);
        String strNumber = getTrimmedText(edNumber);
        String strReason = getTrimmedText(edReason);
        String image64 = convertImageToBase64();

        progressBar.setVisibility(View.VISIBLE);
        btnDelete.setVisibility(View.GONE);

        // Get the API Service from RetrofitClient
        TutorApiService apiService = RetrofitClient.getTutorApiService();

        // Call the API
        Call<UploadResponse> call = apiService.deleteTutorPost(
                "73se6483r", // Key for validation
                strName,
                strNumber,
                strReason,
                image64
        );

        call.enqueue(new Callback<UploadResponse>() {
            @Override
            public void onResponse(@NonNull Call<UploadResponse> call, @NonNull Response<UploadResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    UploadResponse uploadResponse = response.body();
                    if ("000".equals(uploadResponse.getError())) {
                        Toast.makeText(DeletePostActivity.this, uploadResponse.getDetails(), Toast.LENGTH_LONG).show();
                        incrementDeleteRequestCount();
                        clearFields();
                    } else {
                        Toast.makeText(DeletePostActivity.this, "Error: " + uploadResponse.getDetails(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DeletePostActivity.this, "Unexpected server response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<UploadResponse> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(DeletePostActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void pickImage() {
        ImagePicker.with(this)
                .crop()
                .compress(212)
                .maxResultSize(1080, 1080)
                .createIntent(intent -> {
                    imagePickerLauncher.launch(intent);
                    return null;
                });
    }

    private String convertImageToBase64() {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imagSrc.getDrawable();
        if (bitmapDrawable != null) {
            Bitmap bitmap = bitmapDrawable.getBitmap();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            byte[] imageBytes = outputStream.toByteArray();
            return Base64.encodeToString(imageBytes, Base64.DEFAULT);
        }
        return ""; // Return empty if no image is selected
    }

    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent intent = result.getData();
                    if (intent != null) {
                        Uri uri = intent.getData();
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                            imagSrc.setImageBitmap(bitmap);
                            checkFieldsForEmptyValues();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    private void initiaLization() {
        edName = findViewById(R.id.edName);
        edNumber = findViewById(R.id.edNumber);
        edReason = findViewById(R.id.edReason);
        imagSrc = findViewById(R.id.imagSrc);
        cardImagpick = findViewById(R.id.cardImagpick);
        btnDelete = findViewById(R.id.btnDelete);
        imgBack = findViewById(R.id.imgBack);
        progressBar = findViewById(R.id.progressBar);
    }

    private void setTextWatchers() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkFieldsForEmptyValues();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        edName.addTextChangedListener(textWatcher);
        edNumber.addTextChangedListener(textWatcher);
        edReason.addTextChangedListener(textWatcher);
    }

    private void checkFieldsForEmptyValues() {
        boolean allFieldsFilled = !getTrimmedText(edName).isEmpty() &&
                !getTrimmedText(edNumber).isEmpty() &&
                !getTrimmedText(edReason).isEmpty() &&
                imagSrc.getDrawable() != null;

        btnDelete.setEnabled(allFieldsFilled);
        allCheck = allFieldsFilled;

        if (allCheck) {
            btnDelete.setBackgroundColor(ContextCompat.getColor(this, R.color.pink));
        } else {
            btnDelete.setBackgroundColor(ContextCompat.getColor(this, R.color.ash));
        }
    }

    private String getTrimmedText(View view) {
        if (view instanceof TextInputEditText) {
            return Objects.requireNonNull(((TextInputEditText) view).getText()).toString().trim();
        }
        return "";
    }

    private void clearFields() {
        edName.setText("");
        edNumber.setText("");
        edReason.setText("");
        edReason.clearFocus();
        edNumber.clearFocus();
        edName.clearFocus();
        imagSrc.setImageDrawable(null);
        btnDelete.setVisibility(View.VISIBLE);
    }

    // SharedPreferences methods for controlling delete request count
    private boolean canMakeDeleteRequest() {
        SharedPreferences preferences = getSharedPreferences(PREFERENCES_FILE, MODE_PRIVATE);
        int count = preferences.getInt(DELETE_REQUEST_COUNT, 0);
        long lastRequestDate = preferences.getLong(LAST_REQUEST_DATE, 0);

        // Get current date without time
        Calendar calendar = Calendar.getInstance();
        long currentDate = calendar.getTimeInMillis() / (1000 * 60 * 60 * 24);

        // If it's a new day, reset the count
        if (lastRequestDate != currentDate) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(DELETE_REQUEST_COUNT, 0);
            editor.putLong(LAST_REQUEST_DATE, currentDate);
            editor.apply();
            count = 0;
        }

        return count < 3;
    }

    private void incrementDeleteRequestCount() {
        SharedPreferences preferences = getSharedPreferences(PREFERENCES_FILE, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        // Increment the request count
        int count = preferences.getInt(DELETE_REQUEST_COUNT, 0) + 1;

        // Save the updated count and date
        editor.putInt(DELETE_REQUEST_COUNT, count);
        editor.putLong(LAST_REQUEST_DATE, Calendar.getInstance().getTimeInMillis() / (1000 * 60 * 60 * 24));
        editor.apply();
    }
}
