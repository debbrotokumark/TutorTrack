package com.raselraihande.tutortrackbd.Views.Fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.raselraihande.tutortrackbd.R;
import com.raselraihande.tutortrackbd.Views.Activity.DeletePostActivity;
import com.raselraihande.tutortrackbd.Views.Activity.TuationPost;
import com.raselraihande.tutortrackbd.Views.Activity.TutorPost;

public class AddServiceFragment extends Fragment {

    private CardView cardTutorPost,cardTuationPost,cardDeletePost;
    LottieAnimationView animationView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_service, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        animationView =view. findViewById(R.id.animationView);
        init(view);
        clickevent();
    }

    private void clickevent() {
        cardTutorPost.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), TutorPost.class));

        });
        cardTuationPost.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), TuationPost.class));

        });
        cardDeletePost.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), DeletePostActivity.class));

        });


    }

    private void init(View view) {
        cardTutorPost=view.findViewById(R.id.cardTutorPost);
        cardTuationPost=view.findViewById(R.id.cardTuationPost);
        cardDeletePost=view.findViewById(R.id.cardDeletePost);


    }
    @Override
    public void onResume() {
        super.onResume();
        animationView.playAnimation(); // Start the animation when the fragment is visible
    }

    @Override
    public void onPause() {
        super.onPause();
        animationView.pauseAnimation(); // Pause the animation when the fragment goes to the background
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        animationView.cancelAnimation(); // Cancel the animation and release resources when the view is destroyed
    }

}