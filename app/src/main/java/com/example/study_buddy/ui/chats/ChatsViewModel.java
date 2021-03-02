package com.example.study_buddy.ui.chats;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.study_buddy.model.Chat;
import com.example.study_buddy.repository.ChatRepository;

public class ChatsViewModel extends ViewModel {

    private ChatRepository chatRepository;
    private MutableLiveData<Chat[]> mChats;

    public ChatsViewModel() {
        chatRepository = new ChatRepository();
    }

    public LiveData<Chat[]> getChats() {
        if (mChats == null) {
            mChats = new MutableLiveData<>();
            chatRepository.fetchChats(mChats);
        }

        return mChats;
    }

    public void refreshChats() {
        if (mChats == null) {
            mChats = new MutableLiveData<>();
        }

        chatRepository.fetchChats(mChats);
    }
}