package com.example.githubstargazers.ui.stargazer_list;

import androidx.appcompat.app.AppCompatActivity;

import com.example.githubstargazers.R;

import android.content.Intent;
import android.os.Bundle;

public class StargazerListActivity extends AppCompatActivity {

    public static final String USER_LIST_EXTRA = "user_list_extra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stargazer_list_activity);
        if (savedInstanceState == null) {
            StargazerListFragment fragment = StargazerListFragment.newInstance();
            Intent intent = getIntent();
            if(intent != null && intent.hasExtra(USER_LIST_EXTRA)){
                Bundle bundle = new Bundle();
                bundle.putString(StargazerListFragment.LIST_KEY, intent.getStringExtra(USER_LIST_EXTRA));
                fragment.setArguments(bundle);
            }

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment)
                    .commitNow();
        }
    }
}
