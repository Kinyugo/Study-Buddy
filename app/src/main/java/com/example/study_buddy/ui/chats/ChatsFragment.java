package com.example.study_buddy.ui.chats;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.study_buddy.CreateChatActivity;
import com.example.study_buddy.R;
import com.example.study_buddy.model.Chat;
import com.example.study_buddy.model.Post;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

public class ChatsFragment extends Fragment {

    private ChatsViewModel chatsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        chatsViewModel =
                new ViewModelProvider(this).get(ChatsViewModel.class);

        // Setup array adapter for the list view
        View root = inflater.inflate(R.layout.fragment_chats, container, false);
        final ListView listView = root.findViewById(R.id.list_view_chats);

        chatsViewModel.getChats().observe(getViewLifecycleOwner(), chats -> {
            ChatArrayAdapter adapter = new ChatArrayAdapter(getContext(), chats);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        });

        // Setup refresh listeners for SwipeRefreshLayout
        final SwipeRefreshLayout refreshLayout = root.findViewById(R.id.refresh_layout_chats);
        refreshLayout.setOnRefreshListener(() -> {
            chatsViewModel.refreshChats();
            refreshLayout.setRefreshing(false);
        });

        // Setup onClickListener for FAB
        FloatingActionButton createChatFAB = root.findViewById(R.id.fab_chats);
        createChatFAB.setOnClickListener(this::createChat);

        return root;
    }

    protected void createChat(View view) {
        Intent intent = new Intent(getActivity(), CreateChatActivity.class);
        startActivity(intent);
    }

    class ChatArrayAdapter extends ArrayAdapter<Chat> {

        public ChatArrayAdapter(@NonNull Context context, @NonNull Chat[] chats) {
            super(context, R.layout.item_chat, chats);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View itemChatView = inflater.inflate(R.layout.item_chat, parent, false);

            // Find views in order to update their properties.
            ImageView profileImageView = itemChatView.findViewById(R.id.item_chat_profile_image);
            TextView firstNameTextView = itemChatView.findViewById(R.id.item_chat_first_name);
            TextView lastNameTextView = itemChatView.findViewById(R.id.item_chat_last_name);
            TextView dateTextView = itemChatView.findViewById(R.id.item_chat_date);
            TextView previewTextView = itemChatView.findViewById(R.id.item_chat_preview);
            TextView countTextView = itemChatView.findViewById(R.id.item_chat_chats_count);

            // Load the profile image using Picasso
            Picasso.get()
                    .load(Chat.generateProfileImageUri(getItem(position)))
                    .resize(60, 60)
                    .placeholder(R.drawable.ic_person_placeholder)
                    .error(R.drawable.ic_person_placeholder)
                    .into(profileImageView);

            // Update view properties
            firstNameTextView.setText(getItem(position).getParticipant().getFirstName());
            lastNameTextView.setText(getItem(position).getParticipant().getLastName());
            dateTextView.setText(getItem(position).getUpdatedAt());
            previewTextView.setText(getItem(position).getPreview());
            countTextView.setText(String.valueOf(getItem(position).getCount()));

            return itemChatView;
        }
    }
}