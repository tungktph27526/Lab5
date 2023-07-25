package com.example.lab5;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();
    ApiService apiService = new Retrofit.Builder()
            .baseUrl("http://192.168.1.5:8080/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);
    @GET("list")
    Call<List<ProductModel>> getProducts();

    @POST("addProduct")
    Call<ProductModel> addProduct(@Body ProductModel product);

    @PUT("product/{id}")
    Call<ProductModel> updateProduct(@Body ProductModel product, @Path("id") String id);

    @DELETE("product/{id}")
    Call<Void> deleteProduct(@Path("id") String id);
}
