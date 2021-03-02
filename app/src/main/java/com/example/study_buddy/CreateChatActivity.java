package com.example.study_buddy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class CreateChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_chat);

        // Update action bar title
        getSupportActionBar().setTitle("Create Chat");
    }
}