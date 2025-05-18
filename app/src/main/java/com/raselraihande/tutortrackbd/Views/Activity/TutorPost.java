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

import com.github.dhaval2404.imagepicker.ImagePicker;
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

public class TutorPost extends AppCompatActivity {

    private static final String PREFERENCES_FILE = "TutorPostPrefs";
    private static final String POST_COUNT = "postCount";
    private static final String LAST_POST_DATE = "lastPostDate";

    private TextInputEditText edName, edDetails, edStudyAt, edLocation, edClass,
            edWeekDay, edMedium, edSubject, edSalary, edNumber;
    private Button btnPostNow;
    private ImageView imgBack, imagSrc;
    private CardView cardImagpick;
    private boolean allCheck = false;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor_post);

        initViews();
        clickEvent();
        setTextWatchers();
    }

    private void clickEvent() {
        cardImagpick.setOnClickListener(view -> pickImage());
        imgBack.setOnClickListener(view -> finish());
        btnPostNow.setOnClickListener(view -> {
            if (allCheck) {
                if (canMakePost()) {
                    uploadData();
                } else {
                    Toast.makeText(TutorPost.this, "You have reached the limit of 3 posts today.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void uploadData() {
        String name = getTrimmedText(edName);
        String details = getTrimmedText(edDetails);
        String studyAt = getTrimmedText(edStudyAt);
        String location = getTrimmedText(edLocation);
        String studentClass = getTrimmedText(edClass);
        String weekDay = getTrimmedText(edWeekDay);
        String medium = getTrimmedText(edMedium);
        String subject = getTrimmedText(edSubject);
        String salary = getTrimmedText(edSalary);
        String number = getTrimmedText(edNumber);
        String imageBase64 = convertImageToBase64();
        progressBar.setVisibility(View.VISIBLE);
        btnPostNow.setVisibility(View.GONE);

        TutorApiService apiService = RetrofitClient.getTutorApiService();

        Call<UploadResponse> call = apiService.uploadTutorPost(
                "736483r",
                name,
                details,
                studyAt,
                location,
                studentClass,
                weekDay,
                medium,
                subject,
                salary,
                number,
                imageBase64
        );

        call.enqueue(new Callback<UploadResponse>() {
            @Override
            public void onResponse(@NonNull Call<UploadResponse> call, @NonNull Response<UploadResponse> response) {
                progressBar.setVisibility(View.GONE);
                btnPostNow.setVisibility(View.VISIBLE);
                if (response.isSuccessful() && response.body() != null) {
                    UploadResponse uploadResponse = response.body();
                    if ("000".equals(uploadResponse.getError())) {
                        Toast.makeText(TutorPost.this, uploadResponse.getDetails(), Toast.LENGTH_SHORT).show();
                        incrementPostCount();
                        fileEmty();
                    } else
                        Toast.makeText(TutorPost.this, "Error: " + uploadResponse.getDetails(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TutorPost.this, "Unexpected server response", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<UploadResponse> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                btnPostNow.setVisibility(View.VISIBLE);
                Toast.makeText(TutorPost.this, "Network error: " + t.getMessage() + ". Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean canMakePost() {
        SharedPreferences preferences = getSharedPreferences(PREFERENCES_FILE, MODE_PRIVATE);
        int count = preferences.getInt(POST_COUNT, 0);
        long lastPostDate = preferences.getLong(LAST_POST_DATE, 0);

        Calendar calendar = Calendar.getInstance();
        long currentDate = calendar.getTimeInMillis() / (1000 * 60 * 60 * 24);

        if (lastPostDate != currentDate) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(POST_COUNT, 0);
            editor.putLong(LAST_POST_DATE, currentDate);
            editor.apply();
            count = 0;
        }

        return count < 3;
    }

    private void incrementPostCount() {
        SharedPreferences preferences = getSharedPreferences(PREFERENCES_FILE, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        int count = preferences.getInt(POST_COUNT, 0) + 1;

        editor.putInt(POST_COUNT, count);
        editor.putLong(LAST_POST_DATE, Calendar.getInstance().getTimeInMillis() / (1000 * 60 * 60 * 24));
        editor.apply();
    }

    private void pickImage() {
        ImagePicker.with(this)
                .cropSquare()
                .compress(212)
                .maxResultSize(1080, 1080)
                .createIntent(intent -> {
                    imagePickerLauncher.launch(intent);
                    return null;
                });
    }

    private String convertImageToBase64() {
        if (imagSrc.getDrawable() instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) imagSrc.getDrawable();
            if (bitmapDrawable != null) {
                Bitmap bitmap = bitmapDrawable.getBitmap();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream); // Lowered the compression quality to 80%
                byte[] imageBytes = outputStream.toByteArray();
                return Base64.encodeToString(imageBytes, Base64.DEFAULT);
            }
        }
        return "";
    }

    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent intent = result.getData();
                    if (intent != null && intent.getData() != null) {
                        Uri uri = intent.getData();
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                            imagSrc.setImageBitmap(bitmap);
                            checkFieldsForEmptyValues();
                        } catch (IOException e) {
                            Toast.makeText(TutorPost.this, "Failed to load image. Please try again.", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                }
            });

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
        edDetails.addTextChangedListener(textWatcher);
        edStudyAt.addTextChangedListener(textWatcher);
        edLocation.addTextChangedListener(textWatcher);
        edClass.addTextChangedListener(textWatcher);
        edWeekDay.addTextChangedListener(textWatcher);
        edMedium.addTextChangedListener(textWatcher);
        edSubject.addTextChangedListener(textWatcher);
        edSalary.addTextChangedListener(textWatcher);
        edNumber.addTextChangedListener(textWatcher);
    }

    private void checkFieldsForEmptyValues() {
        boolean allFieldsFilled = !getTrimmedText(edName).isEmpty() &&
                !getTrimmedText(edDetails).isEmpty() &&
                !getTrimmedText(edStudyAt).isEmpty() &&
                !getTrimmedText(edLocation).isEmpty() &&
                !getTrimmedText(edClass).isEmpty() &&
                !getTrimmedText(edWeekDay).isEmpty() &&
                !getTrimmedText(edMedium).isEmpty() &&
                !getTrimmedText(edSubject).isEmpty() &&
                !getTrimmedText(edSalary).isEmpty() &&
                !getTrimmedText(edNumber).isEmpty() &&
                imagSrc.getDrawable() != null;

        btnPostNow.setEnabled(allFieldsFilled);
        allCheck = allFieldsFilled;

        if (allCheck) {
            btnPostNow.setBackgroundColor(getResources().getColor(R.color.pink));
        } else {
            btnPostNow.setBackgroundColor(getResources().getColor(R.color.ash));
        }
    }

    private String getTrimmedText(View view) {
        if (view instanceof TextInputEditText) {
            return Objects.requireNonNull(((TextInputEditText) view).getText()).toString().trim();
        }
        return "";
    }

    private void fileEmty() {
        edName.setText("");
        edDetails.setText("");
        edStudyAt.setText("");
        edLocation.setText("");
        edClass.setText("");
        edWeekDay.setText("");
        edMedium.setText("");
        edSubject.setText("");
        edSalary.setText("");
        edNumber.setText("");

        edName.clearFocus();
        edDetails.clearFocus();
        edStudyAt.clearFocus();
        edLocation.clearFocus();
        edClass.clearFocus();
        edWeekDay.clearFocus();
        edMedium.clearFocus();
        edSubject.clearFocus();
        edSalary.clearFocus();
        edNumber.clearFocus();

        imagSrc.setImageDrawable(null);
        btnPostNow.setVisibility(View.VISIBLE);
    }

    private void initViews() {
        imgBack = findViewById(R.id.imgBack);
        imagSrc = findViewById(R.id.imagSrc);

        edName = findViewById(R.id.edName);
        edDetails = findViewById(R.id.edDetails);
        edStudyAt = findViewById(R.id.edStudyAt);
        edLocation = findViewById(R.id.edLocation);
        edClass = findViewById(R.id.edClass);
        edWeekDay = findViewById(R.id.edWeekDay);
        edMedium = findViewById(R.id.edMedium);
        edSubject = findViewById(R.id.edSubject);
        edSalary = findViewById(R.id.edSalary);
        edNumber = findViewById(R.id.edNumber);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        btnPostNow = findViewById(R.id.btnPostNow);
        cardImagpick = findViewById(R.id.cardImagpick);
    }
}
