package com.example.sulekha.assignment_2.network;

import com.example.sulekha.assignment_2.network.model.ArtistInfo;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApIService {

    @GET("search?term=rock&amp;media=music&amp;entity=song&amp;limit=50")
    Call<ArtistInfo> getRock();

    @GET("search?term=classick&amp;media=music&amp;entity=song&amp;limit=50")
    Call<ArtistInfo> getClassic();

    @GET("search?term=pop&amp;media=music&amp;entity=song&amp;limit=50")
    Call<ArtistInfo> getPop();
}
