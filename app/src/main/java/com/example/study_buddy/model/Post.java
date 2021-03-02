package com.example.study_buddy.model;

import android.net.Uri;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Post {
    private String id;
    private String title;
    private String description;
    private String createdAt;
    private String[] tags;
    private User author;

    private static final String ROBO_HASH_BASE_URL = "https://robohash.org";
    private static final String QUERY_PARAM = "set";

    public Post(String id, String title, String description, String createdAt, String[] tags, User author) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.tags = tags;
        this.author = author;
    }

    public static Uri generateProfileImageUri(Post post) {

        return Uri.parse(ROBO_HASH_BASE_URL).buildUpon()
                .appendPath(post.getTitle())
                .appendQueryParameter(QUERY_PARAM, "set4")
                .build();
    }

    public static String generateTagsString(Post post) {
        StringBuilder stringBuilder = new StringBuilder();

        for (String tag: post.getTags()) {
            stringBuilder.append("#" + tag+ " ");
        }

        return stringBuilder.toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedAt() {
        String pattern = "EEE, MMM d, yyy hh:mm aaa";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);

        String formattedDate;

        try {
            Date createdAtDate = dateFormat.parse(createdAt);
            formattedDate = dateFormat.format(createdAtDate);

        } catch (ParseException e) {
            formattedDate = dateFormat.format(new Date());
        }

        return formattedDate;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
