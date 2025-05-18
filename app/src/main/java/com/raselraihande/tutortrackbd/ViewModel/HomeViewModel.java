package com.raselraihande.tutortrackbd.ViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.raselraihande.tutortrackbd.Model.LatestPostsResponse;
import com.raselraihande.tutortrackbd.Network.RetrofitClient;
import com.raselraihande.tutortrackbd.Network.TutorApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<LatestPostsResponse> latestPostsResponse = new MutableLiveData<>();

    public MutableLiveData<LatestPostsResponse> getLatestPostsResponse() {
        return latestPostsResponse;
    }

    public void fetchLatestPosts() {
        TutorApiService tutorApiService = RetrofitClient.getTutorApiService();
        Call<LatestPostsResponse> call = tutorApiService.getLatestPosts();

        call.enqueue(new Callback<LatestPostsResponse>() {
            @Override
            public void onResponse(@NonNull Call<LatestPostsResponse> call, @NonNull Response<LatestPostsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    latestPostsResponse.setValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<LatestPostsResponse> call, @NonNull Throwable t) {
                // Handle failure - update the live data with null or appropriate value
                latestPostsResponse.setValue(null);
            }
        });
    }
}
