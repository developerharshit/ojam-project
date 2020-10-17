package com.example.utube.Utils;

import com.example.utube.Modal.Video;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface Api {
    @GET
    Call<Video> getVideoList(@Url String url);
}
