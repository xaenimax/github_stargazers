package com.example.githubstargazers.ui;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.githubstargazers.model.Stargazer;

import java.util.ArrayList;
import java.util.List;

public class StargazerListAdapter extends RecyclerView.Adapter {
    private List<Stargazer> mStargazerList = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mStargazerList.size();
    }

    public void setItemList(List<Stargazer> stargazers) {
        mStargazerList = stargazers;
    }
}
