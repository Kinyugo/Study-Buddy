package com.example.study_buddy.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.study_buddy.model.Chat;
import com.example.study_buddy.model.Post;
import com.example.study_buddy.utils.JSONUtils;
import com.example.study_buddy.utils.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class ChatRepository {

    public static final String CHATS_API = "https://603cf6aef4333a0017b68bea.mockapi.io/chats";

    private Thread mThread;

    public void fetchChats(MutableLiveData<Chat[]> mChats) {
        Runnable fetchChatsRunnable = new Runnable() {
            @Override
            public void run() {
                queryChatsAPI(CHATS_API, mChats);
            }
        };

        if (mThread != null) {
            mThread.interrupt();
        }

        // Start a new thread to fetch chats
        mThread = new Thread(fetchChatsRunnable);
        mThread.start();
    }

    private void queryChatsAPI(String chatsApi, MutableLiveData<Chat[]> mChats) {
        try {
            Log.d("ChatRepository", "Fetching chats from API");

            URL chatsAPIURL = NetworkUtils.buildURL(chatsApi);
            String response = NetworkUtils.getResponseFromHttpURL(chatsAPIURL);

            Chat[] chats = JSONUtils.parseChatsFromString(response);

            mChats.postValue(chats);

            Log.d("ChatRepository", "Finished fetching chats from API");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
