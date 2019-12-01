package com.example.githubstargazers;

import android.app.Application;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.githubstargazers.model.Stargazer;
import com.example.githubstargazers.network.GithubApi;
import com.example.githubstargazers.network.GithubResponse;
import com.example.githubstargazers.service.Repository;
import com.example.githubstargazers.viewmodel.MainViewModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

import static junit.framework.TestCase.fail;
import static org.mockito.Mockito.when;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(MockitoJUnitRunner.class)
public class GithubStargazerTest {
    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Mock
    Repository mockedRepository;

    @Mock
    Application mockedApplication;

    MainViewModel viewModel;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        viewModel = new MainViewModel(mockedApplication);
        viewModel.setRepository(mockedRepository);
    }

    @Test
    public void mainViewModel_test_with_no_errors() {
        String owner = "owner";
        String repo = "repo";
        List<Stargazer> networkResults = new ArrayList<>();
        networkResults.add(new Stargazer());
        networkResults.add(new Stargazer());
        networkResults.add(new Stargazer());

        MutableLiveData<GithubResponse<List<Stargazer>>> apiResponse = new MutableLiveData<>();
        apiResponse.setValue(new GithubResponse<>(networkResults));

        when(mockedRepository.getStargazers(owner, repo)).thenReturn(apiResponse);

        viewModel.getStargazers(owner,repo).hasObservers();
    }

    @After
    public void tearDown() {
        mockedRepository = null;
        viewModel = null;
        mockedApplication = null;
    }
}