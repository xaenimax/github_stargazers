package com.example.githubstargazers.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.githubstargazers.R;
import com.example.githubstargazers.model.Stargazer;
import com.example.githubstargazers.network.GithubApi;
import com.example.githubstargazers.network.GithubResponse;
import com.example.githubstargazers.service.Repository;
import com.example.githubstargazers.ui.stargazer_list.StargazerListActivity;
import com.example.githubstargazers.viewmodel.MainViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private static final String OWNER_KEY = "owner_key";
    private static final String REPOSITORY_KEY = "repository_key";

    MainViewModel mainViewModel;
    Observer<GithubResponse<List<Stargazer>>> observer;

    @BindView(R.id.owner_tl)
    TextInputLayout ownerTextLayout;

    @BindView(R.id.repository_tl)
    TextInputLayout repositoryTextLayout;

    @BindView(R.id.data_loader_pb)
    ProgressBar progressBar;

    @BindView(R.id.search_stargazer_b)
    Button searchButton;


    @BindView(R.id.owner_te)
    TextInputEditText ownerEditText;

    @BindView(R.id.repository_te)
    TextInputEditText repositoryEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mainViewModel.setRepository(Repository.getInstance(new GithubApi()));

        ownerEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ownerTextLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        repositoryEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                repositoryTextLayout.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @OnClick(R.id.search_stargazer_b)
    public void startSearch() {
        if (isValidInput()) {

            progressBar.setVisibility(View.VISIBLE);
            searchButton.setEnabled(false);

            mainViewModel.getStargazers(Objects.requireNonNull(ownerTextLayout.getEditText()).getText().toString(), repositoryTextLayout.getEditText().getText().toString()).observe(this, stargazers -> {
                progressBar.setVisibility(View.GONE);
                searchButton.setEnabled(true);
                if (stargazers.hasError()) {
                    Log.i(LOG_TAG, "Failed retrieving data: " + stargazers.getError());
                    //alertdialog di errore
                    showAlertDialog(stargazers.getError());
                } else if (stargazers.getData().size() == 0) {
                    Log.i(LOG_TAG, "Stargazer List is empty");
                    showAlertDialog(getString(R.string.no_results_error_message));
                } else {
                    Log.i(LOG_TAG, "Stargazer List retrieved");
                    Intent intent = new Intent(this, StargazerListActivity.class);
                    Gson gson = new Gson();
                    String jsonString = gson.toJson(stargazers.getData());
                    intent.putExtra(StargazerListActivity.USER_LIST_EXTRA, jsonString);
                    startActivity(intent);
                }


            });
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState.containsKey(OWNER_KEY)){
            ownerTextLayout.getEditText().setText(savedInstanceState.getString(OWNER_KEY));
        }
        if(savedInstanceState.containsKey(REPOSITORY_KEY)){
            repositoryTextLayout.getEditText().setText(savedInstanceState.getString(REPOSITORY_KEY));
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(OWNER_KEY, ownerTextLayout.getEditText().getText().toString());
        outState.putString(REPOSITORY_KEY, repositoryTextLayout.getEditText().getText().toString());

        super.onSaveInstanceState(outState);
    }

    private boolean isValidInput() {
        boolean isValid = true;
        if (ownerTextLayout.getEditText().getText().toString().isEmpty()) {
            ownerTextLayout.setError(getString(R.string.enter_owner));
            isValid = false;
        }
        if (repositoryTextLayout.getEditText().getText().toString().isEmpty()) {
            repositoryTextLayout.setError(getString(R.string.enter_repository));
            isValid = false;
        }
        return isValid;
    }

    private void showAlertDialog(String error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.error_title));
        builder.setMessage(getString(R.string.error_message) + " " + error);
        builder.setPositiveButton(R.string.ok_string, null);
        builder.create().show();
    }


}
