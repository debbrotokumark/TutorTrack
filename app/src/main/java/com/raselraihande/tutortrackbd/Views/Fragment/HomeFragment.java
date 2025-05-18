package com.raselraihande.tutortrackbd.Views.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.raselraihande.tutortrackbd.Model.LatestPostsResponse;
import com.raselraihande.tutortrackbd.R;
import com.raselraihande.tutortrackbd.ViewModel.HomeViewModel;
import com.raselraihande.tutortrackbd.Views.Activity.HomeActivity;
import com.raselraihande.tutortrackbd.Views.Activity.TuationActivity;
import com.raselraihande.tutortrackbd.Views.Activity.TutorActivity;

public class HomeFragment extends Fragment {

    ImageView imgManu, imgTuation, imgTutor;
    TextView txTionseemore,txtuLocation, txTeacherseemore,txSubject,txtTuationName, txtTutorName,

    txtTuationClass, txtTutorEdu,txtSalery,TecLoc,txtTeacherSub;
    CardView cardTuation, cardTutor;
    private LatestPostsResponse.LatestTuation latestTuation;
    private LatestPostsResponse.LatestTutor latestTutor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initiaLization(view);
        clickEvent();

        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        // Observe the data from ViewModel
        homeViewModel.getLatestPostsResponse().observe(getViewLifecycleOwner(), latestPosts -> {
            if (latestPosts != null) {
                latestTuation = latestPosts.getLatestTuation();
                latestTutor = latestPosts.getLatestTutor();
                updateUI();
            } else {
                Toast.makeText(getActivity(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
            }
        });

        // Fetch data if it's not already available
        if (homeViewModel.getLatestPostsResponse().getValue() == null) {
            homeViewModel.fetchLatestPosts();
        }
    }

    private void updateUI() {
        if (latestTuation != null) {
            txtTuationName.setText(latestTuation.getName());
            txtTuationClass.setText(latestTuation.getStudentClass());
            txtuLocation.setText(latestTuation.getLocation());
            txSubject.setText(latestTuation.getSubject());
            txtSalery.setText(latestTuation.getSalary());

            String imageUrl = "https://whitemag.xyz/TutorTrack/" + latestTuation.getImageBase64();
            Glide.with(requireActivity())
                    .load(imageUrl)
                    .circleCrop()
                    .placeholder(R.drawable.icman)
                    .error(R.drawable.icman)
                    .into(imgTuation);
        }

        if (latestTutor != null) {
            txtTutorName.setText(latestTutor.getName());
            txtTutorEdu.setText(latestTutor.getStudyAt());
            TecLoc.setText(latestTutor.getLocation());
            txtTeacherSub.setText(latestTutor.getSubject());

            String imageUrl = "https://whitemag.xyz/TutorTrack/" + latestTutor.getImageBase64();
            Glide.with(requireActivity())
                    .load(imageUrl)
                    .circleCrop()
                    .placeholder(R.drawable.icteacher)
                    .error(R.drawable.icteacher)
                    .into(imgTutor);
        }
    }

    private void clickEvent() {
        imgManu.setOnClickListener(view -> showPopUp());

        txTionseemore.setOnClickListener(v -> {
            Fragment tuationFragment = new TuationFragment();
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, tuationFragment, "tuation");
            transaction.addToBackStack(null);
            transaction.commit();
            if (getActivity() instanceof HomeActivity) {
                ((HomeActivity) getActivity()).setBottomNavSelectedItem(R.id.tuation);
            }
        });

        txTeacherseemore.setOnClickListener(v -> {
            Fragment tutorFragment = new TutorFragment();
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, tutorFragment, "tutor");
            transaction.addToBackStack(null);
            transaction.commit();

            if (getActivity() instanceof HomeActivity) {
                ((HomeActivity) getActivity()).setBottomNavSelectedItem(R.id.tutor);
            }
        });

