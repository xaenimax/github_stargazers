package com.example.githubstargazers.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.example.githubstargazers.model.Stargazer;
import com.example.githubstargazers.network.GithubResponse;
import com.example.githubstargazers.service.Repository;

import java.util.List;


public class MainViewModel extends AndroidViewModel {
    private Repository mRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public void setRepository(Repository repository) {
        this.mRepository = repository;
    }

    public LiveData<GithubResponse<List<Stargazer>>> getStargazers(String owner, String repositoryName) {
        return mRepository.getStargazers(owner, repositoryName);
    }
}
