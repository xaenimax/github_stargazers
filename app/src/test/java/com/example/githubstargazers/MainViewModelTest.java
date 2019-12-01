package com.example.githubstargazers;

import android.app.Application;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
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

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(MockitoJUnitRunner.class)
public class MainViewModelTest {
    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Mock
    Repository mockedRepository;

    @Mock
    Observer<GithubResponse<List<Stargazer>>> observer;

    @Mock
    Application mockedApplication;

    MutableLiveData<GithubResponse<List<Stargazer>>> apiResponse = new MutableLiveData<>();
    MainViewModel viewModel;
    String owner = "owner";
    String repo = "repo";

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        viewModel = new MainViewModel(mockedApplication);
        viewModel.setRepository(mockedRepository);

        when(mockedRepository.getStargazers(owner, repo)).thenReturn(apiResponse);
        viewModel.getStargazers(owner, repo).observeForever(observer);

    }

    @Test
    public void mainViewModel_test_data_fetch() {
        List<Stargazer> networkResults = new ArrayList<>();
        networkResults.add(new Stargazer());
        networkResults.add(new Stargazer());
        networkResults.add(new Stargazer());
        GithubResponse<List<Stargazer>> response = new GithubResponse<>(networkResults);

        apiResponse.setValue(response);

        when(mockedRepository.getStargazers(owner, repo)).thenReturn(apiResponse);
        assertTrue(viewModel.getStargazers(owner, repo).hasObservers());
        verify(observer).onChanged(response);
    }

    @Test
    public void mainViewModel_test_with_no_data() {
        GithubResponse<List<Stargazer>> response = new GithubResponse<>(new ArrayList<>());
        apiResponse.setValue(response);

        when(mockedRepository.getStargazers(owner, repo)).thenReturn(apiResponse);
        assertTrue(viewModel.getStargazers(owner, repo).hasObservers());
    }

    @Test
    public void mainViewModel_test_with_throwable() {
        Throwable error = new Throwable("test error");

        apiResponse.setValue(new GithubResponse<>(error));
        when(mockedRepository.getStargazers(owner, repo)).thenReturn(apiResponse);
        assertTrue(viewModel.getStargazers(owner, repo).hasObservers());
    }

    @After
    public void tearDown() {
        mockedRepository = null;
        viewModel = null;
        mockedApplication = null;
        owner = null;
        repo = null;
        observer = null;
    }
}