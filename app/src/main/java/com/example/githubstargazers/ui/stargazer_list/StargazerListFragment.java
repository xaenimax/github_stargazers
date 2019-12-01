package com.example.githubstargazers.ui.stargazer_list;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.githubstargazers.R;
import com.example.githubstargazers.model.Stargazer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StargazerListFragment extends Fragment {
    static final String LIST_KEY = "list_key";
    private StargazerListAdapter mAdapter;

    @BindView(R.id.stargazer_rv)
    RecyclerView stargazerList;


    static StargazerListFragment newInstance() {
        return new StargazerListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.stargazer_list_fragment, container, false);
        ButterKnife.bind(this, view);

        mAdapter = new StargazerListAdapter();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        stargazerList.setLayoutManager(new LinearLayoutManager(getActivity()));
        stargazerList.setAdapter(mAdapter);

        Bundle bundle = getArguments();
        if(bundle != null && bundle.containsKey(LIST_KEY)){
            String jsonString = bundle.getString(LIST_KEY);
            mAdapter.setItemList(new Gson().fromJson(jsonString, new TypeToken<List<Stargazer>>(){}.getType()));
        }
    }

}
