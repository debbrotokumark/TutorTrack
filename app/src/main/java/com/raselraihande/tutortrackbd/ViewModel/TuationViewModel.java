package com.raselraihande.tutortrackbd.ViewModel;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.raselraihande.tutortrackbd.Model.TuationResponse;
import com.raselraihande.tutortrackbd.Model.TuationResponse.TuationPostModel;
import com.raselraihande.tutortrackbd.Network.RetrofitClient;
import com.raselraihande.tutortrackbd.Network.TutorApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class TuationViewModel extends ViewModel {

    private MutableLiveData<List<TuationPostModel>> tuationList;
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final MutableLiveData<Boolean> hasError = new MutableLiveData<>();

    // Getter for tuition list
    public LiveData<List<TuationPostModel>> getTuationList() {
        if (tuationList == null) {
            tuationList = new MutableLiveData<>();
            fetchAllTuations();
        }
        return tuationList;
    }

    // Getter for loading status
    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    // Getter for error status
    public LiveData<Boolean> getHasError() {
        return hasError;
    }

    // Fetch all tuition posts from the API
    private void fetchAllTuations() {
        isLoading.setValue(true);
        hasError.setValue(false);

        TutorApiService tutorApiService = RetrofitClient.getTutorApiService();
        Call<TuationResponse> call = tutorApiService.getAllTuation();

        call.enqueue(new Callback<TuationResponse>() {
            @Override
            public void onResponse(@NonNull Call<TuationResponse> call, @NonNull Response<TuationResponse> response) {
                isLoading.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    TuationResponse tuationResponse = response.body();
                    if (tuationResponse.getError().contains("000")) {
                        tuationList.setValue(tuationResponse.getTuationPostModels());
                        hasError.setValue(false);
                    } else {
                        hasError.setValue(true);
                    }
                } else {
                    hasError.setValue(true);
                }
            }

            @Override
            public void onFailure(@NonNull Call<TuationResponse> call, @NonNull Throwable t) {
                isLoading.setValue(false);
                hasError.setValue(true);
            }
        });
    }
}

