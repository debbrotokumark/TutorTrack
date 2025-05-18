package com.raselraihande.tutortrackbd.ViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.raselraihande.tutortrackbd.Model.TutorResponse;
import com.raselraihande.tutortrackbd.Network.RetrofitClient;
import com.raselraihande.tutortrackbd.Network.TutorApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class TutorViewModel extends ViewModel {

    private MutableLiveData<List<TutorResponse.TutorPostModel>> tutorList;
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private final MutableLiveData<Boolean> hasError = new MutableLiveData<>();

    public LiveData<List<TutorResponse.TutorPostModel>> getTutorList() {
        if (tutorList == null) {
            tutorList = new MutableLiveData<>();
            fetchAllTutors();
        }
        return tutorList;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<Boolean> getHasError() {
        return hasError;
    }

    private void fetchAllTutors() {
        isLoading.setValue(true);
        hasError.setValue(false);

        TutorApiService tutorApiService = RetrofitClient.getTutorApiService();
        Call<TutorResponse> call = tutorApiService.getAllTutors();

        call.enqueue(new Callback<TutorResponse>() {
            @Override
            public void onResponse(@NonNull Call<TutorResponse> call, @NonNull Response<TutorResponse> response) {
                isLoading.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    TutorResponse tutorResponse = response.body();
                    if (tutorResponse.getError().contains("000")) {
                        tutorList.setValue(tutorResponse.getTutorPostModels());
                        hasError.setValue(false);
                    } else {
                        hasError.setValue(true);
                    }
                } else {
                    hasError.setValue(true);
                }
            }

            @Override
            public void onFailure(@NonNull Call<TutorResponse> call, @NonNull Throwable t) {
                isLoading.setValue(false);
                hasError.setValue(true);
            }
        });
    }
}
