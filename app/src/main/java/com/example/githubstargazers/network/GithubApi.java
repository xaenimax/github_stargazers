package com.example.githubstargazers.network;

import com.example.githubstargazers.model.Stargazer;

import java.util.List;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GithubApi {
    private Retrofit retrofit;

    private final static String HOST = "https://api.github.com/";

    public GithubApi() {
        retrofit = new Retrofit.Builder()
                .baseUrl(HOST).callbackExecutor(Executors.newSingleThreadExecutor())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public Call<List<Stargazer>> getStargazers(String owner, String repository) {

        StargazerService service = retrofit.create(StargazerService.class);
        return service.getStargazers(owner, repository);
    }

}
