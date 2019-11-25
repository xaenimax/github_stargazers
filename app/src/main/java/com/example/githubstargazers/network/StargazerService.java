package com.example.githubstargazers.network;

import com.example.githubstargazers.model.Stargazer;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface StargazerService {

    @GET("/repos/{owner}/{repo}/stargazers")
    Call<List<Stargazer>> getStargazers(@Path("owner") String owner, @Path("repo") String repositoryName);


}
