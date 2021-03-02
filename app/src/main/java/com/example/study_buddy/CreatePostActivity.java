package com.example.study_buddy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class CreatePostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        // Update the action bar title
        getSupportActionBar().setTitle("Create Post");
    }
}