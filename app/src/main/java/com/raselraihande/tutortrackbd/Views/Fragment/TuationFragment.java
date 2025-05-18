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
import com.raselraihande.tutortrackbd.Adapter.TutationAdapter;
import com.raselraihande.tutortrackbd.Model.TuationResponse;
import com.raselraihande.tutortrackbd.R;
import com.raselraihande.tutortrackbd.ViewModel.TuationViewModel;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
public class TuationFragment extends Fragment {

    private RecyclerView tuationRecycler;
    private LottieAnimationView LottieLoading, LottieNodata;
    private TuationViewModel tuationViewModel;
    private SearchView searchView;
    private TutationAdapter tuationAdapter;
    private CardView cardser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tuation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
        initRecyclerView();
        initViewModel();

        observeViewModel();
        setUpSearchView();
    }

    private void initViews(View view) {
        tuationRecycler = view.findViewById(R.id.tuationRecycler);
        LottieLoading = view.findViewById(R.id.LottieLoading);
        LottieNodata = view.findViewById(R.id.LottieNodata);
        searchView = view.findViewById(R.id.searchView);
        cardser = view.findViewById(R.id.cardser);

        cardser.setVisibility(View.INVISIBLE);
        searchView.setEnabled(false);
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true);
        layoutManager.setStackFromEnd(true);
        tuationRecycler.setLayoutManager(layoutManager);
        tuationAdapter = new TutationAdapter(new ArrayList<>());
        tuationRecycler.setAdapter(tuationAdapter);
    }

    private void initViewModel() {
        tuationViewModel = new ViewModelProvider(this).get(TuationViewModel.class);
    }

    private void observeViewModel() {
        tuationViewModel.getTuationList().observe(getViewLifecycleOwner(), tuationPosts -> {
            if (tuationPosts != null && !tuationPosts.isEmpty()) {
                cardser.setVisibility(View.VISIBLE);
                LottieLoading.setVisibility(View.GONE);
                LottieNodata.setVisibility(View.GONE);
                tuationRecycler.setVisibility(View.VISIBLE);
                setUpRecyclerView(tuationPosts);
                searchView.setEnabled(true); // Enable SearchView only after adapter is set
            } else {
                cardser.setVisibility(View.INVISIBLE);
                LottieNodata.setVisibility(View.VISIBLE);
                LottieLoading.setVisibility(View.GONE);
                tuationRecycler.setVisibility(View.GONE);
                searchView.setEnabled(false);
            }
        });
    }

    private void setUpRecyclerView(List<TuationResponse.TuationPostModel> tuationList) {
        if (tuationAdapter != null) {
            tuationAdapter.updateTuationList(tuationList);
        }
    }

    private void setUpSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (tuationAdapter != null) {
                    tuationAdapter.filter(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (tuationAdapter != null) {
                    tuationAdapter.filter(newText);
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
