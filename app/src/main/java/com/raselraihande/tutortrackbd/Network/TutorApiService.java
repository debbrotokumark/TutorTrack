package com.raselraihande.tutortrackbd.Network;

import com.raselraihande.tutortrackbd.Model.LatestPostsResponse;
import com.raselraihande.tutortrackbd.Model.TuationResponse;
import com.raselraihande.tutortrackbd.Model.TutorResponse;
import com.raselraihande.tutortrackbd.Model.UploadResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface TutorApiService {
    @GET("TutorTrack/Tutor/fetchalltutorpost.php") // Replace with your actual API endpoint
    Call<TutorResponse> getAllTutors();

    @GET("TutorTrack/Tuation/fetchallTuationPost.php") // Replace with your actual API endpoint
    Call<TuationResponse> getAllTuation();


    @FormUrlEncoded
    @POST("TutorTrack/Tutor/insertvalue.php")
    Call<UploadResponse> uploadTutorPost(
            @Field("key") String key,
            @Field("name") String name,
            @Field("details") String details,
            @Field("studyAt") String studyAt,
            @Field("location") String location,
            @Field("studentClass") String studentClass,
            @Field("weekDay") String weekDay,
            @Field("medium") String medium,
            @Field("subject") String subject,
            @Field("salary") String salary,
            @Field("number") String number,
            @Field("image64") String imageBase64
    );

    @FormUrlEncoded
    @POST("TutorTrack/DeletePost/delete_post.php") // Replace with the actual path to your PHP file
    Call<UploadResponse> deleteTutorPost(
            @Field("key") String key,
            @Field("name") String name,
            @Field("number") String number,
            @Field("reason") String reason,
            @Field("image64") String image64
    );

    @FormUrlEncoded
    @POST("TutorTrack/Tuation/insetTuation.php") // Replace with your actual PHP API path for TuationPost
    Call<UploadResponse> uploadTuationPost(
            @Field("key") String key,
            @Field("name") String name,
            @Field("details") String details,
            @Field("studyAt") String studyAt,
            @Field("location") String location,
            @Field("studentClass") String studentClass,
            @Field("weekDay") String weekDay,
            @Field("medium") String medium,
            @Field("subject") String subject,
            @Field("studentsNo") String studentsNo,
            @Field("salary") String salary,
            @Field("studyTime") String studyTime,
            @Field("number") String number,
            @Field("image64") String imageBase64
    );

    @GET("TutorTrack/fetchLastPost.php")
    Call<LatestPostsResponse> getLatestPosts();


}