        cardTutor.setOnClickListener(view -> {
            if (latestTutor != null) {
                Intent intent = new Intent(getActivity(), TutorActivity.class);
                intent.putExtra("name", latestTutor.getName());
                intent.putExtra("details", latestTutor.getDetails());
                intent.putExtra("studyAt", latestTutor.getStudyAt());
                intent.putExtra("location", latestTutor.getLocation());
                intent.putExtra("studentClass", latestTutor.getStudentClass());
                intent.putExtra("weekDay", latestTutor.getWeekDay());
                intent.putExtra("medium", latestTutor.getMedium());
                intent.putExtra("subject", latestTutor.getSubject());
                intent.putExtra("salary", latestTutor.getSalary());
                intent.putExtra("number", latestTutor.getNumber());
                intent.putExtra("imageBase64", "https://whitemag.xyz/TutorTrack/" + latestTutor.getImageBase64());
                intent.putExtra("postdate", latestTutor.getPostdate());
                startActivity(intent);
            } else {
                Toast.makeText(getActivity(), "No tutor post available", Toast.LENGTH_SHORT).show();
            }
        });

        cardTuation.setOnClickListener(view -> {
            if (latestTuation != null) {
                Intent intent = new Intent(getActivity(), TuationActivity.class);
                intent.putExtra("name", latestTuation.getName());
                intent.putExtra("details", latestTuation.getDetails());
                intent.putExtra("studyAt", latestTuation.getStudyAt());
                intent.putExtra("location", latestTuation.getLocation());
                intent.putExtra("studentClass", latestTuation.getStudentClass());
                intent.putExtra("weekDay", latestTuation.getWeekDay());
                intent.putExtra("medium", latestTuation.getMedium());
                intent.putExtra("subject", latestTuation.getSubject());
                intent.putExtra("studentsNo", latestTuation.getStudentsNo());
                intent.putExtra("salary", latestTuation.getSalary());
                intent.putExtra("studyTime", latestTuation.getStudyTime());
                intent.putExtra("number", latestTuation.getNumber());
                intent.putExtra("imageBase64", "https://whitemag.xyz/TutorTrack/" + latestTuation.getImageBase64());
                intent.putExtra("postdate", latestTuation.getPostdate());
                startActivity(intent);
            } else {
                Toast.makeText(getActivity(), "No tuation post available", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showPopUp() {
        PopupMenu popup = new PopupMenu(getActivity(), imgManu);
        popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(menuItem -> {
            if (menuItem.getItemId() == R.id.about) {
                String url = "https://whitemag.xyz/TutorTrack/Aboutus.html";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            } else {
                String url = "https://whitemag.xyz/TutorTrack/PrivacyPolicy.html";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
            return true;
        });
        popup.show();
    }

    private void initiaLization(View v) {
        imgManu = v.findViewById(R.id.imgmanu);
        txTionseemore = v.findViewById(R.id.txTionseemore);
        txTeacherseemore = v.findViewById(R.id.txTeacherseemore);
        cardTuation = v.findViewById(R.id.cardTuation);
        cardTutor = v.findViewById(R.id.cardTuator);
        txtuLocation = v.findViewById(R.id.txtuLocation);
        txSubject = v.findViewById(R.id.txSubject);
        txtSalery = v.findViewById(R.id.txtSalery);
        TecLoc = v.findViewById(R.id.TecLoc);
        txtTeacherSub = v.findViewById(R.id.txtTeacherSub);

        // Initialize the new views
        imgTuation = v.findViewById(R.id.imgTution);
        imgTutor = v.findViewById(R.id.imgTecherPro);
        txtTuationName = v.findViewById(R.id.txttutionname);
        txtTutorName = v.findViewById(R.id.txtTeacherNam);
        txtTuationClass = v.findViewById(R.id.txtClaas);
        txtTutorEdu = v.findViewById(R.id.txtTechercls);
    }
}