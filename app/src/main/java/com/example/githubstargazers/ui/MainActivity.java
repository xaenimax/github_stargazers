package com.example.githubstargazers.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.example.githubstargazers.R;
import com.example.githubstargazers.service.Repository;
import com.example.githubstargazers.viewmodel.MainViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    StargazerListAdapter mAdapter;
    MainViewModel mainViewModel;

    @BindView(R.id.stargazer_rv)
    RecyclerView stargazerList;

    @BindView(R.id.owner_et)
    EditText ownerEditText;

    @BindView(R.id.repository_et)
    EditText repositoryEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mAdapter = new StargazerListAdapter();
        stargazerList.setLayoutManager(new LinearLayoutManager(this));
        stargazerList.setAdapter(mAdapter);


        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.setRepository(Repository.getInstance());

    }

    @OnClick(R.id.search_stargazer_b)
    public void startSearch(){
        mainViewModel.getStargazers(ownerEditText.getText().toString(), repositoryEditText.getText().toString()).observe(this, stargazers -> {
            if(stargazers.hasError()){
                Log.i(LOG_TAG, "Failed retrieving data: " + stargazers.getError());
                //alertdialog di errore
            } else if(stargazers.getData().size() == 0){
                Log.i(LOG_TAG, "Stargazer List is empty");
                //text vuoto
            } else {
                Log.i(LOG_TAG, "Stargazer List retrieved");
                mAdapter.setItemList(stargazers.getData());
            }

        });
    }


}
