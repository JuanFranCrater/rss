package com.example.rss.network;

import com.example.rss.model.Email;
import com.example.rss.model.Site;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by paco on 6/02/18.
 */

public interface ApiService {
    @GET("api/sites")
    Call<ArrayList<Site>> getSites();

    @GET("acceso/sites.json")
    Call<ArrayList<Site>> getLocalSites();

    @POST("api/sites")
    Call<Site> createSite(@Body Site site);

    @PUT("api/sites/{id}")
    Call<Site> updateSite(@Body Site site, @Path("id") int id);

    @DELETE("api/sites/{id}")
    Call<ResponseBody> deleteSite(@Path("id") int id);

    @POST("api/email")
    Call<ResponseBody> sendEmail(@Body Email email);
}

