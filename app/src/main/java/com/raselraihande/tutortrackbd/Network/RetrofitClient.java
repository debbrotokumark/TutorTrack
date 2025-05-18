package com.raselraihande.tutortrackbd.Network;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {


    private static final String BASE_URL = "https://whitemag.xyz/"; // Replace with your server URL
    private static Retrofit retrofit = null;

    public static synchronized TutorApiService getTutorApiService() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(TutorApiService.class);
    }
}
