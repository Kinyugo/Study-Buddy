package com.example.study_buddy.ui.home;

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
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.study_buddy.CreatePostActivity;
import com.example.study_buddy.R;
import com.example.study_buddy.model.Post;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        final ListView listView = root.findViewById(R.id.list_view_home);
        homeViewModel.getPosts().observe(getViewLifecycleOwner(), posts -> {
            PostArrayAdapter adapter = new PostArrayAdapter(getContext(), posts);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        });

        final SwipeRefreshLayout refreshLayout = root.findViewById(R.id.refresh_layout_home);
        refreshLayout.setOnRefreshListener(() -> {
            homeViewModel.refreshPosts();
            refreshLayout.setRefreshing(false);
        });

        // Add onclick listener for the fab
        FloatingActionButton createPostFAB = root.findViewById(R.id.fab_home);
        createPostFAB.setOnClickListener(this::createPost);

        return root;
    }

    protected void createPost(View view) {
        Intent intent = new Intent(getActivity(), CreatePostActivity.class);
        startActivity(intent);
    }


    class PostArrayAdapter extends ArrayAdapter<Post> {

        public PostArrayAdapter(@NonNull Context context,  @NonNull Post[] posts) {
            super(context, R.layout.card_home, posts);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View cardHomeView = inflater.inflate(R.layout.card_home, parent, false);

            ImageView profileImageView = cardHomeView.findViewById(R.id.card_home_profile_image);
            TextView firstNameTextView = cardHomeView.findViewById(R.id.card_home_first_name);
            TextView lastNameTextView = cardHomeView.findViewById(R.id.card_home_last_name);
            TextView titleTextView = cardHomeView.findViewById(R.id.card_home_title);
            TextView descriptionTextView = cardHomeView.findViewById(R.id.card_home_description);
            TextView tagsTextView = cardHomeView.findViewById(R.id.card_home_tags);
            TextView dateTextView = cardHomeView.findViewById(R.id.card_home_date);

            // Load the profile image using picasso
            Picasso.get()
                    .load(Post.generateProfileImageUri(getItem(position)))
                    .resize(50, 50)
                    .placeholder(R.drawable.ic_person_placeholder)
                    .error(R.drawable.ic_person_placeholder)
                    .into(profileImageView);

            // Set values for other views
            Post currentPost = getItem(position);

            firstNameTextView.setText(currentPost.getAuthor().getFirstName());
            lastNameTextView.setText(currentPost.getAuthor().getLastName());
            titleTextView.setText(currentPost.getTitle());
            descriptionTextView.setText(currentPost.getDescription());
            tagsTextView.setText(Post.generateTagsString(currentPost));
            dateTextView.setText(currentPost.getCreatedAt());

            return cardHomeView;
        }
    }
}