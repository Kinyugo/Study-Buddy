package com.example.study_buddy.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.study_buddy.model.Post;
import com.example.study_buddy.repository.PostRepository;

public class HomeViewModel extends ViewModel {

    private PostRepository postRepository;
    private MutableLiveData<Post[]> mPosts;

    public HomeViewModel() {
        postRepository = new PostRepository();
    }

    public LiveData<Post[]> getPosts() {
        if (mPosts == null) {
            mPosts = new MutableLiveData<>();
            postRepository.fetchPosts(mPosts);
        }
        return mPosts;
    }

    public void refreshPosts() {
        postRepository.fetchPosts(mPosts);
    }
}