package com.example.githubstargazers.ui.home;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

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

    @BindView(R.id.data_loader_pb)
    ProgressBar progressBar;

    @BindView(R.id.search_stargazer_b)
    Button searchButton;

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
        progressBar.setVisibility(View.VISIBLE);
        searchButton.setEnabled(false);

        mainViewModel.getStargazers(ownerEditText.getText().toString(), repositoryEditText.getText().toString()).observe(this, stargazers -> {
            progressBar.setVisibility(View.GONE);
            searchButton.setEnabled(true);


            if(stargazers.hasError()){
                Log.i(LOG_TAG, "Failed retrieving data: " + stargazers.getError());
                //alertdialog di errore
                showAlertDialog(stargazers.getError());
            } else if(stargazers.getData().size() == 0){
                Log.i(LOG_TAG, "Stargazer List is empty");
                //text vuoto
            } else {
                Log.i(LOG_TAG, "Stargazer List retrieved");
                mAdapter.setItemList(stargazers.getData());
            }

        });
    }

    private void showAlertDialog(String error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.error_title));
        builder.setMessage(getString(R.string.error_message) + error);
        builder.setPositiveButton(R.string.ok_string, null);
        builder.create();
    }


}
