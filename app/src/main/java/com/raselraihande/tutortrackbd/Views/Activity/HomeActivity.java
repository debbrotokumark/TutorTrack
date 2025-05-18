package com.raselraihande.tutortrackbd.Views.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.raselraihande.tutortrackbd.R;
import com.raselraihande.tutortrackbd.Utility.NetworkChangeListener;
import com.raselraihande.tutortrackbd.Views.Fragment.AddServiceFragment;
import com.raselraihande.tutortrackbd.Views.Fragment.HomeFragment;
import com.raselraihande.tutortrackbd.Views.Fragment.TuationFragment;
import com.raselraihande.tutortrackbd.Views.Fragment.TutorFragment;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;
    private final NetworkChangeListener networkChangeListener = new NetworkChangeListener();
    private boolean isReceiverRegistered = false; // Flag to track receiver registration
    private static final int PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.CAMERA}, PERMISSION_CODE);
        }

        bottomNav = findViewById(R.id.bottomNav);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment(), "HOME_FRAGMENT").commit();

        bottomNav.setOnNavigationItemSelectedListener(navListener);
    }

    public void setBottomNavSelectedItem(int itemId) {
        bottomNav.setSelectedItemId(itemId);
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {
                Fragment selectedFragment = null;
                String tag = null;

                if (item.getItemId() == R.id.home) {
                    tag = "HOME_FRAGMENT";
                    selectedFragment = getSupportFragmentManager().findFragmentByTag(tag);
                    if (selectedFragment == null) {
                        selectedFragment = new HomeFragment();
                    }
                } else if (item.getItemId() == R.id.tutor) {
                    tag = "tutor";
                    selectedFragment = getSupportFragmentManager().findFragmentByTag(tag);
                    if (selectedFragment == null) {
                        selectedFragment = new TutorFragment();
                    }
                } else if (item.getItemId() == R.id.tuation) {
                    tag = "tuation";
                    selectedFragment = getSupportFragmentManager().findFragmentByTag(tag);
                    if (selectedFragment == null) {
                        selectedFragment = new TuationFragment();
                    }
                } else if (item.getItemId() == R.id.add) {
                    tag = "add";
                    selectedFragment = getSupportFragmentManager().findFragmentByTag(tag);
                    if (selectedFragment == null) {
                        selectedFragment = new AddServiceFragment();
                    }
                }

                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                if (currentFragment == null || !Objects.equals(currentFragment.getTag(), tag)) {
                    if (selectedFragment != null) {
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, selectedFragment, tag);
                        transaction.addToBackStack(tag);
                        transaction.commitAllowingStateLoss();
                    }
                }

                return true;
            };

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (currentFragment != null) {
            String tag = currentFragment.getTag();
            if (tag != null && (tag.equals("tutor") || tag.equals("tuation") || tag.equals("add"))) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new HomeFragment(), "HOME_FRAGMENT")
                        .addToBackStack("HOME_FRAGMENT")
                        .commit();
                bottomNav.setSelectedItemId(R.id.home);
            } else {
                new AlertDialog.Builder(this)
                        .setMessage("Are you sure you want to exit?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", (dialog, id) -> finish())
                        .setNegativeButton("No", null)
                        .show();
            }
        } else {
            super.onBackPressed(); // Call the default behavior
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!isReceiverRegistered) {
            IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver(networkChangeListener, filter);
            isReceiverRegistered = true;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isReceiverRegistered) {
            unregisterReceiver(networkChangeListener);
            isReceiverRegistered = false;
        }
    }

    @Override
    protected void onDestroy() {
        if (isReceiverRegistered) {
            unregisterReceiver(networkChangeListener);
            isReceiverRegistered = false;
        }
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CODE) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission " + permissions[i] + " denied. Some features may not work properly.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
