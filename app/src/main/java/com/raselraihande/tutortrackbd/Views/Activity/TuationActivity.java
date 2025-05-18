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

public class TuationActivity extends AppCompatActivity {

    private static final int REQUEST_CALL_PHONE = 1;

    private ImageView imgBack, imgStu;
    private LinearLayout layCall;
    private String number = "Not available";
    private TextView txtStuNam, txtStuDetails, txtStuLoc, txtStuClss,
            txtStuWeek, txtStuMedium, txtStuSub, txtStuNum,
            txtStuSalary, txtStuPost, txtStuEdu, txtStuTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuation);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PHONE);
        }

        initViews();
        setClickEvents();
        getDataFromIntent();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            String name = intent.getStringExtra("name") != null ? intent.getStringExtra("name") : "";
            String details = intent.getStringExtra("details") != null ? intent.getStringExtra("details") : "";
            String studyAt = intent.getStringExtra("studyAt") != null ? intent.getStringExtra("studyAt") : "";
            String location = intent.getStringExtra("location") != null ? intent.getStringExtra("location") : "";
            String studentClass = intent.getStringExtra("studentClass") != null ? intent.getStringExtra("studentClass") : "";
            String weekDay = intent.getStringExtra("weekDay") != null ? intent.getStringExtra("weekDay") : "";
            String medium = intent.getStringExtra("medium") != null ? intent.getStringExtra("medium") : "";
            String subject = intent.getStringExtra("subject") != null ? intent.getStringExtra("subject") : "";
            String studentsNo = intent.getStringExtra("studentsNo") != null ? intent.getStringExtra("studentsNo") : "";
            String salary = intent.getStringExtra("salary") != null ? intent.getStringExtra("salary") : "";
            String studyTime = intent.getStringExtra("studyTime") != null ? intent.getStringExtra("studyTime") : "";
            number = intent.getStringExtra("number") != null ? intent.getStringExtra("number") : "Not available";
            String imageBase64 = intent.getStringExtra("imageBase64") != null ? intent.getStringExtra("imageBase64") : "";
            String postdate = intent.getStringExtra("postdate") != null ? intent.getStringExtra("postdate") : "";

            setData(name, details, studyAt, location, studentClass, weekDay, medium, subject, studentsNo, salary, studyTime, imageBase64, postdate);
        }
    }

    private void setData(String name, String details, String studyAt, String location, String studentClass,
                         String weekDay, String medium, String subject, String studentsNo, String salary,
                         String studyTime, String imageBase64, String postdate) {

        txtStuNam.setText(name);
        txtStuDetails.setText(details);
        txtStuEdu.setText(studyAt);
        txtStuLoc.setText(location);
        txtStuClss.setText(studentClass);
        txtStuWeek.setText(weekDay);
        txtStuMedium.setText(medium);
        txtStuSub.setText(subject);
        txtStuNum.setText(studentsNo);
        txtStuSalary.setText( salary);
        txtStuTime.setText(studyTime);
        txtStuPost.setText(postdate);

        if (imageBase64 != null && !imageBase64.isEmpty()) {
            Glide.with(this)
                    .load(imageBase64)
                    .circleCrop()
                    .placeholder(R.drawable.icman)
                    .into(imgStu);
        } else {
            imgStu.setImageResource(R.drawable.icman);
        }
    }

    private void setClickEvents() {
        imgBack.setOnClickListener(view -> finish());

        layCall.setOnClickListener(view -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));
                startActivity(intent);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PHONE);
            }
        });
    }

    private void initViews() {
        imgBack = findViewById(R.id.imgBack);
        layCall = findViewById(R.id.layCall);
        imgStu = findViewById(R.id.imgStu);

        txtStuNam = findViewById(R.id.txtStuNam);
        txtStuDetails = findViewById(R.id.txtStuDetails);
        txtStuEdu = findViewById(R.id.txtStuEdu);
        txtStuLoc = findViewById(R.id.txtStuLoc);
        txtStuClss = findViewById(R.id.txtStuClss);
        txtStuWeek = findViewById(R.id.txtStuWeek);
        txtStuMedium = findViewById(R.id.txtStuMedium);
        txtStuSub = findViewById(R.id.txtStuSub);
        txtStuNum = findViewById(R.id.txtStuNum);
        txtStuSalary = findViewById(R.id.txtStuSalary);
        txtStuPost = findViewById(R.id.txtTutorPost);
        txtStuTime = findViewById(R.id.txtStuTime);
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
}
