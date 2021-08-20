package com.example.pawsome;

import com.example.pawsome.ui.Analyze.AnalysisResponse.AnalysisResponse;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiService {
    @Headers("x-api-key: 9b2aae9d-ca2b-4ff0-9578-5f4ed46e2921")
    @GET("v1/images/search/")
    Call<ArrayList<Dogs>>getDogs(
            @Query("limit")Integer limit,
            @Query("page")Integer page
    );

    @Multipart
    @Headers("x-api-key: 9b2aae9d-ca2b-4ff0-9578-5f4ed46e2921")
    @POST("v1/images/upload/")
    Call<Response> upload(
            @Part MultipartBody.Part file
    );

    @Headers("x-api-key: 9b2aae9d-ca2b-4ff0-9578-5f4ed46e2921")
    @GET("v1/images/{user_id}/analysis")
    Call<ArrayList<AnalysisResponse>>Analysis(
            @Path(value = "user_id") String user_id
    );

}
