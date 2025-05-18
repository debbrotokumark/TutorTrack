package com.raselraihande.tutortrackbd;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.raselraihande.tutortrackbd.Views.Activity.HomeActivity;

public class MainActivity extends AppCompatActivity {

    private static final String PREF_NAME = "MyAppTutorTrackPreferences";
    private static final String IS_FIRST_TIME = "IsFirstTime";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        boolean isFirstTime = preferences.getBoolean(IS_FIRST_TIME, true);

        if (isFirstTime) {
            setContentView(R.layout.activity_main);{
                TextView txtLetsgo=findViewById(R.id.txtLetsgo);

                txtLetsgo.setOnClickListener(view -> {
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean(IS_FIRST_TIME, false);
                    editor.apply();
                    finish();


                });

            }
        }

        else {
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }







    }
}