package com.retrofit.user.bookerr;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by user on 30-05-2016.
 */
public interface StackOverflowAPI {
    @GET("/2.2/questions?order=desc&sort=creation&site=stackoverflow")
    Call<StackOverflowQuestions> loadQuestions(@Query("tagged") String tags);
}