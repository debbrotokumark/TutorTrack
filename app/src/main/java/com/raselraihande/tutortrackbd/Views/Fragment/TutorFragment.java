package com.raselraihande.tutortrackbd.Views.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.raselraihande.tutortrackbd.Adapter.TutorAdapter;
import com.raselraihande.tutortrackbd.Model.TutorResponse;
import com.raselraihande.tutortrackbd.R;
import com.raselraihande.tutortrackbd.ViewModel.TutorViewModel;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TutorFragment extends Fragment {

    private RecyclerView tutorRecycler;
    private LottieAnimationView LottieLoading, LottieNodata;
    private TutorViewModel tutorViewModel;
    private SearchView searchView;
    private TutorAdapter tutorAdapter;
    private CardView cardser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tutor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tutorRecycler = view.findViewById(R.id.tutorRecycler);
        LottieLoading = view.findViewById(R.id.LottieLoading);
        LottieNodata = view.findViewById(R.id.LottieNodata);
        searchView = view.findViewById(R.id.searchView);
        cardser = view.findViewById(R.id.cardser);

        cardser.setVisibility(View.INVISIBLE);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true);
        layoutManager.setStackFromEnd(true);
        tutorRecycler.setLayoutManager(layoutManager);

        tutorViewModel = new ViewModelProvider(this).get(TutorViewModel.class);

        tutorAdapter = new TutorAdapter(new ArrayList<>());
        tutorRecycler.setAdapter(tutorAdapter);

        observeViewModel();
        setUpSearchView();
        searchView.setEnabled(false);
    }

    private void observeViewModel() {
        tutorViewModel.getTutorList().observe(getViewLifecycleOwner(), tutorPosts -> {
            if (tutorPosts != null && !tutorPosts.isEmpty()) {
                cardser.setVisibility(View.VISIBLE);
                LottieLoading.setVisibility(View.GONE);
                LottieNodata.setVisibility(View.GONE);
                tutorRecycler.setVisibility(View.VISIBLE);
                setUpRecyclerView(tutorPosts);
                searchView.setEnabled(true);  // Enable SearchView only after adapter is set
            } else {
                cardser.setVisibility(View.INVISIBLE);
                LottieNodata.setVisibility(View.VISIBLE);
                LottieLoading.setVisibility(View.GONE);
                tutorRecycler.setVisibility(View.GONE);
            }
        });


    }


    private void setUpRecyclerView(List<TutorResponse.TutorPostModel> tutorList) {
        tutorAdapter.updateTutorList(tutorList);
    }

    private void setUpSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (tutorAdapter != null) {
                    tutorAdapter.filter(query);

                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (tutorAdapter != null) {
                    tutorAdapter.filter(newText);
                }
                return false;
            }

        });
    }
    @Override
    public void onStart() {
        super.onStart();
        if (searchView != null) {
            searchView.setQuery("", false);
            searchView.clearFocus(); // Optional: remove focus from SearchView
        }
    }

}
