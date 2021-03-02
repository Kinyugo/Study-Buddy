package com.example.study_buddy.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.study_buddy.model.Post;
import com.example.study_buddy.utils.JSONUtils;
import com.example.study_buddy.utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class PostRepository {

    public static final String POSTS_API = "https://603cf6aef4333a0017b68bea.mockapi.io/posts";

    private Thread mThread;

    public void fetchPosts(MutableLiveData<Post[]> mPosts) {
        Runnable fetchPostsRunnable = new Runnable() {
            @Override
            public void run() {
                queryPostAPI(POSTS_API, mPosts);
            }
        };

        // Interrupt the current's thread work
        if (mThread != null) {
            mThread.interrupt();
        }

        // Start a new thread to fetch posts.
        mThread = new Thread(fetchPostsRunnable);
        mThread.start();
    }

    private void queryPostAPI(String postsAPI, MutableLiveData<Post[]> mPosts) {
        try {
            Log.d("PostRepository", "Fetching posts from API");

            URL postsAPIURL = NetworkUtils.buildURL(postsAPI);
            String response = NetworkUtils.getResponseFromHttpURL(postsAPIURL);
            Post[] posts = JSONUtils.parsePostsFromString(response);

            mPosts.postValue(posts);

            Log.d("PostRepository", "Finished fetching posts from API");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
