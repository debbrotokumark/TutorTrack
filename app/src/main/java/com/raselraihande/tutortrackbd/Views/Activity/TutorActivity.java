package com.raselraihande.tutortrackbd.Views.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.raselraihande.tutortrackbd.R;

public class TutorActivity extends AppCompatActivity {

    private static final int REQUEST_CALL_PHONE = 1;

    private ImageView imgBack, imgTutor;
    private LinearLayout layCall;
    private String number = "123";
    private TextView txtTutorNam, txtTutorDetails, txtTutorLoc, txtTutorClss,
            txtTutorWeek, txtTutorMedium, txtTutorSub,
            txtTutorSalary, txtTutorPost, txtTutorEdu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutor);

        // Request permission if not already granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PHONE);
        }

        initViews();
        clickEvent();
        getData();
    }

    private void getData() {
        Intent intent = getIntent();
        if (intent != null) {
            String name = intent.getStringExtra("name") != null ? intent.getStringExtra("name") : "N/A";
            String details = intent.getStringExtra("details") != null ? intent.getStringExtra("details") : "N/A";
            String studyAt = intent.getStringExtra("studyAt") != null ? intent.getStringExtra("studyAt") : "N/A";
            String location = intent.getStringExtra("location") != null ? intent.getStringExtra("location") : "N/A";
            String studentClass = intent.getStringExtra("studentClass") != null ? intent.getStringExtra("studentClass") : "N/A";
            String weekDay = intent.getStringExtra("weekDay") != null ? intent.getStringExtra("weekDay") : "N/A";
            String medium = intent.getStringExtra("medium") != null ? intent.getStringExtra("medium") : "N/A";
            String subject = intent.getStringExtra("subject") != null ? intent.getStringExtra("subject") : "N/A";
            String salary = intent.getStringExtra("salary") != null ? intent.getStringExtra("salary") : "N/A";
            number = intent.getStringExtra("number") != null ? intent.getStringExtra("number") : "N/A";
            String imageBase64 = intent.getStringExtra("imageBase64") != null ? intent.getStringExtra("imageBase64") : "";
            String postdate = intent.getStringExtra("postdate") != null ? intent.getStringExtra("postdate") : "N/A";

            assert imageBase64 != null;
            setData(name, details, studyAt, location, studentClass, weekDay, medium, subject, salary, imageBase64, postdate);
        }
    }

    private void setData(String name, String details, String studyAt, String location, String studentClass,
                         String weekDay, String medium, String subject, String salary,
                         String imageBase64, String postdate) {

        txtTutorNam.setText(name);
        txtTutorDetails.setText(details);
        txtTutorEdu.setText(studyAt);
        txtTutorLoc.setText(location);
        txtTutorClss.setText(studentClass);
        txtTutorWeek.setText(weekDay);
        txtTutorMedium.setText(medium);
        txtTutorSub.setText(subject);
        txtTutorSalary.setText(salary);
        txtTutorPost.setText(postdate);

        // Load image with Glide, providing a fallback if imageBase64 is empty
        Glide.with(this)
                .load(!imageBase64.isEmpty() ? imageBase64 : R.drawable.icteacher)
                .circleCrop()
                .placeholder(R.drawable.icteacher)
                .into(imgTutor);
    }

    private void clickEvent() {
        imgBack.setOnClickListener(view -> finish());

        layCall.setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                if (isValidPhoneNumber(number)) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Invalid phone number", Toast.LENGTH_SHORT).show();
                }
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PHONE);
            }
        });
    }

    private void initViews() {
        imgBack = findViewById(R.id.imgBack);
        layCall = findViewById(R.id.layCall);
        imgTutor = findViewById(R.id.imgTutor);

        txtTutorNam = findViewById(R.id.txtTutorNam);
        txtTutorDetails = findViewById(R.id.txtTutorDetails);
        txtTutorEdu = findViewById(R.id.txtTutorEdu);
        txtTutorLoc = findViewById(R.id.txtTutorLoc);
        txtTutorClss = findViewById(R.id.txtTutorClss);
        txtTutorWeek = findViewById(R.id.txtTutorWeek);
        txtTutorMedium = findViewById(R.id.txtTutorMedium);
        txtTutorSub = findViewById(R.id.txtTutorSub);
        txtTutorSalary = findViewById(R.id.txtTutorSalary);
        txtTutorPost = findViewById(R.id.txtTutorPost);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL_PHONE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Call permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Call permission is required to make a call", Toast.LENGTH_LONG).show();
            }
        }
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\d{3,15}"); // Example validation: 3 to 15 digits
    }
}
