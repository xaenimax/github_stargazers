package com.example.githubstargazers.network;

import com.example.githubstargazers.model.Stargazer;

import java.util.List;

public class GithubResponse <T>{

    T data;
    Throwable error;

    public GithubResponse(T data) {
        this.data = data;
    }

    public GithubResponse(Throwable throwable) {
        error = throwable;
    }

    public boolean hasError() {
        return error != null;
    }

    public T getData() {
        return data;
    }

    public String getError() {
        return error.getMessage();
    }
}
