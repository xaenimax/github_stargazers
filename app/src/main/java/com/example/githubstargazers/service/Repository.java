package com.example.githubstargazers.service;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.githubstargazers.model.Stargazer;
import com.example.githubstargazers.network.GithubApi;
import com.example.githubstargazers.network.GithubResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private static final String LOG_TAG = Repository.class.getSimpleName();

    //Singleton
    private static final Object LOCK = new Object();
    private static Repository sInstance;
    private static GithubApi sGithubApi;

    private Repository(){
        this.sGithubApi = new GithubApi();
    }

    public static Repository getInstance(){
        if(sInstance == null){
            synchronized (LOCK){
                sInstance = new Repository();
                Log.i(LOG_TAG, "Repository instance created");
            }
        }
        return sInstance;
    }

    public LiveData<GithubResponse<List<Stargazer>>> getStargazers(String owner, String repositoryName){
        MutableLiveData<GithubResponse<List<Stargazer>>> apiResponse = new MutableLiveData<>();

        Call<List<Stargazer>> call = sGithubApi.getStargazers(owner, repositoryName);
        call.enqueue(new Callback<List<Stargazer>>() {
            @Override
            public void onResponse(Call<List<Stargazer>> call, Response<List<Stargazer>> response) {
                apiResponse.postValue(new GithubResponse(response.body()));
            }

            @Override
            public void onFailure(Call<List<Stargazer>> call, Throwable t) {
                apiResponse.postValue(new GithubResponse<>(t));
            }
        });
        return apiResponse;
    }

}
